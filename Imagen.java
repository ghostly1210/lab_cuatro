class Imagen extends Contenido {
    private String url;
    private String resolucion;
    
    /**
     * Constructor de Imagen
     * @param titulo Título de la imagen
     * @param autor Autor de la imagen
     * @param categoria Categoría de la imagen
     * @param url URL del archivo de imagen
     * @param resolucion Resolución de la imagen
     */
    public Imagen(String titulo, Usuario autor, Categoria categoria, String url, String resolucion) {
        super(titulo, autor, categoria);
        this.url = url;
        this.resolucion = resolucion;
    }
    
    @Override
    public String visualizar() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        StringBuilder sb = new StringBuilder();
        sb.append("=== IMAGEN ===\n");
        sb.append("Título: ").append(titulo).append("\n");
        sb.append("Autor: ").append(autor.getNombre()).append("\n");
        sb.append("Categoría: ").append(categoria.getNombre()).append("\n");
        sb.append("Resolución: ").append(resolucion).append("\n");
        sb.append("URL: ").append(url).append("\n");
        sb.append("Fecha: ").append(fechaCreacion.format(formatter)).append("\n");
        sb.append("Estado: ").append(publicado ? "Publicado" : "Borrador").append("\n");
        if (!etiquetas.isEmpty()) {
            sb.append("Etiquetas: ").append(String.join(", ", etiquetas)).append("\n");
        }
        return sb.toString();
    }
    
    public String getUrl() { return url; }
    public String getResolucion() { return resolucion; }
    public void setUrl(String url) {
        this.url = url;
        this.fechaModificacion = LocalDateTime.now();
    }
}