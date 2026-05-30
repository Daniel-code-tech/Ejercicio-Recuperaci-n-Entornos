import java.util.Scanner;

public class AppTareas {
    // Constantes estáticas para las opciones del menú
    private static final int OPCION_CREAR = 1;
    private static final int OPCION_LISTAR = 2;
    private static final int OPCION_CAMBIAR_ESTADO = 3;
    private static final int OPCION_VER_CONTADOR = 4;
    private static final int OPCION_SALIR = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestorTareas gestor = new GestorTareas();
        int opcion;

        do {
            System.out.println("\n-------- MENÚ TAREAS --------");
            System.out.println("1. Crear nueva tarea");
            System.out.println("2. Listar tareas");
            System.out.println("3. Cambiar estado de una tarea");
            System.out.println("4. Ver número total de tareas creadas");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            // Validación de entrada numérica
            while (!scanner.hasNextInt()) {
                System.out.print("Por favor, introduce un número válido: ");
                scanner.next();
            }
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el salto de línea del búfer

            switch (opcion) {
                case OPCION_CREAR:
                    System.out.print("Introduce el título de la tarea: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Introduce la descripción: ");
                    String descripcion = scanner.nextLine();
                    gestor.crearNuevaTarea(titulo, descripcion);
                    break;

                case OPCION_LISTAR:
                    gestor.listarTareas();
                    break;

                case OPCION_CAMBIAR_ESTADO:
                    System.out.print("Introduce el número de tarea (1, 2 o 3): ");
                    while (!scanner.hasNextInt()) {
                        System.out.print("Introduce un número de posición válido (1-3): ");
                        scanner.next();
                    }
                    int numTarea = scanner.nextInt();
                    scanner.nextLine();

                    Tarea tareaEncontrada = gestor.buscarTareaPorNumero(numTarea);

                    if (tareaEncontrada == null) {
                        System.out.println("❌ Error: Posición no válida o tarea inexistente.");
                    } else {
                        System.out.println("\nTarea seleccionada: " + tareaEncontrada.getTitulo());
                        System.out.println("Elige nuevo estado:");
                        System.out.println("1. PENDIENTE");
                        System.out.println("2. EN_PROCESO");
                        System.out.println("3. TERMINADA");
                        System.out.print("Selección: ");
                        
                        int opcionEstado = scanner.nextInt();
                        scanner.nextLine();

                        switch (opcionEstado) {
                            case 1:
                                tareaEncontrada.setEstado(EstadoTarea.PENDIENTE);
                                System.out.println("🔄 Estado actualizado a PENDIENTE.");
                                break;
                            case 2:
                                tareaEncontrada.setEstado(EstadoTarea.EN_PROCESO);
                                System.out.println("🔄 Estado actualizado a EN_PROCESO.");
                                break;
                            case 3:
                                tareaEncontrada.setEstado(EstadoTarea.TERMINADA);
                                System.out.println("🔄 Estado actualizado a TERMINADA.");
                                break;
                            default:
                                System.out.println("⚠️ Opción de estado no válida. No se realizaron cambios.");
                        }
                    }
                    break;

                case OPCION_VER_CONTADOR:
                    // Llamada al método estático directamente desde la Clase Tarea
                    System.out.println("📊 Contador global: Se han instanciado un total de " 
                                       + Tarea.getTotalTareas() + " tareas en la aplicación.");
                    break;

                case OPCION_SALIR:
                    System.out.println("Saliendo del gestor de tareas... ¡Buen trabajo hoy!");
                    break;

                default:
                    System.out.println("⚠️ Opción no contemplada en el menú. Reintenta.");
            }

        } while (opcion != OPCION_SALIR);

        scanner.close();
    }
}