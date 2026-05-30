public class MainHotel {
    public static void main(String[] args) {
        // Creamos el hotel pequeño
        Hotel miHotel = new Hotel("Hotel Bit Alicante");

        // Lanzamos las 3 primeras reservas (deberían ocupar la 101, 102 y 103)
        miHotel.reservarHabitacion(2); // Asignará h1 (101)
        miHotel.reservarHabitacion(3); // Asignará h2 (102)
        miHotel.reservarHabitacion(1); // Asignará h3 (103)

        // Intentamos una 4ª reserva (debería fallar porque ya está lleno)
        miHotel.reservarHabitacion(4); 
    }
}