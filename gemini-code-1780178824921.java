public class Torneo {
    // Atributos
    private String nombre;
    private Jugador j1;
    private Jugador j2;
    private Jugador j3;

    // Constructor
    public Torneo(String nombre, Jugador j1, Jugador j2, Jugador j3) {
        this.nombre = nombre;
        this.j1 = j1;
        this.j2 = j2;
        this.j3 = j3;
    }

    // Registra una partida delegando al varargs del jugador
    public void registrarPartida(Jugador jugador, int... puntuaciones) {
        System.out.println("\nRegistrando partida para [" + jugador.getNombre() + "] en el torneo [" + this.nombre + "]");
        jugador.actualizarEstadisticas(puntuaciones);
    }

    // Muestra el resumen de los tres competidores
    public void mostrarResumenTorneo() {
        System.out.println("\n======= RESUMEN DEL TORNEO: " + nombre.toUpperCase() + " =======");
        j1.mostrarResumen();
        j2.mostrarResumen();
        j3.mostrarResumen();
    }

    // Compara las puntuaciones para anunciar al líder de la tabla
    public void anunciarGanador() {
        System.out.println("\n=== ANUNCIANDO AL GANADOR ===");
        Jugador ganador = j1;

        if (j2.getTotalPuntos() > ganador.getTotalPuntos()) {
            ganador = j2;
        }
        if (j3.getTotalPuntos() > ganador.getTotalPuntos()) {
            ganador = j3;
        }

        System.out.println("🏆 El ganador del torneo [" + this.nombre + "] es [" + ganador.getNombre() + "] con [" + ganador.getTotalPuntos() + "] puntos.");
    }
}