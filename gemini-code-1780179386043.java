/**
 * Justificación: Se implementa como una clase POJO normal porque representa 
 * un proceso de negocio que gestiona un estado y cuyo precio o butaca 
 * podrían ser susceptibles de modificaciones o cálculos dinámicos avanzados.
 */
public class Reserva {
    // Atributos privados encapsulados
    private String nombreCliente;
    private String dniCliente;
    private Pelicula pelicula;
    private Sala sala;
    private String butaca;
    private double precio;

    // Constructor completo
    public Reserva(String nombreCliente, String dniCliente, Pelicula pelicula, Sala sala, String butaca, double precio) {
        this.nombreCliente = nombreCliente;
        this.dniCliente = dniCliente;
        this.pelicula = pelicula;
        this.sala = sala;
        this.butaca = butaca;
        this.precio = precio;
    }

    // Método para devolver el resumen en String solicitado
    public String obtenerResumen() {
        return "Reserva de " + nombreCliente + " (DNI " + dniCliente + ") para '" 
                + pelicula.titulo() + "' en " + sala.nombre() + ", butaca " 
                + butaca + ", precio " + precio + " €";
    }

    // Getters y Setters necesarios
    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getDniCliente() { return dniCliente; }
    public void setDniCliente(String dniCliente) { this.dniCliente = dniCliente; }

    public Pelicula getPelicula() { return pelicula; }
    public void setPelicula(Pelicula pelicula) { this.pelicula = pelicula; }

    public Sala getSala() { return sala; }
    public void setSala(Sala sala) { this.sala = sala; }

    public String getButaca() { return butaca; }
    public void setButaca(String butaca) { this.butaca = butaca; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
}