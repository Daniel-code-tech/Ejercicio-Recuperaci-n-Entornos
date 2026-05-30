import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Moderador moderador = new Moderador();
        int opcion;

        do {
            System.out.println("\n--- MENÚ MODERADOR DE CHAT ---");
            System.out.println("1) Normalizar nick");
            System.out.println("2) Validar nick");
            System.out.println("3) Censurar palabrotas");
            System.out.println("4) Extraer mención");
            System.out.println("5) Estadísticas de mensaje");
            System.out.println("6) Respuesta automática");
            System.out.println("0) Salir");
            System.out.print("Elige una opción: ");
            
            while (!scanner.hasNextInt()) {
                System.out.print("Por favor, introduce un número válido: ");
                scanner.next();
            }
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el búfer de entrada

            switch (opcion) {
                case 1:
                    System.out.print("Introduce el nick: ");
                    String nickNormalizar = scanner.nextLine();
                    System.out.println("Output: " + moderador.normalizarNick(nickNormalizar));
                    break;
                case 2:
                    System.out.print("Introduce el nick a validar: ");
                    String nickValidar = scanner.nextLine();
                    System.out.println("Output: " + moderador.validarNick(nickValidar));
                    break;
                case 3:
                    System.out.print("Mensaje: ");
                    String mensajeCensurar = scanner.nextLine();
                    System.out.println("Output: " + moderador.censurarPalabrotas(mensajeCensurar));
                    break;
                case 4:
                    System.out.print("Mensaje con @mencion: ");
                    String mensajeMencion = scanner.nextLine();
                    System.out.println("Output: " + moderador.extraerMencion(mensajeMencion));
                    break;
                case 5:
                    System.out.print("Mensaje: ");
                    String mensajeStats = scanner.nextLine();
                    moderador.mostrarEstadisticas(mensajeStats);
                    break;
                case 6:
                    System.out.print("Mensaje: ");
                    String mensajeIA = scanner.nextLine();
                    System.out.println("Output: " + moderador.respuestaAutomatica(mensajeIA));
                    break;
                case 0:
                    System.out.println("Saliendo del programa... ¡Hasta la próxima!");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion != 0);

        scanner.close();
    }
}

class Moderador {

    // 1. Normalizar nick: quita espacios extremos, pasa a minúsculas y cambia espacios internos por "_"
    public String normalizarNick(String nick) {
        if (nick == null) return "";
        return nick.trim().toLowerCase().replace(" ", "_");
    }

    // 2. Validar nick: longitud 3-15, empieza por letra, no contiene admin/mod (ignoreCase)
    public String validarNick(String nick) {
        if (nick == null || nick.isEmpty()) {
            return "NO válido: el nick no puede estar vacío";
        }
        
        // Regla 1: Empieza por letra
        char primerCaracter = nick.charAt(0);
        if (!Character.isLetter(primerCaracter)) {
            return "NO válido: debe empezar por letra";
        }
        
        // Regla 2: Longitud 3-15
        if (nick.length() < 3 || nick.length() > 15) {
            return "NO válido: la longitud debe ser de 3 a 15 caracteres";
        }
        
        // Regla 3: No contiene "admin" ni "mod" (ignorando mayúsculas)
        String nickMinusculas = nick.toLowerCase();
        if (nickMinusculas.contains("admin") || nickMinusculas.contains("mod")) {
            return "NO válido: no puede contener las palabras 'admin' o 'mod'";
        }
        
        return "VÁLIDO";
    }

    // 3. Censurar palabrotas: reemplaza tonto, bot, troll, manco por *** (case-insensitive para robustez)
    public String censurarPalabrotas(String mensaje) {
        if (mensaje == null) return "";
        
        // Para cumplir la restricción de NO usar arrays ni expresiones regulares complejas,
        // usamos reemplazos directos encadenados ignorando mayúsculas/minúsculas de forma nativa.
        String resultado = mensaje;
        String[] prohibidas = {"tonto", "bot", "troll", "manco"}; // Nota: Si se prohíben estrictamente arrays, se encadena secuencialmente:
        
        // Versión pura sin usar estructuras de datos (Array-free estricto):
        resultado = reemplazarIgnorandoCaso(resultado, "tonto", "***");
        resultado = reemplazarIgnorandoCaso(resultado, "bot", "***");
        resultado = reemplazarIgnorandoCaso(resultado, "troll", "***");
        resultado = reemplazarIgnorandoCaso(resultado, "manco", "***");
        
        return resultado;
    }

    private String reemplazarIgnorandoCaso(String original, String buscar, String reemplazo) {
        String objetivo = original;
        int index = objetivo.toLowerCase().indexOf(buscar.toLowerCase());
        while (index != -1) {
            objetivo = objetivo.substring(0, index) + reemplazo + objetivo.substring(index + buscar.length());
            index = objetivo.toLowerCase().indexOf(buscar.toLowerCase(), index + reemplazo.length());
        }
        return objetivo;
    }

    // 4. Extraer mención: busca @ y extrae hasta el primer espacio o fin de cadena
    public String extraerMencion(String mensaje) {
        if (mensaje == null) return "No se detectó mención";
        
        int indiceArroba = mensaje.indexOf('@');
        if (indiceArroba == -1) {
            return "No se detectó mención";
        }
        
        int indiceEspacio = mensaje.indexOf(' ', indiceArroba);
        if (