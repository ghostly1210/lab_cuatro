import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta Contenido
 * Representa la clase base para todos los tipos de contenido del CMS
 * Implementa las interfaces IPublicable, IVisualizable, IBuscable e IReportable
 */
abstract class Contenido implements IPublicable, IVisualizable, IBuscable, IReportable {
    protected String id;
    protected String titulo;
    protected Usuario autor;
    protected LocalDateTime fechaCreacion;
    protected LocalDateTime fechaModificacion;
    protected Categoria categoria;
    protected List<String> etiquetas;
    protected boolean publicado;
    protected LocalDateTime fechaPublicacion;
    
    /**
     * Constructor de Contenido
     * @param titulo Título del contenido
     * @param autor Usuario autor del contenido
     * @param categoria Categoría del contenido
     */
    public Contenido(String titulo, Usuario autor, Categoria categoria) {
        this.id = UUID.randomUUID().toString();
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaModificacion = LocalDateTime.now();
        this.etiquetas = new ArrayList<>();
        this.publicado = false;
    }
    
    @Override
    public void publicar() {
        this.publicado = true;
        this.fechaPublicacion = LocalDateTime.now();
    }
    
    @Override
    public void despublicar() {
        this.publicado = false;
        this.fechaPublicacion = null;
    }
    
    @Override
    public abstract String visualizar();
    
    /**
     * Edita el título del contenido
     * @param nuevoTitulo Nuevo título del contenido
     */
    public void editar(String nuevoTitulo) {
        this.titulo = nuevoTitulo;
        this.fechaModificacion = LocalDateTime.now();
    }
    
    @Override
    public boolean buscar(String criterio) {
        return titulo.toLowerCase().contains(criterio.toLowerCase()) ||
               categoria.getNombre().toLowerCase().contains(criterio.toLowerCase());
    }
    
    @Override
    public String generarReporte() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format("ID: %s | Título: %s | Autor: %s | Categoría: %s | Publicado: %s | Fecha: %s",
                id.substring(0, 8), titulo, autor.getNombre(), categoria.getNombre(), 
                publicado ? "Sí" : "No", fechaCreacion.format(formatter));
    }
    
    /**
     * Agrega una etiqueta al contenido
     * @param etiqueta Etiqueta a agregar
     */
    public void agregarEtiqueta(String etiqueta) {
        if (!etiquetas.contains(etiqueta)) {
            etiquetas.add(etiqueta);
        }
    }
    
    // Getters y Setters
    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public Usuario getAutor() { return autor; }
    public Categoria getCategoria() { return categoria; }
    public boolean isPublicado() { return publicado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public List<String> getEtiquetas() { return etiquetas; }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
        this.fechaModificacion = LocalDateTime.now();
    }
}