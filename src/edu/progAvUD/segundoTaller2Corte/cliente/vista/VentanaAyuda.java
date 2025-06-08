/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author Andres Felipe
 */
// VentanaAyuda.java (Sin cambios - solo vista)
import java.awt.Color;
import java.awt.Font;
import java.net.URL;
import javax.swing.*;

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
