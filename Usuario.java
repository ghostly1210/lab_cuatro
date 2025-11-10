abstract class Usuario {
    protected String id;
    protected String nombre;
    protected String email;
    protected String password;
    protected String rol;
    
    /**
     * Constructor de Usuario
     * @param nombre Nombre del usuario
     * @param email Email del usuario
     * @param password Contraseña del usuario
     */
    public Usuario(String nombre, String email, String password) {
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }
    
    /**
     * Determina si el usuario puede publicar contenidos
     * @return true si puede publicar
     */
    public abstract boolean puedePublicar();
    
    /**
     * Determina si el usuario puede eliminar contenidos
     * @return true si puede eliminar
     */
    public abstract boolean puedeEliminar();
    
    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getRol() { return rol; }
    
    /**
     * Verifica la contraseña del usuario
     * @param password Contraseña a verificar
     * @return true si coincide
     */
    public boolean verificarPassword(String password) {
        return this.password.equals(password);
    }
}
