import java.util.Scanner;

public class AnalizadorURL {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce una URL: ");
        String urlOriginal = scanner.nextLine();
        
        // ==========================================
        // a) Limpiar la entrada
        // ==========================================
        String url = urlOriginal.trim();
        // Sustituir triples o dobles barras en la ruta (manteniendo las del protocolo)
        String protocoloTemp = "";
        if (url.contains("://")) {
            int posProto = url.indexOf("://");
            protocoloTemp = url.substring(0, posProto + 3);
            url = url.substring(posProto + 3);
        }
        url = url.replace("//", "/");
        url = protocoloTemp + url;

        System.out.println("\n--- COMPONENTES EXTRAÍDOS ---");

        // ==========================================
        // b) Validar y extraer partes básicas
        // ==========================================
        
        // 1. Protocolo
        String protocolo = "";
        int indProtocolo = url.indexOf("://");
        if (indProtocolo != -1) {
            protocolo = url.substring(0, indProtocolo);
            // Validar que sea http o https
            if (!protocolo.equalsIgnoreCase("http") && !protocolo.equalsIgnoreCase("https")) {
                System.out.println("Error: El protocolo debe ser HTTP o HTTPS.");
                return;
            }
            url = url.substring(indProtocolo + 3); // Nos quedamos con el resto
        } else {
            System.out.println("Error: URL sin protocolo válido.");
            return;
        }
        System.out.println("Protocolo: " + protocolo);

        // Separar parámetros primero (si existen)
        String parametros = "";
        int indInterrogante = url.indexOf("?");
        if (indInterrogante != -1) {
            parametros = url.substring(indInterrogante + 1);
            url = url.substring(0, indInterrogante); // url ahora no tiene parámetros
        }

        // 2. Dominio y Ruta/Recurso
        String dominio = "";
        String restoUrl = "";
        int primerBarra = url.indexOf("/");
        
        if (primerBarra != -1) {
            dominio = url.substring(0, primerBarra);
            restoUrl = url.substring(primerBarra); // Incluye la barra inicial
        } else {
            dominio = url;
            restoUrl = "/";
        }
        System.out.println("Dominio: " + dominio);

        // 3. Ruta y Recurso
        String ruta = "/";
        String recurso = "";
        int ultimaBarra = restoUrl.lastIndexOf("/");
        
        if (ultimaBarra != -1) {
            ruta = restoUrl.substring(0, ultimaBarra + 1);
            recurso = restoUrl.substring(ultimaBarra + 1);
        }
        
        System.out.println("Ruta: " + ruta);
        System.out.println("Recurso: " + (recurso.isEmpty() ? "(No tiene)" : recurso));
        System.out.println("Parámetros: " + (parametros.isEmpty() ? "(No tiene)" : parametros));

        // Validar extensión del recurso (si existe)
        if (!recurso.isEmpty() && !recurso.contains(".")) {
            System.out.println("Aviso: El recurso no parece tener una extensión válida (ej. .html, .php).");
        }

        // ==========================================
        // c) Transformar
        // ==========================================
        System.out.println("\n--- TRANSFORMACIONES ---");

        // 1. URL Bonita (Sin www., sin parámetros, ruta limpia)
        String dominioBonito = dominio;
        if (dominioBonito.toLowerCase().startsWith("www.")) {
            dominioBonito = dominioBonito.substring(4);
        }
        String urlBonita = protocolo.concat("://").concat(dominioBonito).concat(ruta).concat(recurso);
        System.out.println("URL Bonita: " + urlBonita);

        // 2. Slug del recurso (Ej: Mi Artículo.html -> mi-artículo.html)
        if (!recurso.isEmpty()) {
            // Pasamos a minúsculas, cambiamos espacios por guiones
            String slug = recurso.toLowerCase().replace(" ", "-");
            System.out.println("Slug del recurso: " + slug);
        } else {
            System.out.println("Slug del recurso: No aplica (no hay recurso en la URL)");
        }
        
        scanner.close();
    }
}