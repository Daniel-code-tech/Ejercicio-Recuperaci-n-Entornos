public class Jugador {
    // Atributos
    private String nombre;
    private int totalPuntos;
    private int partidasJugadas;
    private int mejorPuntuacionEnUnaPartida;

    // Constructor
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.totalPuntos = 0;
        this.partidasJugadas = 0;
        this.mejorPuntuacionEnUnaPartida = 0;
    }

    // Método con Varargs para actualizar estadísticas
    public void actualizarEstadisticas(int... puntuaciones) {
        // Caso a: Si no han llegado puntuaciones
        if (puntuaciones == null || puntuaciones.length == 0) {
            System.out.println("El jugador " + nombre + " no tiene puntuaciones en esta partida.");
            return; // Cortamos la ejecución sin actualizar nada
        }

        // Caso b: Si llegan puntuaciones
        int sumaPartida = 0;
        for (int puntosRonda : puntuaciones) {
            sumaPartida += puntosRonda;
        }

        this.totalPuntos += sumaPartida;
        this.partidasJugadas++;

        if (sumaPartida > this.mejorPuntuacionEnUnaPartida) {
            this.mejorPuntuacionEnUnaPartida = sumaPartida;
        }

        // Caso c: Mostrar resultado de la partida
        System.out.println("Jugador [" + nombre + "] ha conseguido [" + sumaPartida + "] puntos en esta partida.");
    }

    // Muestra el estado del objeto y calcula la media al vuelo
    public void mostrarResumen() {
        // Evitamos la división por cero si no ha jugado partidas
        double media = (partidasJugadas == 0) ? 0.0 : (double) totalPuntos / partidasJugadas;

        System.out.println("=== RESUMEN JUGADOR: " + nombre + " ===");
        System.out.println("Puntos Totales: " + totalPuntos);
        System.out.println("Partidas Jugadas: " + partidasJugadas);
        System.out.println("Mejor Partida: " + mejorPuntuacionEnUnaPartida + " puntos");
        System.out.printf("Media de puntos por partida: %.2f\n", media);
        System.out.println("------------------------------------");
    }

    // Getters necesarios para que la clase Torneo pueda evaluar al ganador
    public String getNombre() {
        return nombre;
    }

    public int getTotalPuntos() {
        return totalPuntos;
    }
}