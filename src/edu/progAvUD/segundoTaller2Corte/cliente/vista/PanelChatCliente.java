/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progAvUD.segundoTaller2Corte.cliente.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author and
 */
public class PanelChatCliente extends JPanel {

    public JTextArea panMostrar;
    public JTextField txtMensage;
    public JButton butEnviar;
    public JLabel lblNomUser;
    public JList<String> lstActivos;
    public JButton butPrivado;
//    public JMenuBar barraMenu;
//    public JMenu JMAyuda;
//    public JMenuItem help;
//    public JMenu JMAcerca;
    //public JMenuItem acercaD;
    //private ControlGrafico controlGrafico;

    public PanelChatCliente(/*ControlGrafico controlGrafico*/) {
//        super("Cliente Chat");
//        this.controlGrafico = controlGrafico;
        initComponents();
        setupLayout();
//        setupEventListeners();

        setSize(450, 430);
        setLocation(120, 90);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents() {
        txtMensage = new JTextField(30);
        butEnviar = new JButton("Enviar");
        lblNomUser = new JLabel("Usuario <<  >>");
        lblNomUser.setHorizontalAlignment(JLabel.CENTER);
        panMostrar = new JTextArea();
        panMostrar.setColumns(25);
        panMostrar.setEditable(false);
        panMostrar.setForeground(Color.BLUE);
        panMostrar.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, new Color(25, 10, 80)));

        lstActivos = new JList<>();
        butPrivado = new JButton("Privado");

        // Menu
//        barraMenu = new JMenuBar();
//        JMAyuda = new JMenu("Ayuda");
//        help = new JMenuItem("Ayuda");
//        help.setActionCommand("help");
//
//        JMAcerca = new JMenu("Acerca de");
//        acercaD = new JMenuItem("Creditos");
//        acercaD.setActionCommand("Acerca");
//
//        JMAyuda.add(help);
//        JMAcerca.add(acercaD);
//        barraMenu.add(JMAcerca);
//        barraMenu.add(JMAyuda);
    }

    private void setupLayout() {
        JPanel panAbajo = new JPanel();
        panAbajo.setLayout(new BorderLayout());
        panAbajo.add(new JLabel("  Ingrese mensage a enviar:"), BorderLayout.NORTH);
        panAbajo.add(txtMensage, BorderLayout.CENTER);
        panAbajo.add(butEnviar, BorderLayout.EAST);

        JPanel panRight = new JPanel();
        panRight.setLayout(new BorderLayout());
        panRight.add(lblNomUser, BorderLayout.NORTH);
        panRight.add(new JScrollPane(panMostrar), BorderLayout.CENTER);
        panRight.add(panAbajo, BorderLayout.SOUTH);

        JPanel panLeft = new JPanel();
        panLeft.setLayout(new BorderLayout());
        panLeft.add(new JScrollPane(lstActivos), BorderLayout.CENTER);
        panLeft.add(butPrivado, BorderLayout.NORTH);

        JSplitPane sldCentral = new JSplitPane();
        sldCentral.setDividerLocation(100);
        sldCentral.setDividerSize(7);
        sldCentral.setOneTouchExpandable(true);
        sldCentral.setLeftComponent(panLeft);
        sldCentral.setRightComponent(panRight);

        setLayout(new BorderLayout());
        add(sldCentral, BorderLayout.CENTER);
//        add(barraMenu, BorderLayout.NORTH);

        txtMensage.requestFocus();
    }

//    private void setupEventListeners() {
//        butEnviar.setActionCommand("enviar_mensaje");
//        butEnviar.addActionListener(controlGrafico);
//
//        txtMensage.setActionCommand("enviar_mensaje");
//        txtMensage.addActionListener(controlGrafico);
//
//        butPrivado.setActionCommand("mensaje_privado");
//        butPrivado.addActionListener(controlGrafico);
//
//        help.addActionListener(controlGrafico);
//        acercaD.addActionListener(controlGrafico);
//    }
    public void setNombreUser(String user) {
        lblNomUser.setText("Usuario " + user);
    }

    public void mostrarMsg(String msg) {
        panMostrar.append(msg + "\n");
    }

    public void ponerActivos(Vector<String> datos) {
        lstActivos.setListData(datos);
    }

    public String obtenerTextoMensaje() {
        return txtMensage.getText();
    }

    public void limpiarTextoMensaje() {
        txtMensage.setText("");
    }

    public int obtenerUsuarioSeleccionado() {
        return lstActivos.getSelectedIndex();

    }
}
