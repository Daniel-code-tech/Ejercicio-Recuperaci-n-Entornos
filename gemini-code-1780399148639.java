import java.util.ArrayList;
import java.util.List;

// ==========================================
// PARTE 1: Clase Asistente Inicial
// ==========================================
class Asistente {
    private String dni;
    private String nombre;
    private String email;

    public Asistente(String dni, String nombre, String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.email = email;
    }

    public String getDni() { return dni; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return "Asistente [DNI=" + dni + ", Nombre=" + nombre + ", Email=" + email + "]";
    }
}

// ==========================================
// PROGRAMA PRINCIPAL (Fase de Pruebas)
// ==========================================
public class Main {
    public static void main(String[] args) {
        // Parte 2: Crear la lista
        List<Asistente> lista = new ArrayList<>();

        // Datos a añadir de forma controlada
        Asistente a1 = new Asistente("111A", "Ana", "ana@mail.com");
        Asistente a2 = new Asistente("222B", "Luis", "luis@mail.com");
        Asistente a3 = new Asistente("333C", "Marta", "marta@mail.com");
        Asistente a4 = new Asistente("111A", "Ana Dup", "otro@mail.com"); // Duplicado de DNI

        // Parte 3: Control de duplicados manual al insertar
        System.out.println("--- insertando Asistentes ---");
        tryRegistrar(lista, a1);
        tryRegistrar(lista, a2);
        tryRegistrar(lista, a3);
        tryRegistrar(lista, a4); // Intentando meter al duplicado

        // Mostrar lista completa
        System.out.println("\n--- Parte 2 y 3: Lista Completa ---");
        for (Asistente a : lista) {
            System.out.println(a);
        }

        // Parte 4: Búsqueda
        System.out.println("\n--- Parte 4: Búsqueda ---");
        Asistente luisBuscar = new Asistente("222B", "Luis", "luis@mail.com");
        if (lista.contains(luisBuscar)) {
            System.out.println("Encontrado");
        } else {
            System.out.println("No encontrado");
        }

        // Parte 5: Eliminación
        System.out.println("\n--- Parte 5: Eliminación ---");
        Asistente martaBorrar = new Asistente("333C", "", "");
        lista.remove(martaBorrar);

        System.out.println("Lista tras el intento de borrado:");
        for (Asistente a : lista) {
            System.out.println(a);
        }
    }

    // Método auxiliar para la Parte 3
    private static void tryRegistrar(List<Asistente> lista, Asistente nuevo) {
        if (lista.contains(nuevo)) {
            System.out.println("❌ El asistente con DNI " + nuevo.getDni() + " ya está registrado.");
        } else {
            lista.add(nuevo);
            System.out.println("✅ " + nuevo.getNombre() + " añadido.");
        }
    }
}