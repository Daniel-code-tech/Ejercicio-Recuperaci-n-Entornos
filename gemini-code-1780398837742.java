import java.util.ArrayList;
import java.util.List;

// =========================================================================
// 3) DIFICULTAD DE LA MISIÓN (Conjunto cerrado -> ENUM)
// =========================================================================
enum Dificultad {
    FACIL("Fácil", 1.0, 10),
    MEDIA("Media", 1.5, 20),
    DIFICIL("Difícil", 2.0, 30),
    EPICA("Épica", 3.0, 40);

    private final String nombreVisible;
    private final double multiplicador;
    private final int energiaOrientativa;

    Dificultad(String nombreVisible, double multiplicador, int energiaOrientativa) {
        this.nombreVisible = nombreVisible;
        this.multiplicador = multiplicador;
        this.energiaOrientativa = energiaOrientativa;
    }

    public String getNombreVisible() { return nombreVisible; }

    public double calcularCosteBase() {
        return this.energiaOrientativa * this.multiplicador;
    }
}

// =========================================================================
// 5) VENTAJAS ESPECIALES (Interfaces de Capacidades)
// =========================================================================
interface ReductorGasto {
    double aplicarReduccion(double gastoActual);
}

interface CurablePostMision {
    void aplicarCuracionAutonoma(Miembro miembro);
}

// =========================================================================
// 6) RESTRICCIONES DEL DOMINIO (Excepciones Personalizadas)
// =========================================================================

// Error de Negocio Recuperable (Checked Exception): El cliente debe decidir si descansar o cambiar de personaje.
class EnergiaInsuficienteException extends Exception {
    public EnergiaInsuficienteException(String mensaje) { super(mensaje); }
}

// Error de Uso Incorrecto o Estado Inválido (Unchecked Exception): Datos corruptos o violación de precondiciones.
class RestriccionDominioException extends RuntimeException {
    public RestriccionDominioException(String mensaje) { super(mensaje); }
}

// =========================================================================
// 1) JERARQUÍA DE MIEMBROS (Clase Abstracta Sellada)
// =========================================================================
sealed abstract class Miembro permits Guerrero, Maga, Sanador {
    
    // Requisitos estáticos y finales
    public static final int ENERGIA_MAXIMA_BASE = 100;
    private static int totalMiembrosCreados = 0;

    private final String id; // Inmutable una vez asignado
    private int nivel;
    private double energiaActual;
    private int misionesCompletadas;

    public Miembro(String id, int nivel, double energiaInicial) {
        if (id == null || id.trim().isEmpty()) {
            throw new RestriccionDominioException("El identificador no puede estar vacío.");
        }
        if (nivel < 1 || nivel > 50) {
            throw new RestriccionDominioException("El nivel debe estar estrictamente entre 1 y 50.");
        }
        if (energiaInicial < 0) {
            throw new RestriccionDominioException("La energía inicial no puede ser un valor negativo.");
        }

        this.id = id;
        this.nivel = nivel;
        // Inicializa con el menor entero entre la recibida y la constante base máxima
        this.energiaActual = Math.min(energiaInicial, ENERGIA_MAXIMA_BASE);
        this.misionesCompletadas = 0;

        totalMiembrosCreados++; // Registro global acumulativo
    }

    // Getters
    public String getId() { return id; }
    public int getNivel() { return nivel; }
    public double getEnergiaActual() { return energiaActual; }
    public int getMisionesCompletadas() { return misionesCompletadas; }
    public static int getTotalMiembrosCreados() { return totalMiembrosCreados; }

    // Gestión de Energía
    public final void recuperarEnergia(double cantidad) {
        if (cantidad < 0) throw new RestriccionDominioException("No se puede recuperar una cantidad negativa.");
        this.energiaActual = Math.min(this.energiaActual + cantidad, ENERGIA_MAXIMA_BASE);
    }

    public final void gastarEnergia(double cantidad) throws EnergiaInsuficienteException {
        if (cantidad < 0) throw new RestriccionDominioException("No se puede gastar una cantidad negativa.");
        if (this.energiaActual < cantidad) {
            throw new EnergiaInsuficienteException("El miembro " + id + " no dispone de " + cantidad + " unidades de energía (Tiene: " + energiaActual + ").");
        }
        this.energiaActual -= cantidad;
    }

    public final void registrarMisionCompletada() {
        this.misionesCompletadas++;
    }

    // Métodos Abstractos Polimórficos
    public abstract double calcularEnergiaReal(double costeBaseMision);
    public abstract double calcularAportacionEspecial();

    // Lógica general de participación en misión simple
    public void participarMisionSimple(double costeBaseMision) throws EnergiaInsuficienteException {
        double gastoReal = calcularEnergiaReal(costeBaseMision);
        
        // Modificación dinámica basada en la interfaz opcional de reducción de gasto
        if (this instanceof ReductorGasto reductor) {
            gastoReal = reductor.aplicarReduccion(gastoReal);
        }

        double aportacion = calcularAportacionEspecial();
        System.out.println(">>> [" + id + "] Aporta " + aportacion + " de valor estratégico al grupo.");

        gastarEnergia(gastoReal);
        registrarMisionCompletada();
    }

    @Override
    public String toString() {
        return String.format("Id: %s | Nivel: %d | Energía: %.1f/%d | Misiones: %d", 
                id, nivel, energiaActual, ENERGIA_MAXIMA_BASE, misionesCompletadas);
    }
}

// =========================================================================
// SUBCLASES CONCRETAS (Perfiles de Aventureros)
// =========================================================================

final class Guerrero extends Miembro implements ReductorGasto {
    private final int fuerza; // 1 a 100

    public Guerrero(String id, int nivel, double energiaInicial, int fuerza) {
        super(id, nivel, energiaInicial);
        if (fuerza < 1 || fuerza > 100) throw new RestriccionDominioException("Fuerza fuera de rango (1-100).");
        this.fuerza = fuerza;
    }

    @Override
    public double calcularEnergiaReal(double costeBaseMision) {
        return costeBaseMision + 5;
    }

    @Override
    public double calcularAportacionEspecial() {
        return this.fuerza / 2.0;
    }

    @Override
    public double aplicarReduccion(double gastoActual) {
        // Ventaja especial: Mitiga el incremento bruto por su resistencia física
        return Math.max(0, gastoActual - 2); 
    }

    @Override
    public String toString() {
        return super.toString() + " | [Guerrero - Fuerza: " + fuerza + "]";
    }
}

final class Maga extends Miembro implements ReductorGasto {
    private final int mana; // 1 a 100

    public Maga(String id, int nivel, double energiaInicial, int mana) {
        super(id, nivel, energiaInicial);
        if (mana < 1 || mana > 100) throw new RestriccionDominioException("Maná fuera de rango (1-100).");
        this.mana = mana;
    }

    @Override
    public double calcularEnergiaReal(double costeBaseMision) {
        return Math.max(0, costeBaseMision - 3);
    }

    @Override
    public double calcularAportacionEspecial() {
        return this.mana;
    }

    @Override
    public double aplicarReduccion(double gastoActual) {
        // Ventaja especial: Ahorra un 10% adicional usando un escudo mágico
        return gastoActual * 0.90;
    }

    @Override
    public String toString() {
        return super.toString() + " | [Maga - Maná: " + mana + "]";
    }
}

final class Sanador extends Miembro implements CurablePostMision {
    private final int curacion; // 1 a 100

    public Sanador(String id, int nivel, double energiaInicial, int curacion) {
        super(id, nivel, energiaInicial);
        if (curacion < 1 || curacion > 100) throw new RestriccionDominioException("Curación fuera de rango (1-100).");
        this.curacion = curacion;
    }

    @Override
    public double calcularEnergiaReal(double costeBaseMision) {
        return costeBaseMision; // El sanador no modifica el coste base de forma directa
    }

    @Override
    public double calcularAportacionEspecial() {
        return this.curacion * 2.0;
    }

    @Override
    public void aplicarCuracionAutonoma(Miembro miembro) {
        // Ventaja especial: recupera 15 puntos fijos tras el esfuerzo
        miembro.recuperarEnergia(15);
        System.out.println("✚ Capacidad Especial activa: " + miembro.getId() + " se auto-cura +15 de energía.");
    }

    @Override
    public String toString() {
        return super.toString() + " | [Sanador - Curación: " + curacion + "]";
    }
}

// =========================================================================
// 2) CLASE: Misión
// =========================================================================
class Mision {
    private final String codigo;
    private final String nombre;
    private final Dificultad dificultad;

    public Mision(String codigo, String nombre, Dificultad dificultad) {
        if (codigo == null || codigo.trim().isEmpty()) throw new RestriccionDominioException("Código de misión inválido.");
        if (nombre == null || nombre.trim().isEmpty()) throw new RestriccionDominioException("Nombre de misión inválido.");
        if (dificultad == null) throw new RestriccionDominioException("La misión requiere una dificultad asignada.");

        this.codigo = codigo;
        this.nombre = nombre;
        this.dificultad = dificultad;
    }

    public void completarMision(Miembro miembro) throws EnergiaInsuficienteException {
        System.out.println("⚡ Intentando completar [" + nombre + "] con el aventurero [" + miembro.getId() + "]...");
        double costeFinal = this.dificultad.calcularCosteBase();
        
        // Ejecuta la participación delegando las reglas y mitigaciones específicas
        miembro.participarMisionSimple(costeFinal);
        
        // Aplicar ventaja especial post-misión si procede
        if (miembro instanceof CurablePostMision ejecutorEspecial) {
            ejecutorEspecial.aplicarCuracionAutonoma(miembro);
        }
        
        System.out.println("✅ ¡Misión completada con éxito!");
    }

    public String getDescripcionBreve() {
        return String.format("Misión: [%s] %s | Dificultad: %s (Coste Base Energía: %.1f)", 
                codigo, nombre, dificultad.getNombreVisible(), dificultad.calcularCosteBase());
    }
}

// =========================================================================
// 4) COMPOSICIÓN: Equipo
// =========================================================================
class Equipo {
    private final String nombre;
    private final Miembro lider;
    private final List<Miembro> integrantes; // Colección interna

    public Equipo(String nombre, Miembro lider) {
        if (nombre == null || nombre.trim().isEmpty()) throw new RestriccionDominioException("El nombre del equipo no puede estar vacío.");
        if (lider == null) throw new RestriccionDominioException("El equipo requiere obligatoriamente un líder válido.");
        
        this.nombre = nombre;
        this.lider = lider;
        this.integrantes = new ArrayList<>();
        
        // El líder debe pertenecer inherentemente al equipo
        this.añadirMiembro(lider);
    }

    public void añadirMiembro(Miembro m) {
        if (m == null) return;
        if (integrantes.size() >= 5) {
            System.out.println("❌ No se puede añadir a " + m.getId() + ". El equipo ya alcanzó el límite máximo de 5 miembros.");
            return;
        }
        // Verificar duplicados basándose en el identificador único
        for (Miembro integrante : integrantes) {
            if (integrante.getId().equalsIgnoreCase(m.getId())) {
                System.out.println("❌ Registro denegado: Ya existe un miembro con el ID '" + m.getId() + "' en este equipo.");
                return;
            }
        }
        integrantes.add(m);
        System.out.println("⁺ [" + m.getId() + "] ha sido asignado al equipo '" + nombre + "'.");
    }

    public void eliminarMiembro(Miembro m) {
        if (m == null) return;
        if (m.getId().equalsIgnoreCase(lider.getId())) {
            System.out.println("❌ Acción ilícita: No se puede expulsar al líder del equipo.");
            return;
        }
        if (integrantes.remove(m)) {
            System.out.println("⁻ [" + m.getId() + "] ha sido removido del equipo '" + nombre + "'.");
        }
    }

    public double calcularEnergiaTotal() {
        double total = 0;
        for (Miembro m : integrantes) {
            total += m.getEnergiaActual();
        }
        return total;
    }

    public void mostrarEstructura() {
        System.out.println("\n=== COMPOSICIÓN DEL EQUIPO: " + nombre.toUpperCase() + " ===");
        System.out.println("Líder de Escuadra -> " + lider);
        for (Miembro m : integrantes) {
            System.out.println(" • " + m);
        }
        System.out.println("🔋 Reserva de Energía Colectiva: " + calcularEnergiaTotal() + " Puntos.");
    }
}

// =========================================================================
// 9) PROGRAMA PRINCIPAL (MAIN DE PRUEBAS)
// =========================================================================
public class Main {
    public static void main(String[] args) {
        System.out.println("--- INICIALIZANDO GUILDHUB SYSTEM (2026) --- \n");

        // 1. Creación de un guerrero, una maga y un sanador.
        Miembro ragnar = new Guerrero("Ragnar", 15, 95, 80);
        Miembro ysolda = new Maga("Ysolda", 22, 120, 90); // Su energía se limitará a 100 automáticamente
        Miembro anduin = new Sanador("Anduin", 10, 60, 75);

        // 7. Uso de una colección para guardar miembros o misiones globales
        List<Miembro> tabernaGremio = new ArrayList<>();
        tabernaGremio.add(ragnar);
        tabernaGremio.add(ysolda);
        tabernaGremio.add(anduin);

        // 6. El uso de polimorfismo entre miembros (recorrido limpio de la colección)
        System.out.println("--- LISTADO DE AVENTUREROS EN LA TABERNA ---");
        for (Miembro m : tabernaGremio) {
            System.out.println(m);
        }

        // 2. La creación de misiones con dificultad.
        Mision mFácil = new Mision("M-01", "Desratizar Bodega", Dificultad.FACIL);
        Mision mÉpica = new Mision("M-02", "Derrotar al Dragón Ancestral", Dificultad.EPICA);

        System.out.println("\n--- MISIONES DISPONIBLES ---");
        System.out.println(mFácil.getDescripcionBreve());
        System.out.println(mÉpica.getDescripcionBreve());

        // 3. La creación de un equipo estructurado con composición.
        Equipo escuadraAlfa = new Equipo("Escuadra Alfa", ragnar);
        escuadraAlfa.addMiembro(ysolda);
        escuadraAlfa.addMiembro(anduin);
        escuadraAlfa.mostrarEstructura();

        System.out.println("\n--- EJECUCIÓN DE SIMULACROS DE COMBATE ---");

        // 4. Un caso correcto de participación en misión (Misión fácil con Ragnar)
        // 8. El cálculo del coste energético final se realiza internamente (10 * 1.0 = 10)
        try {
            mFácil.completarMision(ragnar);
        } catch (EnergiaInsuficienteException e) {
            System.out.println(e.getMessage());
        }

        // 9. Uso de ventaja especial de sanador integrada de manera polimórfica en Anduin
        try {
            mFácil.completarMision(anduin);
        } catch (EnergiaInsuficienteException e) {
            System.out.println(e.getMessage());
        }

        // 5. Un caso incorrecto que provoque una excepción del dominio (Energía insuficiente)
        // Anduin intenta ir a una misión Épica (Coste base: 40 * 3.0 = 120), requiere más energía de la que almacena.
        try {
            mÉpica.completarMision(anduin);
        } catch (EnergiaInsuficienteException e) {
            System.out.println("\n⚠️ [CAPTURA DE ERROR DE NEGOCIO]: Misión Abortada. " + e.getMessage());
        }

        // Forzar un error de uso incorrecto (Unchecked Exception) para validar los límites de nivel
        try {
            System.out.println("\nIntentando alterar el sistema instanciando un nivel ilícito...");
            Miembro tramposo = new Guerrero("Hacker", 99, 50, 50);
        } catch (RestriccionDominioException e) {
            System.out.println("❌ [CAPTURA DE VIOLACIÓN DE REGLA]: " + e.getMessage());
        }

        // 10. La impresión final de los objetos principales tras las batallas.
        System.out.println("\n--- ESTADO FINAL DEL GREMIO ---");
        escuadraAlfa.mostrarEstructura();
        System.out.println("\n📊 Censo Global: Se han registrado un total de " + Miembro.getTotalMiembrosCreados() + " aventureros en la plataforma.");
    }
}