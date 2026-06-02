import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Creamos la lista de reproducción usando la interfaz List
        List<String> playlist = new ArrayList<>();

        // 1. Añadir 6 canciones inventadas
        playlist.add("Ecos del Silencio");
        playlist.add("Neon Nights");
        playlist.add("Algoritmo de Amor");
        playlist.add("Cyberpunk Sonata");
        playlist.add("Bajo el Mismo Sol");
        playlist.add("Lluvia Digital");

        System.out.println("--- Lista inicial (6 canciones) ---");
        System.out.println(playlist);
        System.out.println();

        // 2. Insertar una canción en tercera posición
        // Recordamos que en Java los índices empiezan en 0. La 3ª posición es el índice 2.
        playlist.add(2, "Melodía Escondida");
        System.out.println("--- Después de insertar en 3ª posición ---");
        System.out.println(playlist);
        System.out.println();

        // 3. Eliminar una canción por nombre
        playlist.remove("Neon Nights");
        System.out.println("--- Después de eliminar 'Neon Nights' ---");
        System.out.println(playlist);
        System.out.println();

        // 4. Sustituir la última canción por otra nueva
        // El índice de la última canción siempre es (tamaño - 1)
        int ultimoIndice = playlist.size() - 1;
        playlist.set(ultimoIndice, "Último Acorde (Nueva)");
        System.out.println("--- Después de sustituir la última canción ---");
        System.out.println(playlist);
        System.out.println();

        // 5. Mostrar información requerida
        System.out.println("=== ESTADÍSTICAS Y CONSULTAS ===");
        
        // o lista completa
        System.out.println("• Lista completa actual: " + playlist);
        
        // o tamaño
        System.out.println("• Tamaño de la playlist: " + playlist.size() + " canciones.");
        
        // o primera canción
        System.out.println("• Primera canción: " + playlist.get(0));
        
        // o última canción
        System.out.println("• Última canción: " + playlist.get(playlist.size() - 1));
        
        // o si existe una canción concreta
        String cancionBuscar = "Algoritmo de Amor";
        boolean existe = playlist.contains(cancionBuscar);
        System.out.println("• ¿Existe la canción '" + cancionBuscar + "'?: " + (existe ? "Sí" : "No"));
        
        // o posición de una canción concreta
        // indexOf devuelve el índice (0-based), sumamos 1 para mostrar la posición humana real
        int posicion = playlist.indexOf(cancionBuscar);
        if (posicion != -1) {
            System.out.println("• La canción '" + cancionBuscar + "' está en la posición: " + (posicion + 1) + " (Índice: " + posicion + ")");
        } else {
            System.out.println("• La canción '" + cancionBuscar + "' no se encuentra en la lista.");
        }
    }
}