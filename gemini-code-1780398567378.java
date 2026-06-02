// ==========================================
// CLASE PADRE: Cuenta
// ==========================================
class Cuenta {
    private String titular;
    protected double saldo; // protected para que las subclases puedan modificarlo directamente

    // Constructor
    public Cuenta(String titular, double saldoInicial) {
        this.titular = titular;
        this.saldo = saldoInicial;
    }

    // Métodos
    public void ingresar(double cantidad) {
        if (cantidad > 0) {
            this.saldo += cantidad;
        }
    }

    public void retirar(double cantidad) {
        if (cantidad <= this.saldo) {
            this.saldo -= cantidad;
        } else {
            System.out.println("Saldo insuficiente para retirar: " + cantidad);
        }
    }

    public void mostrarInfo() {
        System.out.print("Titular: " + titular + " | Saldo: " + saldo);
    }
}

// ==========================================
// SUBCLASE: CuentaAhorro
// ==========================================
class CuentaAhorro extends Cuenta {
    private double interes;

    // Constructor completo
    public CuentaAhorro(String titular, double saldoInicial, double interes) {
        super(titular, saldoInicial); // Llama al constructor del padre
        this.interes = interes;
    }

    // Aplica el interés aumentando el saldo
    public void aplicarInteres() {
        double ganancia = this.saldo * (this.interes / 100);
        ingresar(ganancia); 
    }

    // Sobrescribe el método del padre
    @Override
    public void mostrarInfo() {
        super.mostrarInfo(); // 1. Llamar a mostrarInfo del padre
        System.out.println(" | Interés: " + interes + "%"); // 2. Añadir el interés
    }
}

// ==========================================
// SUBCLASE: CuentaPremium
// ==========================================
class CuentaPremium extends Cuenta {
    private double limiteDescubierto;

    // Constructor completo
    public CuentaPremium(String titular, double saldoInicial, double limiteDescubierto) {
        super(titular, saldoInicial);
        this.limiteDescubierto = limiteDescubierto;
    }

    // Sobrescribe la regla de retiro para permitir saldo negativo
    @Override
    public void retirar(double cantidad) {
        // Permite retirar si el saldo actual + el límite es mayor o igual a la cantidad
        if (cantidad <= (this.saldo + this.limiteDescubierto)) {
            this.saldo -= cantidad;
        } else {
            System.out.println("Límite de descubierto excedido para retirar: " + cantidad);
        }
    }

    @Override
    public void mostrarInfo() {
        super.mostrarInfo();
        System.out.println(" | Límite Descubierto: " + limiteDescubierto);
    }
}

// ==========================================
// CLASE PRINCIPAL: Main
// ==========================================
public class Main { 
    public static void main(String[] args) { 
        // Prueba de CuentaAhorro
        CuentaAhorro ca = new CuentaAhorro("Ana", 1000, 5); 
        ca.aplicarInteres(); // 1000 + 5% = 1050
        ca.mostrarInfo(); 
        
        System.out.println("-----"); 
        
        // Prueba de CuentaPremium
        CuentaPremium cp = new CuentaPremium("Luis", 500, 300); 
        cp.retirar(700); // 500 - 700 = -200 (Permitido, ya que el límite es hasta -300)
        cp.mostrarInfo(); 
    } 
}