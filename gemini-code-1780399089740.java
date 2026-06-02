import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Crear una lista vacía de películas
        List<String> maratonCine = new ArrayList<>();

        // 2. Añadir 7 películas reales o inventadas al final de la lista
        maratonCine.add("Inception");
        maratonCine.add("Interstellar");
        maratonCine.add("Pulp Fiction");
        maratonCine.add("El Padrino");
        maratonCine.add("Matrix");
        maratonCine.add("Gladiator");
        maratonCine.add("Avatar");

        // 3. Insertar una película llamada "Película sorpresa" en la primera posición (índice 0)
        maratonCine.add(0, "Película sorpresa");

        // 4. Insertar una película llamada "Estreno recomendado" en la posición 3 (índice 2)
        maratonCine.add(2, "Estreno recomendado");

        // 5. Eliminar una película concreta por su nombre
        maratonCine.remove("Matrix");

        // 6. Eliminar la película situada en la última posición (tamaño - 1)
        maratonCine.remove(maratonCine.size() - 1);

        // 7. Sustituir la película de la posición 2 (índice 1) por "Clásico imprescindible"
        maratonCine.set(1, "Clásico imprescindible");

        // 8. Añadir otra vez una película que ya exista para que esté repetida
        // Vamos a repetir "Inception" al final de la lista
        maratonCine.add("Inception");

        // 9. Mostrar por pantalla los datos solicitados
        System.out.println("=== CONSULTAS DE LA LISTA DE PELÍCULAS ===");
        
        // o la lista completa
        System.out.println("• Lista completa: " + maratonCine);
        
        // o el número total de películas
        System.out.println("• Número total de películas: " + maratonCine.size());
        
        // o la primera película
        System.out.println("• Primera película: " + maratonCine.get(0));
        
        // o la última película
        System.out.println("• Última película: " + maratonCine.get(maratonCine.size() - 1));
        
        // o la película que está en la posición 4 (índice 3)
        System.out.println("• Película en la posición 4: " + maratonCine.get(3));
        
        // o si existe una película llamada "Película sorpresa"
        System.out.println("• ¿Existe 'Película sorpresa'?: " + (maratonCine.contains("Película sorpresa") ? "Sí" : "No"));
        
        // o la posición de la primera aparición de una película concreta ("Inception")
        String peliRepetida = "Inception";
        int primeraAparicion = maratonCine.indexOf(peliRepetida);
        System.out.println("• Primera aparición de '" + peliRepetida + "': Posición " + (primeraAparicion + 1) + " (Índice: " + primeraAparicion + ")");
        
        // o la posición de la última aparición de esa misma película
        int ultimaAparicion = maratonCine.lastIndexOf(peliRepetida);
        System.out.println("• Última aparición de '" + peliRepetida + "': Posición " + (ultimaAparicion + 1) + " (Índice: " + ultimaAparicion + ")");

        System.out.println("\n=== SUBLISTA SOLICITADA ===");
        // 10. Mostrar una sublista con las películas comprendidas entre la posición 2 y la 5.
        // Recordatorio de subList(desde, hasta): El índice "desde" es inclusivo y el "hasta" es exclusivo.
        // Posición 2 humana -> índice 1. Posición 5 humana -> índice 4. Para incluir el índice 4 ponemos 5 en el límite.
        List<String> subLista = maratonCine.subList(1, 5);
        System.out.println("• Sublista (posiciones 2 a 5): " + subLista);
    }
}