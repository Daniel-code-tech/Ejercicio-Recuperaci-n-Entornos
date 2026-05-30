public class MainCine {
    public static void main(String[] args) {
        // 1. Creamos una película de estreno y una sala con tecnología premium
        // (Nota: al ser records, no usamos getters tradicionales, se accede mediante .titulo(), .duracion(), etc.)
        Pelicula peliculaTop = new Pelicula("Interstellar", 169, 13); 
        Sala salaPremium = new Sala("SALA 1 IMAX", 150, true);

        System.out.println("--- CONFIGURACIÓN DE LA SESIÓN ---");
        System.out.println("Película: " + peliculaTop.titulo() + " | Edad Mínima: " + peliculaTop.edadMinima() + " años");
        System.out.println("Sala: " + salaPremium.nombre() + " | Sonido Envolvente: " + (salaPremium.tieneSonidoEnvolvente() ? "Sí" : "No"));
        System.out.println("----------------------------------\n");

        // Calculamos el precio común de la entrada basándonos en la sala y la película
        double precioEntrada = GestorReservas.calcularPrecioFinal(salaPremium, peliculaTop);

        // ==========================================
        // CASO 1: Cliente que SÍ cumple la edad (24 años)
        // ==========================================
        int edadCliente1 = 24;
        ResultadoReserva resultado1 = GestorReservas.generarResultado(peliculaTop, edadCliente1);
        
        System.out.println("PROCESANDO RESERVA 1...");
        if (resultado1.codigoInterno().equals("OK")) {
            Reserva reserva1 = new Reserva("Kevin Sodier", "12345678X", peliculaTop, salaPremium, "FILA 5 - ASIENTO 8", precioEntrada);
            System.out.println(reserva1.obtenerResumen());
        }
        System.out.println("Estado de operación: " + resultado1.mensaje() + " [Código: " + resultado1.codigoInterno() + "]");
        System.out.println("----------------------------------------------------------------------\n");

        // ==========================================
        // CASO 2: Cliente que NO cumple la edad (11 años)
        // ==========================================
        int edadCliente2 = 11;
        ResultadoReserva resultado2 = GestorReservas.generarResultado(peliculaTop, edadCliente2);

        System.out.println("PROCESANDO RESERVA 2...");
        if (resultado2.codigoInterno().equals("OK")) {
            Reserva reserva2 = new Reserva("Dani Coll", "87654321Z", peliculaTop, salaPremium, "FILA 2 - ASIENTO 4", precioEntrada);
            System.out.println(reserva2.obtenerResumen());
        } else {
            System.out.println("⚠️ Alerta del sistema: No se pudo emitir el ticket físico.");
        }
        System.out.println("Estado de operación: " + resultado2.mensaje() + " [Código: " + resultado2.codigoInterno() + "]");
        System.out.println("----------------------------------------------------------------------");
    }
}