// ==========================================
// 1️⃣ y 2️⃣ ENUM: TipoTransporte con Switch Expresión
// ==========================================
enum TipoTransporte {
    BUS, TREN, AVION;

    // Método que implementa las reglas de precio usando el switch moderno de Java
    public double calcularPrecio(double distancia) {
        return switch (this) {
            case BUS   -> distancia * 0.08;
            case TREN  -> distancia * 0.12;
            case AVION -> (distancia * 0.30) + 20.0;
        };
    }
}

// ==========================================
// 3️⃣ CLASE: Viaje
// ==========================================
class Viaje {
    private String destino;
    private double distancia;
    private TipoTransporte transporte;

    // Constructor completo
    public Viaje(String destino, double distancia, TipoTransporte transporte) {
        this.destino = destino;
        this.distancia = distancia;
        this.transporte = transporte;
    }

    // Calcula el precio delegando en el enum
    public double precioFinal() {
        return this.transporte.calcularPrecio(this.distancia);
    }

    // Método para imprimir exactamente con el formato solicitado
    public void imprimirResultado() {
        // %.0f elimina los decimales en la salida para que quede idéntico al ejemplo
        System.out.printf("%s -> %.0fkm %s -> %.0f€%n", 
                this.destino, this.distancia, this.transporte, this.precioFinal());
    }
}

// ==========================================
// 4️⃣ PROGRAMA PRINCIPAL: Main
// ==========================================
public class Main {
    public static void main(String[] args) {
        // Crear los viajes solicitados en el enunciado
        Viaje miami = new Viaje("Madrid", 350, TipoTransporte.BUS);
        Viaje paris = new Viaje("Paris", 1200, TipoTransporte.AVION);
        Viaje barna = new Viaje("Barcelona", 500, TipoTransporte.TREN);

        // Calcular e imprimir resultados
        miami.imprimirResultado();
        paris.imprimirResultado();
        barna.imprimirResultado();
    }
}