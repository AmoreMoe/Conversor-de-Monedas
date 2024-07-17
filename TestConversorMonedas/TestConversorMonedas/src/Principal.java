import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Principal {
    private static final String[] MONEDAS = {"ARS", "BOB", "BRL", "CLP", "COP", "USD", "MXN"};
    private static final String MONEDA_BASE_DEFAULT = "MXN";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String monedaBase = MONEDA_BASE_DEFAULT;
        Map<String, Double> tasasDeCambio = new HashMap<>();
        ConsultaAPI consultaAPI = new ConsultaAPI();

        while (true) {
            monedaBase = Interfaz.seleccionarMonedaBase(scanner, monedaBase, MONEDAS);
            if (monedaBase == null) {
                System.out.println("Programa terminado.");
                break;
            }

            try {
                tasasDeCambio = consultaAPI.obtenerTasasDeCambio(monedaBase, MONEDAS);
            } catch (Exception e) {
                System.out.println("Error al obtener las tasas de cambio: " + e.getMessage());
                continue;
            }

            double cantidadBase = Interfaz.ingresarCantidad(scanner, monedaBase);
            if (cantidadBase == -1) {
                System.out.println("Programa terminado.");
                break;
            }

            String monedaObjetivo = Interfaz.seleccionarMonedaObjetivo(scanner, monedaBase, MONEDAS);
            if (monedaObjetivo == null) {
                System.out.println("Programa terminado.");
                break;
            }

            Interfaz.realizarConversion(monedaBase, monedaObjetivo, cantidadBase, tasasDeCambio);
        }

        scanner.close();
    }
}
