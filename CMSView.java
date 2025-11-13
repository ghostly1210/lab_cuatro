import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Clase CMSView
 * Vista del sistema CMS usando Swing
 * Presenta la interfaz gráfica al usuario
 */
class CMSView extends JFrame {
    private CMSController controller;
    private JPanel panelPrincipal;
    private JTextArea areaResultados;
    
    /**
     * Constructor de CMSView
     * @param controller Controlador asociado
     */
    public CMSView(CMSController controller) {
        this.controller = controller;
        configurarVentana();
        mostrarPanelLogin();
    }
    
    /**
     * Configura la ventana principal
     */
    private void configurarVentana() {
        setTitle("Sistema de Gestión de Contenidos - EGA");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        panelPrincipal = new JPanel(new BorderLayout());
        add(panelPrincipal);
    }
    
    /**
     * Muestra el panel de login
     */
    private void mostrarPanelLogin() {
        panelPrincipal.removeAll();
        
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(240, 240, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel titulo = new JLabel("Sistema CMS - EGA");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        loginPanel.add(titulo, gbc);
        
        gbc.gridwidth = 1; gbc.gridy = 1;
        loginPanel.add(new JLabel("Email:"), gbc);
        JTextField campoEmail = new JTextField(20);
        gbc.gridx = 1;
        loginPanel.add(campoEmail, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        loginPanel.add(new JLabel("Contraseña:"), gbc);
        JPasswordField campoPassword = new JPasswordField(20);
        gbc.gridx = 1;
        loginPanel.add(campoPassword, gbc);
        
        JButton btnLogin = new JButton("Iniciar Sesión");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        btnLogin.setBackground(new Color(76, 175, 80));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        
        btnLogin.addActionListener(e -> {
            String email = campoEmail.getText();
            String password = new String(campoPassword.getPassword());
            
            if (controller.iniciarSesion(email, password)) {
                mostrarMenuPrincipal();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Credenciales incorrectas", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        loginPanel.add(btnLogin, gbc);
        
        JLabel info = new JLabel("<html><center>Usuarios de prueba:<br>" +
                                "admin@ega.edu / admin123<br>" +
                                "editor@ega.edu / editor123</center></html>");
        info.setFont(new Font("Arial", Font.PLAIN, 11));
        gbc.gridy = 4;
        loginPanel.add(info, gbc);
        
        panelPrincipal.add(loginPanel, BorderLayout.CENTER);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }
    
    /**
     * Muestra el menú principal
     */
    public void mostrarMenuPrincipal() {
        panelPrincipal.removeAll();
        
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(63, 81, 181));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        Usuario usuario = controller.getUsuarioActual();
        JLabel lblUsuario = new JLabel("Usuario: " + usuario.getNombre() + " (" + usuario.getRol() + ")");
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 14));
        panelSuperior.add(lblUsuario, BorderLayout.WEST);
        
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setBackground(new Color(244, 67, 54));
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.addActionListener(e -> {
            controller.cerrarSesion();
            mostrarPanelLogin();
        });
        panelSuperior.add(btnCerrarSesion, BorderLayout.EAST);
        
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setBackground(new Color(250, 250, 250));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton btnCrearContenido = crearBoton("Crear Contenido", new Color(76, 175, 80));
        btnCrearContenido.addActionListener(e -> mostrarFormularioCrear());
        
        JButton btnVerContenidos = crearBoton("Ver Contenidos", new Color(33, 150, 243));
        btnVerContenidos.addActionListener(e -> mostrarContenidos(controller.obtenerContenidos()));
        
        JButton btnBuscar = crearBoton("Buscar", new Color(255, 152, 0));
        btnBuscar.addActionListener(e -> mostrarFormularioBuscar());
        
        JButton btnReporte = crearBoton("Generar Reporte", new Color(156, 39, 176));
        btnReporte.addActionListener(e -> mostrarReporte(controller.generarReporteGeneral()));
        
        panelBotones.add(btnCrearContenido);
        panelBotones.add(Box.createRigidArea(new Dimension(0, 10)));
        panelBotones.add(btnVerContenidos);
        panelBotones.add(Box.createRigidArea(new Dimension(0, 10)));
        panelBotones.add(btnBuscar);
        panelBotones.add(Box.createRigidArea(new Dimension(0, 10)));
        panelBotones.add(btnReporte);
        
        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        areaResultados.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaResultados.setText("Bienvenido al Sistema de Gestión de Contenidos - EGA\n\n" +
                              "Seleccione una opción del menú lateral para comenzar.");
        JScrollPane scrollResultados = new JScrollPane(areaResultados);
        scrollResultados.setBorder(BorderFactory.createTitledBorder("Área de Trabajo"));
        
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelBotones, BorderLayout.WEST);
        panelPrincipal.add(scrollResultados, BorderLayout.CENTER);
        
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }
    
    /**
     * Crea un botón estilizado
     */
    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setMaximumSize(new Dimension(200, 40));
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        return boton;
    }
    
    /**
     * Muestra el formulario para crear contenido
     */
    private void mostrarFormularioCrear() {
        JDialog dialogo = new JDialog(this, "Crear Contenido", true);
        dialogo.setSize(500, 600);
        dialogo.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Tipo:"), gbc);
        String[] tipos = {"Articulo", "Video", "Imagen"};
        JComboBox<String> comboTipo = new JComboBox<>(tipos);
        gbc.gridx = 1;
        panel.add(comboTipo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Título:"), gbc);
        JTextField campoTitulo = new JTextField(20);
        gbc.gridx = 1;
        panel.add(campoTitulo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Categoría:"), gbc);
        JComboBox<String> comboCategoria = new JComboBox<>();
        for (Categoria cat : controller.obtenerCategorias()) {
            comboCategoria.addItem(cat.getNombre());
        }
        gbc.gridx = 1;
        panel.add(comboCategoria, gbc);
        
        JPanel panelDinamico = new JPanel(new GridBagLayout());
        GridBagConstraints gbcDin = new GridBagConstraints();
        gbcDin.insets = new Insets(5, 5, 5, 5);
        gbcDin.fill = GridBagConstraints.HORIZONTAL;
        
        gbcDin.gridx = 0; gbcDin.gridy = 0;
        JLabel lblCampo1 = new JLabel("Contenido:");
        JTextArea areaCuerpo = new JTextArea(10, 20);
        JScrollPane scrollCuerpo = new JScrollPane(areaCuerpo);
        
        JLabel lblUrl = new JLabel("URL:");
        JTextField campoUrl = new JTextField(20);
        JLabel lblExtra = new JLabel("Duración (seg):");
        JTextField campoExtra = new JTextField(20);
        
        comboTipo.addActionListener(e -> {
            panelDinamico.removeAll();
            String tipoSeleccionado = (String) comboTipo.getSelectedItem();
            
            if ("Articulo".equals(tipoSeleccionado)) {
                gbcDin.gridx = 0; gbcDin.gridy = 0;
                panelDinamico.add(lblCampo1, gbcDin);
                gbcDin.gridx = 0; gbcDin.gridy = 1; gbcDin.gridwidth = 2;
                panelDinamico.add(scrollCuerpo, gbcDin);
            } else if ("Video".equals(tipoSeleccionado)) {
                lblExtra.setText("Duración (seg):");
                gbcDin.gridx = 0; gbcDin.gridy = 0; gbcDin.gridwidth = 1;
                panelDinamico.add(lblUrl, gbcDin);
                gbcDin.gridx = 1;
                panelDinamico.add(campoUrl, gbcDin);
                gbcDin.gridx = 0; gbcDin.gridy = 1;
                panelDinamico.add(lblExtra, gbcDin);
                gbcDin.gridx = 1;
                panelDinamico.add(campoExtra, gbcDin);
            } else { 
                lblExtra.setText("Resolución:");
                gbcDin.gridx = 0; gbcDin.gridy = 0; gbcDin.gridwidth = 1;
                panelDinamico.add(lblUrl, gbcDin);
                gbcDin.gridx = 1;
                panelDinamico.add(campoUrl, gbcDin);
                gbcDin.gridx = 0; gbcDin.gridy = 1;
                panelDinamico.add(lblExtra, gbcDin);
                gbcDin.gridx = 1;
                panelDinamico.add(campoExtra, gbcDin);
            }
            
            panelDinamico.revalidate();
            panelDinamico.repaint();
        });
        
        comboTipo.setSelectedIndex(0);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(panelDinamico, gbc);
        
        JPanel panelBotones = new JPanel();
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new Color(76, 175, 80));
        btnGuardar.setForeground(Color.WHITE);
        
        btnGuardar.addActionListener(e -> {
            String tipo = (String) comboTipo.getSelectedItem();
            String titulo = campoTitulo.getText();
            String catNombre = (String) comboCategoria.getSelectedItem();
            
            if (titulo.isEmpty()) {
                JOptionPane.showMessageDialog(dialogo, "El título es obligatorio");
                return;
            }
            
            Categoria categoria = null;
            for (Categoria cat : controller.obtenerCategorias()) {
                if (cat.getNombre().equals(catNombre)) {
                    categoria = cat;
                    break;
                }
            }
            
            Map<String, Object> datos = new HashMap<>();
            datos.put("titulo", titulo);
            datos.put("categoria", categoria);
            
            try {
                if ("Articulo".equals(tipo)) {
                    datos.put("cuerpo", areaCuerpo.getText());
                } else if ("Video".equals(tipo)) {
                    datos.put("url", campoUrl.getText());
                    datos.put("duracion", Integer.parseInt(campoExtra.getText()));
                } else { 
                    datos.put("url", campoUrl.getText());
                    datos.put("resolucion", campoExtra.getText());
                }
                
                Contenido contenido = controller.crearContenido(tipo, datos);
                if (contenido != null) {
                    JOptionPane.showMessageDialog(dialogo, "Contenido creado exitosamente");
                    dialogo.dispose();
                    mostrarContenidos(controller.obtenerContenidos());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialogo, "La duración debe ser un número");
            }
        });
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dialogo.dispose());
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        gbc.gridy = 4;
        panel.add(panelBotones, gbc);
        
        dialogo.add(panel);
        dialogo.setVisible(true);
    }
    
    /**
     * Muestra la lista de contenidos
     */
    public void mostrarContenidos(List<Contenido> contenidos) {
        if (contenidos.isEmpty()) {
            areaResultados.setText("No hay contenidos para mostrar.");
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== LISTA DE CONTENIDOS ===\n\n");
        
        for (int i = 0; i < contenidos.size(); i++) {
            Contenido c = contenidos.get(i);
            sb.append((i + 1)).append(". ");
            sb.append("[").append(c.getClass().getSimpleName()).append("] ");
            sb.append(c.getTitulo());
            sb.append(" - ").append(c.isPublicado() ? "Publicado" : "Borrador");
            sb.append(" (ID: ").append(c.getId().substring(0, 8)).append(")\n");
        }
        
        sb.append("\n--- Haga doble clic en un contenido para ver detalles ---\n");
        
        areaResultados.setText(sb.toString());
        
        JPanel panelLista = new JPanel(new BorderLayout());
        
        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        for (Contenido c : contenidos) {
            modeloLista.addElement(String.format("[%s] %s - %s", 
                c.getClass().getSimpleName(), 
                c.getTitulo(),
                c.isPublicado() ? "Publicado" : "Borrador"));
        }
        
        JList<String> lista = new JList<>(modeloLista);
        lista.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        lista.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = lista.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        mostrarDetalleContenido(contenidos.get(index));
                    }
                }
            }
        });
        
        JScrollPane scrollLista = new JScrollPane(lista);
        scrollLista.setBorder(BorderFactory.createTitledBorder("Contenidos (doble clic para detalles)"));
        
        for (Component comp : panelPrincipal.getComponents()) {
            if (comp instanceof JScrollPane) {
                panelPrincipal.remove(comp);
                panelPrincipal.add(scrollLista, BorderLayout.CENTER);
                break;
            }
        }
        
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }
    
    /**
     * Muestra los detalles de un contenido
     */
    private void mostrarDetalleContenido(Contenido contenido) {
        JDialog dialogo = new JDialog(this, "Detalle de Contenido", true);
        dialogo.setSize(600, 500);
        dialogo.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextArea areaDetalle = new JTextArea(contenido.visualizar());
        areaDetalle.setEditable(false);
        areaDetalle.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(areaDetalle);
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        
        Usuario usuario = controller.getUsuarioActual();
        
        if (usuario.puedePublicar()) {
            JButton btnPublicar = new JButton(contenido.isPublicado() ? "Despublicar" : "Publicar");
            btnPublicar.setBackground(contenido.isPublicado() ? new Color(255, 152, 0) : new Color(76, 175, 80));
            btnPublicar.setForeground(Color.WHITE);
            btnPublicar.addActionListener(e -> {
                if (contenido.isPublicado()) {
                    controller.despublicarContenido(contenido.getId());
                    JOptionPane.showMessageDialog(dialogo, "Contenido despublicado");
                } else {
                    controller.publicarContenido(contenido.getId());
                    JOptionPane.showMessageDialog(dialogo, "Contenido publicado");
                }
                dialogo.dispose();
                mostrarContenidos(controller.obtenerContenidos());
            });
            panelBotones.add(btnPublicar);
        }
        
        JButton btnEditar = new JButton("Editar");
        btnEditar.setBackground(new Color(33, 150, 243));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.addActionListener(e -> {
            dialogo.dispose();
            mostrarFormularioEditar(contenido);
        });
        panelBotones.add(btnEditar);
        
        if (usuario.puedeEliminar()) {
            JButton btnEliminar = new JButton("Eliminar");
            btnEliminar.setBackground(new Color(244, 67, 54));
            btnEliminar.setForeground(Color.WHITE);
            btnEliminar.addActionListener(e -> {
                int confirmar = JOptionPane.showConfirmDialog(dialogo,
                    "¿Está seguro de eliminar este contenido?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);
                
                if (confirmar == JOptionPane.YES_OPTION) {
                    controller.eliminarContenido(contenido.getId());
                    JOptionPane.showMessageDialog(dialogo, "Contenido eliminado");
                    dialogo.dispose();
                    mostrarContenidos(controller.obtenerContenidos());
                }
            });
            panelBotones.add(btnEliminar);
        }
        
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dialogo.dispose());
        panelBotones.add(btnCerrar);
        
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        dialogo.add(panel);
        dialogo.setVisible(true);
    }
    
    /**
     * Muestra el formulario para editar contenido
     */
    private void mostrarFormularioEditar(Contenido contenido) {
        JDialog dialogo = new JDialog(this, "Editar Contenido", true);
        dialogo.setSize(500, 400);
        dialogo.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Título:"), gbc);
        JTextField campoTitulo = new JTextField(contenido.getTitulo(), 20);
        gbc.gridx = 1;
        panel.add(campoTitulo, gbc);
        
        JComponent campoEspecifico = null;
        gbc.gridx = 0; gbc.gridy = 1;
        
        if (contenido instanceof Articulo) {
            panel.add(new JLabel("Contenido:"), gbc);
            JTextArea areaCuerpo = new JTextArea(((Articulo) contenido).getCuerpo(), 10, 20);
            campoEspecifico = new JScrollPane(areaCuerpo);
            gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
            panel.add(campoEspecifico, gbc);
        } else if (contenido instanceof Video) {
            panel.add(new JLabel("URL:"), gbc);
            JTextField campoUrl = new JTextField(((Video) contenido).getUrl(), 20);
            campoEspecifico = campoUrl;
            gbc.gridx = 1; gbc.gridy = 1;
            panel.add(campoEspecifico, gbc);
        } else if (contenido instanceof Imagen) {
            panel.add(new JLabel("URL:"), gbc);
            JTextField campoUrl = new JTextField(((Imagen) contenido).getUrl(), 20);
            campoEspecifico = campoUrl;
            gbc.gridx = 1; gbc.gridy = 1;
            panel.add(campoEspecifico, gbc);
        }
        
        JPanel panelBotones = new JPanel();
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new Color(76, 175, 80));
        btnGuardar.setForeground(Color.WHITE);
        
        JComponent finalCampoEspecifico = campoEspecifico;
        btnGuardar.addActionListener(e -> {
            Map<String, Object> datos = new HashMap<>();
            datos.put("titulo", campoTitulo.getText());
            
            if (contenido instanceof Articulo) {
                JTextArea area = (JTextArea) ((JScrollPane) finalCampoEspecifico).getViewport().getView();
                datos.put("cuerpo", area.getText());
            } else if (contenido instanceof Video || contenido instanceof Imagen) {
                datos.put("url", ((JTextField) finalCampoEspecifico).getText());
            }
            
            if (controller.editarContenido(contenido.getId(), datos)) {
                JOptionPane.showMessageDialog(dialogo, "Contenido actualizado");
                dialogo.dispose();
                mostrarContenidos(controller.obtenerContenidos());
            }
        });
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dialogo.dispose());
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(panelBotones, gbc);
        
        dialogo.add(panel);
        dialogo.setVisible(true);
    }
    
    /**
     * Muestra el formulario de búsqueda
     */
    private void mostrarFormularioBuscar() {
        JDialog dialogo = new JDialog(this, "Buscar Contenidos", true);
        dialogo.setSize(400, 200);
        dialogo.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Buscar:"), gbc);
        JTextField campoBusqueda = new JTextField(20);
        gbc.gridx = 1;
        panel.add(campoBusqueda, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Tipo:"), gbc);
        String[] tipos = {"Todos", "Articulo", "Video", "Imagen"};
        JComboBox<String> comboTipo = new JComboBox<>(tipos);
        gbc.gridx = 1;
        panel.add(comboTipo, gbc);
        
    
        JPanel panelBotones = new JPanel();
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(33, 150, 243));
        btnBuscar.setForeground(Color.WHITE);
        
        btnBuscar.addActionListener(e -> {
            String criterio = campoBusqueda.getText();
            String tipo = (String) comboTipo.getSelectedItem();
            
            if (criterio.isEmpty()) {
                JOptionPane.showMessageDialog(dialogo, "Ingrese un criterio de búsqueda");
                return;
            }
            
            String tipoBusqueda = "Todos".equals(tipo) ? null : tipo;
            List<Contenido> resultados = controller.buscarContenidos(criterio, tipoBusqueda);
            
            dialogo.dispose();
            mostrarContenidos(resultados);
        });
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dialogo.dispose());
        
        panelBotones.add(btnBuscar);
        panelBotones.add(btnCancelar);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(panelBotones, gbc);
        
        dialogo.add(panel);
        dialogo.setVisible(true);
    }
    
    /**
     * Muestra un reporte en el área de resultados
     */
    public void mostrarReporte(String reporte) {
        areaResultados.setText(reporte);
    }
    
    /**
     * Muestra un mensaje al usuario
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}