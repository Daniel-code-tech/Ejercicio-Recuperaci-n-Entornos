public class Libro {
    private String titulo;
    private Autor autor;
    private int anio;

    public Libro(String titulo, Autor autor, int anio) {
        if (anio <= 0) {
            throw new IllegalArgumentException("El año debe ser mayor que 0.");
        }
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
    }

    // Getters y Setters
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }

    public int getAnio() { return anio; }
    public void setAnio(int anio) {
        if (anio <= 0) {
            throw new IllegalArgumentException("El año debe ser mayor que 0.");
        }
        this.anio = anio;
    }

    @Override
    public String toString() {
        return "'" + titulo + "' de " + autor + " (" + anio + ")";
    }
}