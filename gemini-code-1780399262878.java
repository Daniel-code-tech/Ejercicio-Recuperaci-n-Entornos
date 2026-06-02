import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Estructura principal: Mes -> (Producto -> Unidades)
        HashMap<String, HashMap<String, Integer>> estadisticas = new HashMap<>();

        // 1. Registrar ventas de varios productos en varios meses.
        registrarVenta(estadisticas, "Enero", "Televisor", 15);
        registrarVenta(estadisticas, "Enero", "Portátil", 25);
        registrarVenta(estadisticas, "Febrero", "Televisor", 10);
        registrarVenta(estadisticas, "Febrero", "Smartphone", 50);
        registrarVenta(estadisticas, "Marzo", "Portátil", 30);
        registrarVenta(estadisticas, "Marzo", "Smartphone", 45);
        registrarVenta(estadisticas, "Marzo", "Televisor", 5);

        System.out.println("--- Datos inicializados con éxito ---\n");

        // 2. Mostrar las ventas de un mes concreto.
        mostrarVentasMes(estadisticas, "Enero");
        mostrarVentasMes(estadisticas, "Marzo");

        // 3. Mostrar cuántas unidades se vendieron de un producto en un mes concreto.
        mostrarUnidadesProductoMes(estadisticas, "Febrero", "Smartphone");
        mostrarUnidadesProductoMes(estadisticas, "Enero", "Smartphone"); // Caso inexistente

        // 4. Calcular el total vendido en un mes.
        calcularTotalMes(estadisticas, "Marzo");

        // 5. Calcular el total vendido de un producto sumando todos los meses.
        calcularTotalProductoGlobal(estadisticas, "Televisor");
        calcularTotalProductoGlobal(estadisticas, "Smartphone");

        // 6. Indicar cuál es el mes con más ventas totales.
        obtenerMesRecord(estadisticas);
    }

    /**
     * Método auxiliar para registrar ventas de forma segura evitando NullPointerException
     */
    private static void registrarVenta(HashMap<String, HashMap<String, Integer>> mapa, String mes, String producto, int unidades) {
        // Si el mes no existe en el mapa exterior, lo creamos vacío con una función lambda
        mapa.putIfAbsent(mes, new HashMap<>());
        
        // Obtenemos el mapa interno de ese mes
        HashMap<String, Integer> ventasMes = mapa.get(mes);
        
        // Sumamos las unidades si el producto ya existía, si no, empezamos en 0
        int unidadesAnteriores = ventasMes.getOrDefault(producto, 0);
        ventasMes.put(producto, unidadesAnteriores + unidades);
    }

    // 2. Mostrar las ventas de un mes concreto.
    private static void mostrarVentasMes(HashMap<String, HashMap<String, Integer>> mapa, String mes) {
        System.out.println("📊 Ventas detalladas de [" + mes + "]:");
        if (!mapa.containsKey(mes)) {
            System.out.println("   No hay registros para este mes.");
            return;
        }
        for (Map.Entry<String, Integer> entrada : mapa.get(mes).entrySet()) {
            System.out.println("   • " + entrada.getKey() + ": " + entrada.getValue() + " unidades");
        }
        System.out.println();
    }

    // 3. Mostrar cuántas unidades se vendieron de un producto en un mes concreto.
    private static void mostrarUnidadesProductoMes(HashMap<String, HashMap<String, Integer>> mapa, String mes, String producto) {
        int unidades = 0;
        if (mapa.containsKey(mes)) {
            unidades = mapa.get(mes).getOrDefault(producto, 0);
        }
        System.out.println("🔍 Consulta [" + mes + "]: Del producto '" + producto + "' se vendieron: " + unidades + " unidades.");
    }

    // 4. Calcular el total vendido en un mes.
    private static int calcularTotalMes(HashMap<String, HashMap<String, Integer>> mapa, String mes) {
        int total = 0;
        if (mapa.containsKey(mes)) {
            // values() nos da una colección con solo los números enteros del mapa interno
            for (int unidades : mapa.get(mes).values()) {
                total += unidades;
            }
        }
        System.out.println("📉 Volumen total de ventas en [" + mes + "]: " + total + " unidades.");
        return total;
    }

    // 5. Calcular el total vendido de un producto sumando todos los meses.
    private static void calcularTotalProductoGlobal(HashMap<String, HashMap<String, Integer>> mapa, String producto) {
        int totalGlobal = 0;
        // Recorremos todos los mapas internos que existen sin importar el mes
        for (HashMap<String, Integer> ventasMes : mapa.values()) {
            totalGlobal += ventasMes.getOrDefault(producto, 0);
        }
        System.out.println("🌍 Ventas globales acumuladas de '" + producto + "': " + totalGlobal + " unidades.");
    }

    // 6. Indicar cuál es el mes con más ventas totales.
    private static void obtenerMesRecord(HashMap<String, HashMap<String, Integer>> mapa) {
        if (mapa.isEmpty()) {
            System.out.println("No hay datos para calcular el récord.");
            return;
        }

        String mesRecord = "";
        int maxUnidades = -1;

        // Iteramos por las claves de los meses del mapa exterior
        for (String mes : mapa.keySet()) {
            // Reutilizamos el algoritmo del punto 4 para contar el volumen de este mes
            int totalEsteMes = 0;
            for (int unidades : mapa.get(mes).values()) {
                totalEsteMes += unidades;
            }

            if (totalEsteMes > maxUnidades) {
                maxUnidades = totalEsteMes;
                mesRecord = mes;
            }
        }

        System.out.println("\n🏆 EL MES CON MÁS VENTAS ES: [" + mesRecord + "] con un total de " + maxUnidades + " unidades.");
    }
}