class Administrador extends Usuario {
    
    /**
     * Constructor de Administrador
     * @param nombre Nombre del administrador
     * @param email Email del administrador
     * @param password Contraseña del administrador
     */
    public Administrador(String nombre, String email, String password) {
        super(nombre, email, password);
        this.rol = "Administrador";
    }
    
    @Override
    public boolean puedePublicar() {
        return true;
    }
    
    @Override
    public boolean puedeEliminar() {
        return true;
    }
}

/**
 * Clase Editor
 * Usuario con permisos limitados en el sistema
 */
class Editor extends Usuario {
    
    /**
     * Constructor de Editor
     * @param nombre Nombre del editor
     * @param email Email del editor
     * @param password Contraseña del editor
     */
    public Editor(String nombre, String email, String password) {
        super(nombre, email, password);
        this.rol = "Editor";
    }
    
    @Override
    public boolean puedePublicar() {
        return false;
    }
    
    @Override
    public boolean puedeEliminar() {
        return false;
    }
}
