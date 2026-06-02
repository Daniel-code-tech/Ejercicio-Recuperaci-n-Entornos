// =========================================================================
// 1) EXCEPCIÓN CHECKED (Error de negocio recuperable)
// =========================================================================
class EntradasAgotadasException extends Exception {
    public EntradasAgotadasException(String mensaje) {
        super(mensaje);
    }
}

// =========================================================================
// 2) EXCEPCIÓN UNCHECKED (Error de uso/Precondición violada)
// =========================================================================
class DatosCompraInvalidosException extends RuntimeException {
    public DatosCompraInvalidosException(String mensaje) {
        super(mensaje);
    }
}

// =========================================================================
// 3) CLASE CONCIERTO
// =========================================================================
class Concierto {
    private String nombre;
    private int entradasDisponibles;

    public Concierto(String nombre, int entradasDisponibles) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new DatosCompraInvalidosException("El nombre del concierto no puede estar vacío.");
        }
        if (entradasDisponibles < 0) {
            throw new DatosCompraInvalidosException("Las entradas iniciales no pueden ser negativas.");
        }
        this.nombre = nombre;
        this.entradasDisponibles = entradasDisponibles;
    }

    // Método de compra
    public void comprarEntradas(String comprador, int cantidad) throws EntradasAgotadasException {
        // Validaciones de uso correcto (Unchecked Exceptions)
        if (comprador == null || comprador.trim().isEmpty()) {
            throw new DatosCompraInvalidosException("El nombre del comprador no es válido.");
        }
        if (cantidad <= 0) {
            throw new DatosCompraInvalidosException("La cantidad de entradas pedidas debe ser mayor que 0.");
        }

        // Validación de regla de negocio (Checked Exception)
        if (cantidad > this.entradasDisponibles) {
            throw new EntradasAgotadasException("No se pudo procesar la compra. Solicitadas: " 
                    + cantidad + " | Disponibles: " + this.entradasDisponibles);
        }

        // Flujo de éxito
        this.entradasDisponibles -= cantidad;
        System.out.println("✨ ¡Compra exitosa! " + comprador + " ha adquirido " + cantidad 
                + " entradas para " + this.nombre + ". (Quedan: " + this.entradasDisponibles + ")");
    }

    // Getter auxiliar para el Main
    public int getEntradasDisponibles() {
        return entradasDisponibles;
    }
}

// =========================================================================
// 4) PROGRAMA PRINCIPAL
// =========================================================================
public class Main {
    public static void main(String[] args) {
        // Creamos un concierto con un aforo pequeño para las pruebas
        Concierto concierto = new Concierto("Rock en las Aulas 2026", 10);
        System.out.println("--- INICIO DE LA PLATAFORMA DE ENTRADAS --- \n");

        // CASO 1: Compra Correcta
        try {
            concierto.comprarEntradas("Ana López", 3); // Quedan 7
        } catch (EntradasAgotadasException e) {
            System.out.println("Error de negocio: " + e.getMessage());
        }

        // CASO 2: Nombre inválido (Provoca Unchecked Exception)
        try {
            concierto.comprarEntradas("", 2);
        } catch (DatosCompraInvalidosException e) {
            System.out.println("❌ Error de programación/uso: " + e.getMessage());
        }

        // CASO 3: Cantidad inválida (Provoca Unchecked Exception)
        try {
            concierto.comprarEntradas("Carlos", -5);
        } catch (DatosCompraInvalidosException e) {
            System.out.println("❌ Error de programación/uso: " + e.getMessage());
        }

        // CASO 4: Más entradas de las disponibles (Provoca Checked Exception)
        try {
            // Quedaban 7 entradas, intentamos comprar 8
            concierto.comprarEntradas("Luis Gómez", 8);
        } catch (EntradasAgotadasException e) {
            System.out.println("⚠️ Error de negocio: " + e.getMessage());
        }

        // CASO 5: Compra final correcta para agotar el aforo
        try {
            concierto.comprarEntradas("Marta", 7); // Quedan 0
        } catch (EntradasAgotadasException e) {
            System.out.println("Error de negocio: " + e.getMessage());
        }
    }
}