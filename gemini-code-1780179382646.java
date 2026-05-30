/**
 * Justificación: Se usa 'record' porque la configuración física de la sala 
 * (asientos, tecnología de sonido) es fija para la simulación del cine.
 */
public record Sala(String nombre, int totalButacas, boolean tieneSonidoEnvolvente) {}