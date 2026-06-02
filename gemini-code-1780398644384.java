// =========================================================================
// 1) INTERFAZ RESTRINGIDA (SEALED)
// =========================================================================
sealed interface Ticket permits NormalTicket, VipTicket, DescuentoTicket {
    String codigo();
    double precioFinal();

    // Método default reutilizable
    default String resumen() {
        // Obtenemos el nombre simple de la clase (NormalTicket, VipTicket, etc.)
        String tipo = this.getClass().getSimpleName();
        return "[" + codigo() + "] " + tipo + " -> " + precioFinal() + "€";
    }
}

// =========================================================================
// 2) IMPLEMENTACIONES DE TICKET
// =========================================================================

// --- NormalTicket ---
final class NormalTicket implements Ticket {
    private String codigo;
    private double precioBase;
    private int edad;

    public NormalTicket(String codigo, double precioBase, int edad) {
        this.codigo = codigo;
        this.precioBase = precioBase;
        this.edad = edad;
    }

    @Override
    public String codigo() { return this.codigo; }

    @Override
    public double precioFinal() {
        if (edad < 14) {
            return precioBase * 0.80;  // -20%
        } else if (edad >= 65) {
            return precioBase * 0.70;  // -30%
        }
        return precioBase;
    }
}

// --- VipTicket ---
final class VipTicket implements Ticket {
    private String codigo;
    private double precioBase;

    public VipTicket(String codigo, double precioBase) {
        this.codigo = codigo;
        this.precioBase = precioBase;
    }

    @Override
    public String codigo() { return this.codigo; }

    @Override
    public double precioFinal() {
        return precioBase + 5.0; // +5€ de recargo
    }
}

// --- DescuentoTicket (Clase base de descuentos, declarada como non-sealed) ---
non-sealed class DescuentoTicket implements Ticket {
    private String codigo;
    protected double precioBase; // protected para que sus hijos accedan fácilmente

    public DescuentoTicket(String codigo, double precioBase) {
        this.codigo = codigo;
        this.precioBase = precioBase;
    }

    @Override
    public String codigo() { return this.codigo; }

    @Override
    public double precioFinal() {
        return precioBase * 0.90; // Descuento genérico del -10%
    }
}

// --- EstudianteTicket (Hijo de DescuentoTicket) ---
final class EstudianteTicket extends DescuentoTicket {
    public EstudianteTicket(String codigo, double precioBase) {
        super(codigo, precioBase);
    }

    @Override
    public double precioFinal() {
        // Aplica el -10% de la clase padre
        double precioConDescuentoBase = super.precioFinal();
        // Si el precio base original es >= 20, resta 5€ extra
        if (this.precioBase >= 20) {
            return precioConDescuentoBase - 5.0;
        }
        return precioConDescuentoBase;
    }
}

// --- ParoTicket (Hijo de DescuentoTicket) ---
final class ParoTicket extends DescuentoTicket {
    public ParoTicket(String codigo, double precioBase) {
        super(codigo, precioBase);
    }

    @Override
    public double precioFinal() {
        // Descuento total del -20% (10% inicial + 10% adicional)
        return this.precioBase * 0.80;
    }
}

// =========================================================================
// 3) CLASE TICKETOFFICE (GESTIÓN)
// =========================================================================
class TicketOffice {
    
    // Suma el precioFinal de todos los tickets
    public static double total(Ticket[] tickets) {
        double suma = 0;
        for (Ticket t : tickets) {
            if (t != null) {
                suma += t.precioFinal();
            }
        }
        return suma;
    }

    // Devuelve el ticket con mayor precioFinal
    public static Ticket masCaro(Ticket[] tickets) {
        if (tickets == null || tickets.length == 0) return null;
        
        Ticket caro = tickets[0];
        for (int i = 1; i < tickets.length; i++) {
            if (tickets[i] != null && tickets[i].precioFinal() > caro.precioFinal()) {
                caro = tickets[i];
            }
        }
        return caro;
    }

    // Imprime el resumen() de cada ticket
    public static void imprimir(Ticket[] tickets) {
        for (Ticket t : tickets) {
            if (t != null) {
                System.out.println(t.resumen());
            }
        }
    }
}

// =========================================================================
// CLASE DE PRUEBA (MAIN)
// =========================================================================
public class Main {
    public static void main(String[] args) {
        // Inicializamos un array con todos los tipos de tickets
        Ticket[] inventario = new Ticket[] {
            new NormalTicket("N-01", 10.0, 12),      // Niño: 10 - 20% = 8.0€
            new NormalTicket("N-02", 10.0, 30),      // Adulto: 10.0€
            new VipTicket("V-01", 50.0),             // VIP: 50 + 5 = 55.0€
            new DescuentoTicket("D-01", 20.0),       // Descuento base: 20 - 10% = 18.0€
            new EstudianteTicket("E-01", 25.0),      // Estudiante >=20: (25 - 10%) - 5 = 17.5€
            new ParoTicket("P-01", 30.0)             // Paro: 30 - 20% = 24.0€
        };

        System.out.println("--- DETALLE DE TICKETS ---");
        TicketOffice.imprimir(inventario);

        System.out.println("\n--- ESTADÍSTICAS ---");
        System.out.println("Caja total del día: " + TicketOffice.total(inventario) + "€");
        
        Ticket masCaro = TicketOffice.masCaro(inventario);
        if (masCaro != null) {
            System.out.println("Ticket más costoso: " + masCaro.codigo() + " (" + masCaro.precioFinal() + "€)");
        }
    }
}