/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progAvUD.segundoTaller2Corte.cliente.vista;

import edu.progAvUD.segundoTaller2Corte.cliente.control.ControlGrafico;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author and
 */
public class DialogChatPrivado extends JDialog {
    
    public JTextArea panMostrar;
    public JTextField txtMensage;
    public JButton butEnviar;
    private String amigo;
//    private ControlGrafico controlGrafico;

    public DialogChatPrivado(/*ControlGrafico controlGrafico*/) {
        
        //super("Amigo");
        //this.controlGrafico = controlGrafico;
        this.amigo = "";
        initComponents();
        setupLayout();
        //setupEventListeners();

        setSize(300, 300);
        setLocation(570, 90);
    }

    private void initComponents() {
        txtMensage = new JTextField(30);
        butEnviar = new JButton("Enviar");
        panMostrar = new JTextArea();
        panMostrar.setEditable(false);
    }

    private void setupLayout() {
        JPanel panAbajo = new JPanel();
        panAbajo.setLayout(new BorderLayout());
        panAbajo.add(new JLabel("  Ingrese mensage a enviar:"), BorderLayout.NORTH);
        panAbajo.add(txtMensage, BorderLayout.CENTER);
        panAbajo.add(butEnviar, BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(new JScrollPane(panMostrar), BorderLayout.CENTER);
        add(panAbajo, BorderLayout.SOUTH);

        txtMensage.requestFocus();
    }

//    private void setupEventListeners() {
//        butEnviar.setActionCommand("enviar_privado");
//        butEnviar.addActionListener(controlGrafico);
//
//        txtMensage.setActionCommand("enviar_privado");
//        txtMensage.addActionListener(controlGrafico);
//    }
//
    public void setAmigo(String ami) {
        this.amigo = ami;
        setTitle(ami);
    }

    public String getAmigo() {
        return amigo;
    }

    public void mostrarMsg(String msg) {
        panMostrar.append(msg + "\n");
    }

    public String obtenerTextoMensaje() {
        return txtMensage.getText();
    }

    public void limpiarTextoMensaje() {
        txtMensage.setText("");
    }
    
}
