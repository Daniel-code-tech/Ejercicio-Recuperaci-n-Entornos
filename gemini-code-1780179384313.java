/**
 * Justificación: Es el caso perfecto para un 'record'. Es un portador de datos 
 * momentáneo que solo empaqueta el estado final ("OK", "EDAD") y un mensaje 
 * para ser leído, sin ninguna necesidad de cambiar sus valores.
 */
public record ResultadoReserva(String mensaje, String codigoInterno) {}