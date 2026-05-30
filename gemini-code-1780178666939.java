public class Reserva {
    // Atributos
    private Habitacion habitacion;
    private int noches;

    // Constructor
    public Reserva(Habitacion habitacion, int noches) {
        this.habitacion = habitacion;
        this.noches = noches;
    }

    // Calcula, muestra y devuelve el importe total
    public double calcularImporte() {
        double importeTotal = habitacion.getPrecioNoche() * noches;
        System.out.println("   [Importe total por " + noches + " noches: " + importeTotal + "€]");
        return importeTotal;
    }

    // Confirma la reserva delegando en los métodos de Habitacion
    public void confirmar() {
        if (habitacion.estaLibre()) {
            habitacion.ocupar();
            System.out.println("✅ Reserva confirmada en la habitación " + habitacion.getNumero());
            calcularImporte();
        } else {
            System.out.println("❌ La habitación " + habitacion.getNumero() + " no está disponible.");
        }
    }
}