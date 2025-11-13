class Video extends Contenido {
    private String url;
    private int duracion; 
    
    /**
     * Constructor de Video
     * @param titulo Título del video
     * @param autor Autor del video
     * @param categoria Categoría del video
     * @param url URL del archivo de video
     * @param duracion Duración en segundos
     */
    public Video(String titulo, Usuario autor, Categoria categoria, String url, int duracion) {
        super(titulo, autor, categoria);
        this.url = url;
        this.duracion = duracion;
    }
    
    @Override
    public String visualizar() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        int minutos = duracion / 60;
        int segundos = duracion % 60;
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== VIDEO ===\n");
        sb.append("Título: ").append(titulo).append("\n");
        sb.append("Autor: ").append(autor.getNombre()).append("\n");
        sb.append("Categoría: ").append(categoria.getNombre()).append("\n");
        sb.append("Duración: ").append(minutos).append("m ").append(segundos).append("s\n");
        sb.append("URL: ").append(url).append("\n");
        sb.append("Fecha: ").append(fechaCreacion.format(formatter)).append("\n");
        sb.append("Estado: ").append(publicado ? "Publicado" : "Borrador").append("\n");
        if (!etiquetas.isEmpty()) {
            sb.append("Etiquetas: ").append(String.join(", ", etiquetas)).append("\n");
        }
        return sb.toString();
    }
    
    public String getUrl() { return url; }
    public int getDuracion() { return duracion; }
    public void setUrl(String url) {
        this.url = url;
        this.fechaModificacion = LocalDateTime.now();
    }
}