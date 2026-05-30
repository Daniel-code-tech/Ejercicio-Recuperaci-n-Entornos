public class Tarea {
    // Atributos de instancia
    private String titulo;
    private String descripcion;
    private EstadoTarea estado;

    // Atributo estático (Contador global compartido por todas las instancias)
    private static int totalTareas = 0;

    // Constructor
    public Tarea(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = EstadoTarea.PENDIENTE; // Por defecto empieza PENDIENTE
        totalTareas++; // Incrementa el contador global cada vez que nace una tarea
    }

    // Método de instancia para mostrar los datos de la tarea
    public void mostrarInfo() {
        System.out.println("   📌 Título: " + titulo);
        System.out.println("   💬 Descripción: " + descripcion);
        System.out.println("   ⚙️ Estado: " + estado);
    }

    // Método estático para obtener el total de tareas creadas
    public static int getTotalTareas() {
        return totalTareas;
    }

    // Getters y Setters
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public EstadoTarea getEstado() { return estado; }
    public void setEstado(EstadoTarea estado) { this.estado = estado; }
}