/**
 * Clase Main
 * Punto de entrada de la aplicación
 */
public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            CMSView vista = new CMSView(null);
            CMSController controller = new CMSController(vista);
            vista.setVisible(true);
        });
    }
}