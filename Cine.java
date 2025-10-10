import java.util.*;

public class Cine {
    static final int FILAS = 4;
    static final int COLUMNAS = 6;
    static final String[] LETRAS = {"A", "B", "C", "D"};
    static String[][] asientos = new String[FILAS][COLUMNAS];

    // 2. se realiza:Mapeo comprador a asientos comprados
    static Map<String, List<String>> compradores = new LinkedHashMap<>();

    // OBtencion del Costo fijo 
    static final int PRECIO_ASIENTO = 10000;

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        inicializarAsientos();

        while (true) {
            System.out.println("MENÚ CINE");
            System.out.println("1. Comprar asiento disponible");
            System.out.println("2. Ver compradores y sus asientos");
            System.out.println("3. Ver asientos disponibles");
            System.out.println("4. Apagar sistema");
            System.out.print("Seleccione una opción (1-4): ");

            int opcion = obtenerEnteroSeguro();

            switch (opcion) {
                case 1: comprarAsientos(); break;
                case 2: mostrarCompradores(); break;
                case 3: mostrarAsientos(); break;
                case 4: System.out.println("Sistema apagado. ¡Hasta pronto!"); return;
                default: System.out.println("Opción inválida. Intente de nuevo."); break;
            }
        }
    }

    // Inicializar los asientos 
    static void inicializarAsientos() {
        for (int i = 0; i < FILAS; i++)
            for (int j = 0; j < COLUMNAS; j++)
                asientos[i][j] = LETRAS[i] + (j + 1);
    }

    // Comprar uno o más asientos para una persona
    static void comprarAsientos() {
        System.out.print("Ingrese su nombre (o número de documento): ");
        String nombre = scanner.nextLine().trim();
        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }

        // Si el usuario ya existe, recupera sus asientos; si no, crea nueva entradaaa
        List<String> misAsientos = compradores.getOrDefault(nombre, new ArrayList<>());
        int total = 0;

        while (true) {
            mostrarAsientos();
            System.out.print("Seleccione asiento (ejemplo B3): ");
            String asiento = scanner.nextLine().toUpperCase().trim();

            if (!esAsientoValido(asiento)) {
                System.out.println("Asiento inválido. Intente de nuevo.");
            } else if (!estaDisponible(asiento)) {
                System.out.println("Asiento OCUPADO. Elija otro.");
            } else {
                bloquearAsiento(asiento);
                misAsientos.add(asiento);
                total += PRECIO_ASIENTO;
                System.out.println("✔ ¡Asiento " + asiento + " asignado!");
            }

            System.out.print("¿Desea comprar otro asiento? (S/N): ");
            String respuesta = scanner.nextLine().trim().toUpperCase();
            if (!respuesta.equals("S")) {
                compradores.put(nombre, misAsientos);
                System.out.println("--- RESUMEN DE COMPRA ---");
                System.out.println("Cliente: " + nombre);
                System.out.println("Asientos comprados: " + misAsientos);
                System.out.println("Total a pagar: $" + total + " COP");
                break;
            }
        }
    }

    // Mostrar asientos de manera visual
    static void mostrarAsientos() {
        System.out.println("\nEstado de la sala (XX = ocupado):");
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                String valor = asientos[i][j];
                if (valor.equals("XX")) {
                    System.out.print("[XX] ");
                } else {
                    System.out.print("[" + valor + "] ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // Mostrar compradores y sus asientos
    static void mostrarCompradores() {
        if (compradores.isEmpty()) {
            System.out.println("No hay compras registradas aún.");
            return;
        }
        System.out.println("\n--- Lista de Compradores ---");
        for (Map.Entry<String, List<String>> entry : compradores.entrySet()) {
            System.out.println("Cliente: " + entry.getKey() + " → Asientos: " + entry.getValue());
        }
    }

    // Validación: ¿existe el asiento en la sala?
    static boolean esAsientoValido(String asiento) {
        for (int i = 0; i < FILAS; i++)
            for (int j = 0; j < COLUMNAS; j++)
                if (asientos[i][j].equals(asiento) || ("XX".equals(asientos[i][j]) && asiento.equals(LETRAS[i] + (j+1))))
                    return true;
        return false;
    }

    // Validación: ¿está disponible o ya fue vendido?
    static boolean estaDisponible(String asiento) {
        for (int i = 0; i < FILAS; i++)
            for (int j = 0; j < COLUMNAS; j++)
                if (asientos[i][j].equals(asiento))
                    return true;
        return false;
    }

    // Marcar el asiento como vendido
    static void bloquearAsiento(String asiento) {
        for (int i = 0; i < FILAS; i++)
            for (int j = 0; j < COLUMNAS; j++)
                if (asientos[i][j].equals(asiento))
                    asientos[i][j] = "XX";
    }

    // Leer enteros de forma segura para no romper el ciclo en caso de error
    static int obtenerEnteroSeguro() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.print("Por favor, ingrese un número válido: ");
            }
        }
    }
}
