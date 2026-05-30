public class GestorTareas {
    // Atributos de instancia (inicializados por defecto a null)
    private Tarea tarea1;
    private Tarea tarea2;
    private Tarea tarea3;

    // Crea y almacena una tarea en el primer hueco libre que encuentre
    public void crearNuevaTarea(String titulo, String descripcion) {
        if (tarea1 == null) {
            tarea1 = new Tarea(titulo, descripcion);
            System.out.println("✅ Tarea guardada con éxito en la posición 1.");
        } else if (tarea2 == null) {
            tarea2 = new Tarea(titulo, descripcion);
            System.out.println("✅ Tarea guardada con éxito en la posición 2.");
        } else if (tarea3 == null) {
            tarea3 = new Tarea(titulo, descripcion);
            System.out.println("✅ Tarea guardada con éxito en la posición 3.");
        } else {
            System.out.println("❌ Error: No se pueden crear más tareas. El gestor está lleno (máx. 3).");
        }
    }

    // Recorre y lista las tareas existentes controlando los nulos
    public void listarTareas() {
        System.out.println("\n=== LISTADO DE TAREAS ===");
        
        System.out.print("Tarea 1: ");
        if (tarea1 != null) { System.out.println(); tarea1.mostrarInfo(); } 
        else { System.out.println("(vacía)"); }

        System.out.print("Tarea 2: ");
        if (tarea2 != null) { System.out.println(); tarea2.mostrarInfo(); } 
        else { System.out.println("(vacía)"); }

        System.out.print("Tarea 3: ");
        if (tarea3 != null) { System.out.println(); tarea3.mostrarInfo(); } 
        else { System.out.println("(vacía)"); }
        System.out.println("=========================");
    }

    // Busca y devuelve el objeto según el número introducido
    public Tarea buscarTareaPorNumero(int numero) {
        if (numero == 1) return tarea1;
        if (numero == 2) return tarea2;
        if (numero == 3) return tarea3;
        return null; // Si no es válido o está vacío devolverá null
    }

    // Método extra: Comprueba si el gestor está completamente lleno
    public boolean estaLleno() {
        return tarea1 != null && tarea2 != null && tarea3 != null;
    }
}