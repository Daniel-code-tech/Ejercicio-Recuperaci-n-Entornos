/**
 * Justificación: Se implementa como 'record' porque representa una entidad 
 * de datos inmutable cuyos atributos no van a cambiar durante la sesión.
 */
public record Pelicula(String titulo, int duracion, int edadMinima) {}