import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

// ==========================================
// 1. CLASE STATION
// ==========================================
class Station {
    private final String nombre;
    private final String ciudad;
    private final int minutosParada;

    public Station(String nombre, String ciudad, int minutosParada) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.minutosParada = minutosParada;
    }

    public String getNombre() { return nombre; }
    public String getCiudad() { return ciudad; }
    public int getMinutosParada() { return minutosParada; }

    @Override
    public String toString() {
        return nombre + " - " + ciudad + " (" + minutosParada + " min)";
    }
}

// ==========================================
// 2, 3 y 4. PROGRAMA PRINCIPAL Y NAVEGACIÓN
// ==========================================
public class Main {
    public static void main(String[] args) {
        // d) Elección de colección: Usamos LinkedList (justificación al final)
        List<Station> ruta = new LinkedList<>();

        // Añadimos 7 estaciones de ejemplo
        ruta.add(new Station("Atocha", "Madrid", 5));
        ruta.add(new Station("Delicias", "Zaragoza", 3));
        ruta.add(new Station("Sants", "Barcelona", 6));
        ruta.add(new Station("Joaquín Sorolla", "Valencia", 4));
        ruta.add(new Station("Santa Justa", "Sevilla", 5));
        ruta.add(new Station("María Zambrano", "Málaga", 3));
        ruta.add(new Station("Campo de Grande", "Valladolid", 2));

        // Iniciamos el recorrido simulado
        recorrerRuta(ruta);
    }

    public static void recorrerRuta(List<Station> ruta) {
        if (ruta.isEmpty()) {
            System.out.println("❌ La ruta no tiene estaciones programadas.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        ListIterator<Station> it = ruta.listIterator();
        
        // Estación inicial: avanzamos a la primera
        Station actual = it.next();
        boolean haciaAdelante = true; // Control de dirección trackeada

        System.out.println("🚂 ¡Bienvenido a bordo! Iniciando viaje.");
        System.out.println("📍 Estación Actual: " + actual);
        
        mostrarMenu();

        boolean salir = false;
        while (!salir) {
            System.out.print("\nSelecciona una opción (5 para ver menú): ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 0 -> {
                    System.out.println("👋 Saliendo del recorrido ferroviario. ¡Buen viaje!");
                    salir = true;
                }
                case 1 -> { // Ir a la siguiente estación
                    // Si el usuario venía de ir hacia atrás, hay que ajustar el cursor de ListIterator
                    if (!haciaAdelante) {
                        if (it.hasNext()) it.next();
                        haciaAdelante = true;
                    }
                    
                    if (it.hasNext()) {
                        actual = it.next();
                        System.out.println("➡️ Avanzando... 📍 Estación Actual: " + actual);
                    } else {
                        System.out.println("⚠️ Fin de trayecto. No hay más estaciones por delante.");
                    }
                }
                case 2 -> { // Ir a la estación anterior
                    // Si el usuario venía de ir hacia adelante, ajustamos el cursor del ListIterator
                    if (haciaAdelante) {
                        if (it.hasPrevious()) it.previous();
                        haciaAdelante = false;
                    }
                    
                    if (it.hasPrevious()) {
                        actual = it.previous();
                        System.out.println("⬅️ Retrocediendo... 📍 Estación Actual: " + actual);
                    } else {
                        System.out.println("⚠️ Cabecera de línea. Estás en la primera estación, no puedes retroceder.");
                    }
                }
                case 3 -> { // Repetir estación actual
                    System.out.println("🔄 Repitiendo parada: 📍 Estación Actual: " + actual);
                }
                case 4 -> { // Mostrar todas las estaciones
                    System.out.println("📋 MAPA COMPLETO DE LA RUTA:");
                    for (int i = 0; i < ruta.size(); i++) {
                        String marca = (ruta.get(i).getNombre().equals(actual.getNombre())) ? " -> [ACTUAL]" : "";
                        System.out.println("  " + (i + 1) + ". " + ruta.get(i) + marca);
                    }
                }
                case 5 -> mostrarMenu();
                case 6 -> { // Insertar una nueva estación después de la actual
                    System.out.print("Nombre de la nueva estación: ");
                    String nom = scanner.nextLine();
                    System.out.print("Ciudad: ");
                    String ciu = scanner.nextLine();
                    System.out.print("Minutos de parada: ");
                    int min = scanner.nextInt();

                    Station nueva = new Station(nom, ciu, min);
                    
                    // Aseguramos que el iterador esté sincronizado con la posición lógica "después de la actual"
                    if (!haciaAdelante && it.hasNext()) {
                        it.next();
                        haciaAdelante = true;
                    }
                    
                    it.add(nueva); // Añade en la posición del cursor
                    // Tras el add(), el cursor queda justo DESPUÉS de la estación añadida. 
                    // Para que no salte el orden de ruta, reajustamos el iterador un paso atrás.
                    it.previous(); 
                    if (!haciaAdelante) haciaAdelante = true;

                    System.out.println("➕ Estación \"" + nom + "\" insertada con éxito.");
                }
                case 7 -> { // Eliminar la estación actual
                    // El método .remove() de ListIterator elimina el último elemento devuelto por next() o previous()
                    it.remove();
                    System.out.println("🗑️ Estación \"" + actual.getNombre() + "\" eliminada del recorrido.");

                    if (ruta.isEmpty()) {
                        System.out.println("❌ Ya no quedan estaciones en la ruta. El sistema se cerrará.");
                        salir = true;
                    } else if (it.hasNext()) {
                        // Si hay siguiente, se convierte en la actual
                        actual = it.next();
                        haciaAdelante = true;
                        System.out.println("📍 Nueva Estación Actual: " + actual);
                    } else if (it.hasPrevious()) {
                        // Si no hay siguiente pero hay anterior, pasamos a la anterior
                        actual = it.previous();
                        haciaAdelante = false;
                        System.out.println("📍 Nueva Estación Actual (Anterior): " + actual);
                    }
                }
                default -> System.out.println("❌ Opción inválida del menú.");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n--- MENÚ DE INTERACCIÓN ---");
        System.out.println("0 – Salir del recorrido");
        System.out.println("1 – Ir a la siguiente estación");
        System.out.println("2 – Ir a la estación anterior");
        System.out.println("3 – Repetir estación actual");
        System.out.println("4 – Mostrar todas las estaciones");
        System.out.println("5 – Volver a mostrar el menú");
        System.out.println("6 – Insertar nueva estación después de la actual");
        System.out.println("7 – Eliminar la estación actual");
    }
}