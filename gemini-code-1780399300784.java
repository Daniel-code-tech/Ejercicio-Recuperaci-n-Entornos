import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// ==========================================
// CLASE: Zona
// ==========================================
class Zona {
    private String nombreZona;
    private int prioridad; // Menor número = Mayor prioridad (o viceversa)

    public Zona(String nombreZona, int prioridad) {
        this.nombreZona = nombreZona;
        this.prioridad = prioridad;
    }

    public String getNombreZona() { return nombreZona; }
    public int getPrioridad() { return prioridad; }
}

// ==========================================
// CLASE: Destinatario
// ==========================================
class Destinatario {
    private String nombre;
    private Zona zona;

    public Destinatario(String nombre, Zona zona) {
        this.nombre = nombre;
        this.zona = zona;
    }

    public String getNombre() { return nombre; }
    public Zona getZona() { return zona; }
}

// ==========================================
// CLASE: Paquete
// ==========================================
class Paquete {
    private double peso;
    private Destinatario destinatario;

    public Paquete(double peso, Destinatario destinatario) {
        this.peso = peso;
        this.destinatario = destinatario;
    }

    public double getPeso() { return peso; }
    public Destinatario getDestinatario() { return destinatario; }
}

// ==========================================
// CLASE: Envio (Implementa el Orden Natural)
// ==========================================
class Envio implements Comparable<Envio> {
    private String codigo;
    private Paquete paquete;
    private double precioEnvio;

    public Envio(String codigo, Paquete paquete, double precioEnvio) {
        this.codigo = codigo;
        this.paquete = paquete;
        this.precioEnvio = precioEnvio;
    }

    public String getCodigo() { return codigo; }
    public Paquete getPaquete() { return paquete; }
    public double getPrecioEnvio() { return precioEnvio; }

    // 2. Implementación del Orden Natural (Por Código)
    @Override
    public int compareTo(Envio otro) {
        return this.codigo.compareTo(otro.codigo);
    }

    @Override
    public String toString() {
        return String.format("[Envio: %s | Pvp: %.2f€] -> Paquete: %.1fkg | Dest: %s | Zona: %s (Prioridad: %d)",
                codigo, precioEnvio, paquete.getPeso(), paquete.getDestinatario().getNombre(),
                paquete.getDestinatario().getZona().getNombreZona(), paquete.getDestinatario().getZona().getPrioridad());
    }
}

// ==========================================
// PROGRAMA PRINCIPAL
// ==========================================
public class Main {
    public static void main(String[] args) {
        // Inicializamos algunas zonas de ejemplo
        Zona norte = new Zona("Norte", 1);
        Zona sur = new Zona("Sur", 3);
        Zona centro = new Zona("Centro", 1); // Empata en prioridad con Norte

        // 3. Crear una lista de envíos con al menos 6 envíos distintos
        List<Envio> envios = new ArrayList<>();
        envios.add(new Envio("ENV-06", new Paquete(5.5, new Destinatario("Carlos", sur)), 12.50));
        envios.add(new Envio("ENV-01", new Paquete(2.0, new Destinatario("Ana", norte)), 8.00));
        envios.add(new Envio("ENV-04", new Paquete(12.0, new Destinatario("Marta", centro)), 25.00));
        envios.add(new Envio("ENV-02", new Paquete(2.0, new Destinatario("Ana", centro)), 9.50)); // Mismo peso y dest que ENV-01
        envios.add(new Envio("ENV-03", new Paquete(1.5, new Destinatario("Luis", norte)), 6.00));
        envios.add(new Envio("ENV-05", new Paquete(12.0, new Destinatario("Beto", sur)), 22.00)); // Mismo peso que ENV-04

        // 4. Mostrar la lista original
        System.out.println("=== 4. LISTA ORIGINAL ===");
        imprimirLista(envios);

        // 5. Ordenar la lista por orden natural (Código) y mostrarla
        Collections.sort(envios); // Utiliza el compareTo de Envio
        System.out.println("\n=== 5. ORDEN NATURAL (POR CÓDIGO) ===");
        imprimirLista(envios);

        // 6. Ordenar por nombre del destinatario
        envios.sort(Comparator.comparing(e -> e.getPaquete().getDestinatario().getNombre()));
        System.out.println("\n=== 6. ORDENADO POR NOMBRE DESTINATARIO ===");
        imprimirLista(envios);

        // 7. Ordenar por prioridad de la zona y, si empatan, por nombre de la zona
        envios.sort(Comparator.comparing((Envio e) -> e.getPaquete().getDestinatario().getZona().getPrioridad())
                .thenComparing(e -> e.getPaquete().getDestinatario().getZona().getNombreZona()));
        System.out.println("\n=== 7. ORDENADO POR PRIORIDAD ZONA Y LUEGO NOMBRE ZONA ===");
        imprimirLista(envios);

        // 8. Ordenar por peso del paquete y, si empatan, por precio del envío
        envios.sort(Comparator.comparing((Envio e) -> e.getPaquete().getPeso())
                .thenComparing(Envio::getPrecioEnvio));
        System.out.println("\n=== 8. ORDENADO POR PESO Y LUEGO PRECIO ENVÍO ===");
        imprimirLista(envios);

        // 9. Ordenar por: nombreZona -> nombre destinatario -> peso
        envios.sort(Comparator.comparing((Envio e) -> e.getPaquete().getDestinatario().getZona().getNombreZona())
                .thenComparing(e -> e.getPaquete().getDestinatario().getNombre())
                .thenComparing(e -> e.getPaquete().getPeso()));
        System.out.println("\n=== 9. ORDENADO POR NOMBRE ZONA -> DESTINATARIO -> PESO ===");
        imprimirLista(envios);
    }

    // 10. Método auxiliar para mostrar la lista tras cada ordenación
    private static void imprimirLista(List<Envio> lista) {
        for (Envio e : lista) {
            System.out.println(e);
        }
    }
}