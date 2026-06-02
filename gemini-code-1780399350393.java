import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// =========================================================================
// CLASE GENÉRICA: Ranking (Restringida a tipos que implementen Comparable)
// =========================================================================
class Ranking<T extends Comparable<T>> {
    // Requisito: Usar internamente un ArrayList
    private final List<T> elementos;

    public Ranking() {
        this.elementos = new ArrayList<>();
    }

    public void agregar(T elemento) {
        if (elemento != null) {
            this.elementos.add(elemento);
        }
    }

    public T obtenerMayor() {
        if (elementos.isEmpty()) return null;
        // Collections.max utiliza el orden natural dictado por compareTo
        return Collections.max(elementos);
    }

    public T obtenerMenor() {
        if (elementos.isEmpty()) return null;
        // Collections.min hace lo propio para encontrar el menor
        return Collections.min(elementos);
    }

    public void mostrarOrdenado() {
        if (elementos.isEmpty()) {
            System.out.println("El ranking está vacío.");
            return;
        }
        // Creamos una copia temporal para no alterar el orden de inserción original del mapa
        List<T> copiaOrdenada = new ArrayList<>(this.elementos);
        
        // Requisito: Usar Collections.sort
        Collections.sort(copiaOrdenada);
        
        // Mostramos de mayor puntuación a menor (invertido) para que parezca un ranking real
        System.out.println("--- CLASIFICACIÓN DEL RANKING ---");
        for (int i = copiaOrdenada.size() - 1; i >= 0; i--) {
            System.out.println((copiaOrdenada.size() - i) + ". " + copiaOrdenada.get(i));
        }
    }
}

// =========================================================================
// CLASE PROPIA: Jugador (Implementa Comparable)
// =========================================================================
class Jugador implements Comparable<Jugador> {
    private final String nombre;
    private final int puntos;

    public Jugador(String nombre, int puntos) {
        this.nombre = nombre;
        this.puntos = puntos;
    }

    public String getNombre() { return nombre; }
    public int getPuntos() { return puntos; }

    // Requisito: Comparar por puntos
    @Override
    public int compareTo(Jugador otro) {
        return Integer.compare(this.puntos, otro.puntos);
    }

    @Override
    public String toString() {
        return nombre + " (" + puntos + " pts)";
    }
}

// =========================================================================
// PROGRAMA PRINCIPAL: Main de Pruebas
// =========================================================================
public class Main {
    public static void main(String[] args) {
        // -----------------------------------------------------------------
        // PRUEBA 1: Funciona con una Clase Propia (Jugador)
        // -----------------------------------------------------------------
        System.out.println("=== RANKING DE JUGADORES ===");
        Ranking<Jugador> rankingJugadores = new Ranking<>(); 
        rankingJugadores.agregar(new Jugador("Ana", 120)); 
        rankingJugadores.agregar(new Jugador("Luis", 90)); 
        rankingJugadores.agregar(new Jugador("Eva", 200)); 

        System.out.println("Líder actual: " + rankingJugadores.obtainMayor()); // Eva (200 pts)
        System.out.println("Colista actual: " + rankingJugadores.obtainMenor()); // Luis (90 pts)
        rankingJugadores.mostrarOrdenado();
        
        // -----------------------------------------------------------------
        // PRUEBA 2: Funciona con Integer (Soporta Comparable de forma nativa)
        // -----------------------------------------------------------------
        System.out.println("\n=== RANKING DE ENTEROS (PUNTUACIONES) ===");
        Ranking<Integer> rankingNumeros = new Ranking<>();
        rankingNumeros.agregar(45);
        rankingNumeros.add(100);
        rankingNumeros.add(12);
        
        System.out.println("Mayor número: " + rankingNumeros.obtainMayor());
        rankingNumeros.mostrarOrdenado();

        // -----------------------------------------------------------------
        // PRUEBA 3: Funciona con String (Orden alfabético nativo)
        // -----------------------------------------------------------------
        System.out.println("\n=== RANKING DE STRINGS (ALFABÉTICO) ===");
        Ranking<String> rankingPalabras = new Ranking<>();
        rankingPalabras.add("Zeta");
        rankingPalabras.add("Alfa");
        rankingPalabras.add("Delta");
        
        System.out.println("Mayor (último en el diccionario): " + rankingPalabras.obtainMayor());
        rankingPalabras.mostrarOrdenado();
    }
}