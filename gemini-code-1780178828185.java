public class RetoMain {
    public static void main(String[] args) {
        // 1. Crea 3 jugadores con nombres distintos
        Jugador jugador1 = new Jugador("Parziblox");
        Jugador jugador2 = new Jugador("KevSodier");
        Jugador jugador3 = new Jugador("AlphaNiko");

        // 2. Crea un torneo con esos 3 jugadores
        Torneo torneo = new Torneo("Alicante Cyber Cup", jugador1, jugador2, jugador3);

        // 3. Registra varias partidas según requisitos del guion:
        
        // (a) Un jugador con una partida de 1 sola ronda
        torneo.registrarPartida(jugador1, 150);

        // (b) Un jugador con una partida de 4 o más rondas
        torneo.registrarPartida(jugador2, 40, 55, 70, 65);

        // (c) Un jugador con dos partidas diferentes
        torneo.registrarPartida(jugador3, 80, 90);
        torneo.registrarPartida(jugador3, 110, 45, 60);

        // (d) Un jugador con una llamada sin puntuaciones (Varargs vacío)
        torneo.registrarPartida(jugador1);

        // 4. Llama a mostrarResumenTorneo para ver el estado final de todos
        torneo.mostrarResumenTorneo();

        // Extra: Anunciamos al ganador para cerrar el evento
        torneo.anunciarGanador();
    }
}