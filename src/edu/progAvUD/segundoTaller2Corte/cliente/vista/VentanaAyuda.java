package edu.progAvUD.segundoTaller2Corte.cliente.vista;

import java.net.URL;
import javax.swing.*;

/**
 *
 * @author Andres Felipe
 */
public class VentanaAyuda extends JFrame {

    private JScrollPane panelPrincipal;
    private JEditorPane html;

    public VentanaAyuda() {
        super("Ventana de Ayuda :");
        setSize(600, 700);
        setLocation(450, 0);
        panelPrincipal = new JScrollPane();

        try {
            URL url = getClass().getResource("index.html");
            html = new JEditorPane(url);
            html.setEditable(false);
        } catch (Exception e) {
            html = new JEditorPane();
            html.setText("Error al cargar la ayuda: " + e.getMessage());
        }

        JViewport jv = panelPrincipal.getViewport();
        jv.add(html);

        setContentPane(panelPrincipal);
    }
}
