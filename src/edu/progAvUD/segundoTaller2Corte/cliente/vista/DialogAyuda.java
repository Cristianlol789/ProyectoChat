/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progAvUD.segundoTaller2Corte.cliente.vista;

import java.net.URL;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 *
 * @author and
 */
public class DialogAyuda extends JDialog {

    private JScrollPane panelPrincipal;
    private JEditorPane html;

    public DialogAyuda() throws Exception {
        //super("Ventana de Ayuda :");
        setSize(600, 700);
        setLocation(450, 0);
        panelPrincipal = new JScrollPane();

        URL url = getClass().getResource("index.html");
        html = new JEditorPane(url);
        html.setEditable(false);

        JViewport jv = panelPrincipal.getViewport();
        jv.add(html);

        setContentPane(panelPrincipal);
    }

    public void errorAyuda(Exception e) {
        html = new JEditorPane();
        html.setText("Error al cargar la ayuda: " + e.getMessage());
    }

}
