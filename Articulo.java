class Articulo extends Contenido {
    private String cuerpo;
    private List<String> referencias;
    
    /**
     * Constructor de Articulo
     * @param titulo Título del artículo
     * @param autor Autor del artículo
     * @param categoria Categoría del artículo
     * @param cuerpo Contenido textual del artículo
     */
    public Articulo(String titulo, Usuario autor, Categoria categoria, String cuerpo) {
        super(titulo, autor, categoria);
        this.cuerpo = cuerpo;
        this.referencias = new ArrayList<>();
    }
    
    @Override
    public String visualizar() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        StringBuilder sb = new StringBuilder();
        sb.append("=== ARTÍCULO ===\n");
        sb.append("Título: ").append(titulo).append("\n");
        sb.append("Autor: ").append(autor.getNombre()).append("\n");
        sb.append("Categoría: ").append(categoria.getNombre()).append("\n");
        sb.append("Fecha: ").append(fechaCreacion.format(formatter)).append("\n");
        sb.append("Estado: ").append(publicado ? "Publicado" : "Borrador").append("\n");
        sb.append("\nContenido:\n").append(cuerpo).append("\n");
        if (!referencias.isEmpty()) {
            sb.append("\nReferencias:\n");
            for (int i = 0; i < referencias.size(); i++) {
                sb.append((i + 1)).append(". ").append(referencias.get(i)).append("\n");
            }
        }
        if (!etiquetas.isEmpty()) {
            sb.append("\nEtiquetas: ").append(String.join(", ", etiquetas)).append("\n");
        }
        return sb.toString();
    }
    
    /**
     * Agrega una referencia bibliográfica
     * @param referencia Referencia a agregar
     */
    public void agregarReferencia(String referencia) {
        referencias.add(referencia);
    }
    
    // Getters y Setters
    public String getCuerpo() { return cuerpo; }
    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
        this.fechaModificacion = LocalDateTime.now();
    }
    public List<String> getReferencias() { return referencias; }
}
