import java.util.ArrayList;
import java.util.List;

// ==========================================
// ABSTRACCIÓN: Clase Abstracta Drone
// ==========================================
abstract class Drone {
    private String id;
    private int autonomia; // en minutos
    private double cargaMaxima; // en kg

    public Drone(String id, int autonomia, double cargaMaxima) {
        // ENCAPSULACIÓN: Validaciones en el constructor
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("El ID no puede estar vacío.");
        if (autonomia <= 0) throw new IllegalArgumentException("La autonomía debe ser mayor a 0 minutos.");
        if (cargaMaxima <= 0) throw new IllegalArgumentException("La carga máxima debe ser mayor a 0 kg.");
        
        this.id = id;
        this.autonomia = autonomia;
        this.cargaMaxima = cargaMaxima;
    }

    // Getters necesarios
    public String getId() { return id; }
    public int getAutonomia() { return autonomia; }
    public double getCargaMaxima() { return cargaMaxima; }

    // MÉTODOS ABSTRACTOS
    public abstract double calcularConsumo(double distancia);
    public abstract void ejecutarMision(double distancia);
}

// ==========================================
// POLIMORFISMO: Clases Hijas (Especializaciones)
// ==========================================

// 1. Drone Ligero (Consumo base bajo)
class DroneLigero extends Drone {
    public DroneLigero(String id, int autonomia, double cargaMaxima) {
        super(id, autonomia, cargaMaxima);
    }

    @Override
    public double calcularConsumo(double distancia) {
        // Un dron ligero consume 0.5 minutos de autonomía por kilómetro
        return distancia * 0.5;
    }

    @Override
    public void ejecutarMision(double distancia) {
        System.out.println("Drone Ligero [" + getId() + "] surcando el aire ágilmente. Distancia: " + distancia + " km.");
    }
}

// 2. Drone Pesado (Consumo más alto debido al peso del propio dron)
class DronePesado extends Drone {
    public DronePesado(String id, int autonomia, double cargaMaxima) {
        super(id, autonomia, cargaMaxima);
    }

    @Override
    public double calcularConsumo(double distancia) {
        // Un dron pesado consume 1.2 minutos de autonomía por kilómetro
        return distancia * 1.2;
    }

    @Override
    public void ejecutarMision(double distancia) {
        System.out.println("Drone Pesado [" + getId() + "] volando con motores de alta potencia. Distancia: " + distancia + " km.");
    }
}

// 3. Drone Refrigerado (Consumo extra constante por mantener el sistema de frío activo)
class DroneRefrigerado extends Drone {
    public DroneRefrigerado(String id, int autonomia, double cargaMaxima) {
        super(id, autonomia, cargaMaxima);
    }

    @Override
    public double calcularConsumo(double distancia) {
        // Consumo de 0.8 min/km + un coste fijo de 5 minutos por el sistema de refrigeración
        return (distancia * 0.8) + 5;
    }

    @Override
    public void ejecutarMision(double distancia) {
        System.out.println("Drone Refrigerado [" + getId() + "] transportando mercancía fría (Control de temperatura OK). Distancia: " + distancia + " km.");
    }
}

// ==========================================
// COMPOSICIÓN: Clase Mision
// ==========================================
class Mision {
    // Relación de composición/asociación: Una misión TIENE UN drone
    private Drone drone; 
    private double distancia; // en km
    private double pesoPaquete; // en kg

    public Mision(Drone drone, double distancia, double pesoPaquete) {
        // ENCAPSULACIÓN: Validaciones
        if (drone == null) throw new IllegalArgumentException("La misión requiere un dron asignado.");
        if (distancia <= 0) throw new IllegalArgumentException("La distancia debe ser mayor a 0 km.");
        if (pesoPaquete <= 0) throw new IllegalArgumentException("El peso del paquete debe ser mayor a 0 kg.");
        
        this.drone = drone;
        this.distancia = distancia;
        this.pesoPaquete = pesoPaquete;
    }

    public void ejecutar() {
        System.out.println("=== Evaluando Misión para el paquete de " + pesoPaquete + " kg ===");
        
        // 1. Validar si el dron puede con el peso del paquete
        if (pesoPaquete > drone.getCargaMaxima()) {
            System.out.println("❌ MISIÓN CANCELADA: El paquete excede la carga máxima permitida del dron (" + drone.getCargaMaxima() + " kg).");
            return;
        }

        // 2. Validar si tiene suficiente autonomía de batería
        double consumoRequerido = drone.calcularConsumo(distancia);
        if (consumoRequerido > drone.getAutonomia()) {
            System.out.println("❌ MISIÓN CANCELADA: Autonomía insuficiente. Se requieren " + consumoRequerido + " min pero el dron solo tiene " + drone.getAutonomia() + " min.");
            return;
        }

        // 3. POLIMORFISMO: Si todo es correcto, ejecuta el comportamiento específico del dron asignado
        drone.ejecutarMision(distancia);
        System.out.println("✨ Misión completada con éxito. Batería consumida estimada: " + consumoRequerido + " minutos.");
    }
}

// ==========================================
// CLASE PRINCIPAL: Main Obligatorio
// ==========================================
public class Main {
    public static void main(String[] args) {
        // 1. Crear varios drones distintos
        Drone ligero = new DroneLigero("DL-101", 30, 2.5);       // 30 min autonomía, soporta 2.5 kg
        Drone pesado = new DronePesado("DP-505", 45, 15.0);      // 45 min autonomía, soporta 15 kg
        Drone refrigerado = new DroneRefrigerado("DR-909", 25, 5.0); // 25 min autonomía, soporta 5 kg

        // 2. Crear varias misiones combinando los drones
        Mision m1 = new Mision(ligero, 10, 1.5);       // Distancia 10km (gasta 5min), paquete de 1.5kg -> OK
        Mision m2 = new Mision(ligero, 5, 4.0);        // Paquete de 4kg supera el límite del ligero (2.5kg) -> Falla por peso
        Mision m3 = new Mision(pesado, 40, 10.0);      // Distancia 40km (gasta 48min), supera autonomía (45min) -> Falla por batería
        Mision m4 = new Mision(refrigerado, 15, 3.0);  // Distancia 15km (gasta 12+5 = 17min), paquete 3kg -> OK

        // 3. Guardarlas en una estructura o Array (en este caso una lista dinámica)
        List<Mision> misiones = new ArrayList<>();
        misiones.add(m1);
        misiones.add(m2);
        misiones.add(m3);
        misiones.add(m4);

        // 4. Ejecutarlas recorriendo el array/lista y mostrar resultados
        for (Mision mision : misiones) {
            mision.ejecutar();
            System.out.println(); // Salto de línea estético entre misiones
        }
    }
}