public class GestorReservas {

    // 1. Comprobar si el cliente cumple la edad mínima
    public static boolean puedeVerPelicula(int edadCliente, Pelicula pelicula) {
        return edadCliente >= pelicula.edadMinima();
    }

    // 2. Calcular el precio final en función de los extras
    public static double calcularPrecioFinal(Sala sala, Pelicula pelicula) {
        double precioBase = 8.0; // Nuestro precio base inventado

        // Si la sala tiene sonido envolvente, sumamos 1.5€
        if (sala.tieneSonidoEnvolvente()) {
            precioBase += 1.5;
        }

        // Si la película dura más de 150 minutos, sumamos 1€
        if (pelicula.duracion() > 150) {
            precioBase += 1.0;
        }

        return precioBase;
    }

    // 3. Generar el objeto ResultadoReserva validando las reglas de negocio
    public static ResultadoReserva generarResultado(Pelicula pelicula, int edadCliente) {
        if (!puedeVerPelicula(edadCliente, pelicula)) {
            return new ResultadoReserva("Reserva rechazada: edad insuficiente", "EDAD");
        }
        
        return new ResultadoReserva("Reserva confirmada", "OK");
    }
}