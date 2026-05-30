public class Habitacion {
    // Atributos
    private int numero;
    private boolean ocupada;
    private double precioNoche;

    // Constructor: por defecto empieza libre (ocupada = false)
    public Habitacion(int numero, double precioNoche) {
        this.numero = numero;
        this.precioNoche = precioNoche;
        this.ocupada = false; 
    }

    // Comprueba si está libre
    public boolean estaLibre() {
        return !this.ocupada;
    }

    // Marca la habitación como ocupada
    public void ocupar() {
        this.ocupada = true;
        System.out.println("-> La habitación " + numero + " ahora está ocupada.");
    }

    // Métodos Getters
    public int getNumero() {
        return numero;
    }

    public double getPrecioNoche() {
        return precioNoche;
    }
}