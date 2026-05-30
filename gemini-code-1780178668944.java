public class Hotel {
    // Atributos
    private String nombre;
    private Habitacion h1;
    private Habitacion h2;
    private Habitacion h3;

    // Constructor: inicializa el nombre y las 3 habitaciones fijas
    public Hotel(String nombre) {
        this.nombre = nombre;
        this.h1 = new Habitacion(101, 80.0);
        this.h2 = new Habitacion(102, 90.0);
        this.h3 = new Habitacion(103, 100.0);
    }

    // Busca en orden la primera habitación que esté libre
    public Habitacion buscarHabitacionLibre() {
        if (h1.estaLibre()) {
            return h1;
        } else if (h2.estaLibre()) {
            return h2;
        } else if (h3.estaLibre()) {
            return h3;
        }
        
        // Si ninguna está libre
        System.out.println("⚠️ No hay habitaciones disponibles en este momento.");
        return null;
    }

    // Intenta realizar una reserva gestionando el flujo según la disponibilidad
    public void reservarHabitacion(int noches) {
        System.out.println("\n--- Solicitando nueva reserva de " + noches + " noches ---");
        
        Habitacion libre = buscarHabitacionLibre();
        
        if (libre != null) {
            // Creamos el objeto Reserva pasándole la habitación encontrada
            Reserva nuevaReserva = new Reserva(libre, noches);
            nuevaReserva.confirmar();
        } else {
            System.out.println("⛔ No se ha podido crear la reserva.");
        }
    }
}