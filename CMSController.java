import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Clase CMSController
 * Controlador principal del sistema CMS
 * Gestiona la lógica de negocio y coordina Modelo y Vista
 */
class CMSController {
    private List<Contenido> contenidos;
    private List<Usuario> usuarios;
    private List<Categoria> categorias;
    private Usuario usuarioActual;
    private CMSView vista;
    
    /**
     * Constructor de CMSController
     * @param vista Vista asociada al controlador
     */
    public CMSController(CMSView vista) {
        this.vista = vista;
        this.contenidos = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.categorias = new ArrayList<>();
        this.usuarioActual = null;
        inicializarDatos();
    }
    
    /**
     * Inicializa datos de prueba del sistema
     */
    private void inicializarDatos() {
        usuarios.add(new Administrador("Admin User", "admin@ega.edu", "admin123"));
        usuarios.add(new Editor("Editor User", "editor@ega.edu", "editor123"));
        
        categorias.add(new Categoria("Ciencias", "Contenidos de ciencias exactas"));
        categorias.add(new Categoria("Humanidades", "Contenidos de humanidades"));
        categorias.add(new Categoria("Tecnología", "Contenidos de tecnología"));
        categorias.add(new Categoria("Arte", "Contenidos artísticos"));
    }
    
    /**
     * Inicia sesión de usuario
     * @param email Email del usuario
     * @param password Contraseña del usuario
     * @return true si la autenticación es exitosa
     */
    public boolean iniciarSesion(String email, String password) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email) && usuario.verificarPassword(password)) {
                usuarioActual = usuario;
                return true;
            }
        }
        return false;
    }
    
    /**
     * Crea un nuevo contenido
     * @param tipo Tipo de contenido (articulo, video, imagen)
     * @param datos Mapa con los datos del contenido
     * @return El contenido creado o null si hay error
     */
    public Contenido crearContenido(String tipo, Map<String, Object> datos) {
        if (usuarioActual == null) {
            return null;
        }
        
        String titulo = (String) datos.get("titulo");
        Categoria categoria = (Categoria) datos.get("categoria");
        Contenido nuevoContenido = null;
        
        switch (tipo.toLowerCase()) {
            case "articulo":
                String cuerpo = (String) datos.get("cuerpo");
                nuevoContenido = new Articulo(titulo, usuarioActual, categoria, cuerpo);
                break;
                
            case "video":
                String urlVideo = (String) datos.get("url");
                int duracion = (int) datos.get("duracion");
                nuevoContenido = new Video(titulo, usuarioActual, categoria, urlVideo, duracion);
                break;
                
            case "imagen":
                String urlImagen = (String) datos.get("url");
                String resolucion = (String) datos.get("resolucion");
                nuevoContenido = new Imagen(titulo, usuarioActual, categoria, urlImagen, resolucion);
                break;
        }
        
        if (nuevoContenido != null) {
            contenidos.add(nuevoContenido);
        }
        
        return nuevoContenido;
    }
    
    /**
     * Edita un contenido existente
     * @param id ID del contenido a editar
     * @param nuevosDatos Nuevos datos del contenido
     * @return true si la edición fue exitosa
     */
    public boolean editarContenido(String id, Map<String, Object> nuevosDatos) {
        if (usuarioActual == null) {
            return false;
        }
        
        Contenido contenido = buscarContenidoPorId(id);
        if (contenido == null) {
            return false;
        }
        
        if (nuevosDatos.containsKey("titulo")) {
            contenido.setTitulo((String) nuevosDatos.get("titulo"));
        }
        
        if (contenido instanceof Articulo && nuevosDatos.containsKey("cuerpo")) {
            ((Articulo) contenido).setCuerpo((String) nuevosDatos.get("cuerpo"));
        } else if (contenido instanceof Video && nuevosDatos.containsKey("url")) {
            ((Video) contenido).setUrl((String) nuevosDatos.get("url"));
        } else if (contenido instanceof Imagen && nuevosDatos.containsKey("url")) {
            ((Imagen) contenido).setUrl((String) nuevosDatos.get("url"));
        }
        
        return true;
    }
    
    /**
     * Elimina un contenido
     * @param id ID del contenido a eliminar
     * @return true si la eliminación fue exitosa
     */
    public boolean eliminarContenido(String id) {
        if (usuarioActual == null || !usuarioActual.puedeEliminar()) {
            return false;
        }
        
        return contenidos.removeIf(c -> c.getId().equals(id));
    }
    
    /**
     * Publica un contenido
     * @param id ID del contenido a publicar
     * @return true si la publicación fue exitosa
     */
    public boolean publicarContenido(String id) {
        if (usuarioActual == null || !usuarioActual.puedePublicar()) {
            return false;
        }
        
        Contenido contenido = buscarContenidoPorId(id);
        if (contenido != null) {
            contenido.publicar();
            return true;
        }
        return false;
    }
    
    /**
     * Despublica un contenido
     * @param id ID del contenido a despublicar
     * @return true si la despublicación fue exitosa
     */
    public boolean despublicarContenido(String id) {
        if (usuarioActual == null || !usuarioActual.puedePublicar()) {
            return false;
        }
        
        Contenido contenido = buscarContenidoPorId(id);
        if (contenido != null) {
            contenido.despublicar();
            return true;
        }
        return false;
    }
    
    /**
     * Busca contenidos según criterio y tipo
     * @param criterio Texto a buscar
     * @param tipo Tipo de contenido (opcional)
     * @return Lista de contenidos que coinciden
     */
    public List<Contenido> buscarContenidos(String criterio, String tipo) {
        List<Contenido> resultados = new ArrayList<>();
        
        for (Contenido contenido : contenidos) {
            boolean coincideTipo = tipo == null || tipo.isEmpty() || 
                                 contenido.getClass().getSimpleName().equalsIgnoreCase(tipo);
            
            if (coincideTipo && contenido.buscar(criterio)) {
                resultados.add(contenido);
            }
        }
        
        return resultados;
    }
    
    /**
     * Filtra contenidos por categoría
     * @param categoria Categoría a filtrar
     * @return Lista de contenidos de esa categoría
     */
    public List<Contenido> filtrarPorCategoria(Categoria categoria) {
        return contenidos.stream()
                .filter(c -> c.getCategoria().getId().equals(categoria.getId()))
                .collect(Collectors.toList());
    }
    
    /**
     * Genera un reporte general del sistema
     * @return String con estadísticas del sistema
     */
    public String generarReporteGeneral() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE GENERAL DEL CMS ===\n\n");
        
       
        long totalArticulos = contenidos.stream().filter(c -> c instanceof Articulo).count();
        long totalVideos = contenidos.stream().filter(c -> c instanceof Video).count();
        long totalImagenes = contenidos.stream().filter(c -> c instanceof Imagen).count();
        long totalPublicados = contenidos.stream().filter(Contenido::isPublicado).count();
        
        reporte.append("Total de contenidos: ").append(contenidos.size()).append("\n");
        reporte.append("  - Artículos: ").append(totalArticulos).append("\n");
        reporte.append("  - Videos: ").append(totalVideos).append("\n");
        reporte.append("  - Imágenes: ").append(totalImagenes).append("\n");
        reporte.append("Contenidos publicados: ").append(totalPublicados).append("\n");
        reporte.append("Contenidos sin publicar: ").append(contenidos.size() - totalPublicados).append("\n\n");
        
        
        reporte.append("=== CONTENIDOS POR CATEGORÍA ===\n");
        for (Categoria cat : categorias) {
            long cantidad = contenidos.stream()
                    .filter(c -> c.getCategoria().getId().equals(cat.getId()))
                    .count();
            reporte.append(cat.getNombre()).append(": ").append(cantidad).append(" contenido(s)\n");
        }
        
        reporte.append("\n=== LISTA DE CONTENIDOS ===\n");
        for (Contenido c : contenidos) {
            reporte.append(c.generarReporte()).append("\n");
        }
        
        return reporte.toString();
    }
    
    /**
     * Busca un contenido por su ID
     * @param id ID del contenido
     * @return El contenido encontrado o null
     */
    private Contenido buscarContenidoPorId(String id) {
        return contenidos.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    public List<Contenido> obtenerContenidos() { return new ArrayList<>(contenidos); }
    public List<Categoria> obtenerCategorias() { return new ArrayList<>(categorias); }
    public Usuario getUsuarioActual() { return usuarioActual; }
    public void cerrarSesion() { usuarioActual = null; }
}
