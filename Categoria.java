import java.util.UUID;

class Categoria {
    private String id;
    private String nombre;
    private String descripcion;
    
    /**
     * Constructor de Categoria
     * @param nombre Nombre de la categoría
     * @param descripcion Descripción de la categoría
     */
    public Categoria(String nombre, String descripcion) {
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
}