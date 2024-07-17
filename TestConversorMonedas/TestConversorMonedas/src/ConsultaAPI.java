import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsultaAPI {
    private static final String API_KEY = "562f10b6f97cece94756d75d";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    public Map<String, Double> obtenerTasasDeCambio(String base, String[] monedas) throws IOException {
        String urlString = BASE_URL + base;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new IOException("HTTP response code: " + responseCode);
        }

        Scanner scanner = new Scanner(url.openStream());
        String inline = "";
        while (scanner.hasNext()) {
            inline += scanner.nextLine();
        }
        scanner.close();

        JsonObject jsonObject = JsonParser.parseString(inline).getAsJsonObject();
        JsonObject ratesObject = jsonObject.getAsJsonObject("conversion_rates");

        Map<String, Double> tasasDeCambio = new HashMap<>();
        for (String moneda : monedas) {
            if (ratesObject.has(moneda)) {
                tasasDeCambio.put(moneda, ratesObject.get(moneda).getAsDouble());
            }
        }

        return tasasDeCambio;
    }
}
