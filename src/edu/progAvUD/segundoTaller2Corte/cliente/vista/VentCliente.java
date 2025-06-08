package edu.progAvUD.segundoTaller2Corte.cliente.vista;

import edu.progAvUD.segundoTaller2Corte.cliente.control.ControlGrafico;
import java.awt.*;
import java.util.Vector;
import javax.swing.*;

public class VentCliente extends JFrame {
    
    private JTextArea panMostrar;
    private JTextField txtMensage;
    private JButton butEnviar;
    private JLabel lblNomUser;
    private JList<String> lstActivos;
    private JButton butPrivado;
    private JMenuBar barraMenu;
    private JMenu JMAyuda;
    private JMenuItem help;
    private JMenu JMAcerca;
    private JMenuItem acercaD;
    private ControlGrafico controlGrafico;
    
    public VentCliente(ControlGrafico controlGrafico) {
        super("Cliente Chat");
        this.controlGrafico = controlGrafico;
        initComponents();
        setupLayout();
        setupEventListeners();
        
        setSize(450, 430);
        setLocation(120, 90);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        barraMenu = new JMenuBar();
        JMAyuda = new JMenu("Ayuda");
        help = new JMenuItem("Ayuda");
        help.setActionCommand("help");
        
        JMAcerca = new JMenu("Acerca de");
        acercaD = new JMenuItem("Creditos");
        acercaD.setActionCommand("Acerca");
        
        JMAyuda.add(help);
        JMAcerca.add(acercaD);
        barraMenu.add(JMAcerca);
        barraMenu.add(JMAyuda);
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
        add(barraMenu, BorderLayout.NORTH);
        
        txtMensage.requestFocus();
    }
    
    private void setupEventListeners() {
        butEnviar.setActionCommand("enviar_mensaje");
        butEnviar.addActionListener(controlGrafico);
        
        txtMensage.setActionCommand("enviar_mensaje");
        txtMensage.addActionListener(controlGrafico);
        
        butPrivado.setActionCommand("mensaje_privado");
        butPrivado.addActionListener(controlGrafico);
        
        help.addActionListener(controlGrafico);
        acercaD.addActionListener(controlGrafico);
    }
    
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