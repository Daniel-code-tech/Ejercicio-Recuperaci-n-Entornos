public static boolean esPalindromo(String str) {
    // Caso base: una cadena vacía o de un solo carácter siempre es palíndromo
    if (str == null || str.length() <= 1) {
        return true;
    }

    // Comparamos el primer carácter con el último
    char primero = str.charAt(0);
    char ultimo = str.charAt(str.length() - 1);

    if (primero == ultimo) {
        // Caso recursivo: eliminamos el primer y último carácter y volvemos a comprobar
        // substring(1, str.length() - 1) extrae el texto intermedio
        return esPalindromo(str.substring(1, str.length() - 1));
    }

    // Si el primer y último carácter no coinciden, no es palíndromo
    return false;
}