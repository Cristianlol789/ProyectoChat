package edu.progAvUD.segundoTaller2Corte.cliente.vista;

import java.net.URL;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 * Clase DialogAyuda
 * 
 * Esta clase representa una ventana de diálogo que muestra una página de ayuda
 * HTML dentro de un componente gráfico (`JEditorPane`) incrustado en un
 * `JScrollPane`. Se utiliza para proporcionar al usuario documentación o guía 
 * sobre el uso del sistema cliente.
 * 
 * Se carga un archivo HTML llamado "index.html" ubicado en el mismo paquete
 * de la clase. Si ocurre un error al cargar el archivo, se muestra un mensaje 
 * alternativo de error dentro del panel.
 * 
 * @author And
 */
public class DialogAyuda extends JDialog {

    // Panel con barras de desplazamiento que contiene el contenido HTML
    private JScrollPane panelPrincipal;

    // Componente que carga y muestra el archivo HTML de ayuda
    private JEditorPane html;

    /**
     * Constructor de la clase DialogAyuda.
     * Inicializa la ventana, configura su tamaño, posición y carga el archivo
     * HTML de ayuda. El archivo debe estar en el mismo paquete y llamarse "index.html".
     *
     * @throws Exception si ocurre un error al cargar el archivo HTML.
     */
    public DialogAyuda() throws Exception {
        setSize(600, 700);                 // Tamaño de la ventana
        setLocation(450, 0);               // Posición en la pantalla
        panelPrincipal = new JScrollPane();

        // Carga el archivo HTML "index.html" desde el mismo paquete
        URL url = getClass().getResource("index.html");
        html = new JEditorPane(url);
        html.setEditable(false);          // El contenido no se puede modificar

        // Agrega el contenido HTML al viewport del JScrollPane
        JViewport jv = panelPrincipal.getViewport();
        jv.add(html);

        // Establece el JScrollPane como el contenido principal de la ventana
        setContentPane(panelPrincipal);
    }

    /**
     * Método para mostrar un mensaje de error en caso de que no se pueda cargar la ayuda.
     * Crea un nuevo JEditorPane con el mensaje de error y lo muestra en lugar del HTML.
     *
     * @param e Excepción ocurrida al intentar cargar el archivo de ayuda.
     */
    public void errorAyuda(Exception e) {
        html = new JEditorPane();
        html.setText("Error al cargar la ayuda: " + e.getMessage());
    }

}