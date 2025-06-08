package edu.progAvUD.segundoTaller2Corte.cliente.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Clase PanelChatCliente
 * 
 * Representa el panel principal de la interfaz gráfica del cliente en el chat.
 * Contiene componentes para mostrar mensajes, ingresar texto para enviar,
 * mostrar la lista de usuarios activos, y botones para enviar mensajes
 * y abrir chats privados.
 * 
 * Este panel organiza sus elementos en una estructura dividida que permite
 * visualizar la lista de usuarios a la izquierda y la conversación junto con
 * el campo de texto y botones a la derecha.
 * 
 * Autor: and
 */
public class PanelChatCliente extends JPanel {

    /**
     * Área de texto donde se muestran los mensajes recibidos y enviados.
     * No es editable para evitar modificaciones accidentales.
     */
    public JTextArea panMostrar;

    /**
     * Campo de texto donde el usuario escribe el mensaje que desea enviar.
     */
    public JTextField txtMensage;

    /**
     * Botón para enviar el mensaje escrito en el campo de texto.
     */
    public JButton butEnviar;

    /**
     * Etiqueta que muestra el nombre del usuario actualmente conectado.
     */
    public JLabel lblNomUser;

    /**
     * Lista gráfica que muestra los usuarios activos en el chat.
     */
    public JList<String> lstActivos;

    /**
     * Botón para iniciar una conversación privada con un usuario seleccionado.
     */
    public JButton butPrivado;

    /**
     * Constructor de la clase PanelChatCliente.
     * Inicializa los componentes gráficos y establece la organización del panel.
     * 
     * Configura tamaño y posición, aunque estos pueden ser ignorados en un contenedor padre.
     * El constructor también tiene código comentado para agregar listeners y barra de menú.
     */
    public PanelChatCliente() {
        
        initComponents();  // Crear los componentes gráficos
        setupLayout();     // Organizar los componentes dentro del panel

        setSize(450, 430);  // Tamaño del panel
        setLocation(120, 90); // Posición del panel (puede no usarse dependiendo del layout padre)
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Método que crea y configura los componentes gráficos: campo de texto,
     * botones, etiquetas, área de texto para mostrar mensajes y lista de usuarios activos.
     * También se personalizan colores y bordes para una mejor apariencia visual.
     */
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

    }

    /**
     * Método que organiza los componentes en el panel utilizando un BorderLayout
     * y un JSplitPane para dividir la interfaz en dos secciones:
     * 
     * - Izquierda: lista de usuarios activos y botón para iniciar chats privados.
     * - Derecha: etiqueta con nombre de usuario, área para mostrar mensajes y
     *   panel inferior con campo para escribir mensajes y botón para enviar.
     * 
     * El campo de texto para ingresar mensajes recibe el foco para facilitar la escritura.
     */
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

    /**
     * Actualiza la etiqueta que muestra el nombre del usuario conectado.
     * 
     * @param user Nombre del usuario a mostrar.
     */
    public void setNombreUser(String user) {
        lblNomUser.setText("Usuario " + user);
    }

    /**
     * Agrega un mensaje al área de texto para mostrarlo en la conversación.
     * El mensaje se añade en una línea nueva.
     * 
     * @param msg Mensaje a mostrar.
     */
    public void mostrarMsg(String msg) {
        panMostrar.append(msg + "\n");
    }

    /**
     * Actualiza la lista gráfica de usuarios activos con los datos recibidos.
     * 
     * @param datos Vector con los nombres de los usuarios activos.
     */
    public void ponerActivos(Vector<String> datos) {
        lstActivos.setListData(datos);
    }

    /**
     * Obtiene el texto actual escrito en el campo para enviar mensajes.
     * 
     * @return Texto ingresado por el usuario.
     */
    public String obtenerTextoMensaje() {
        return txtMensage.getText();
    }

    /**
     * Limpia el campo de texto para que el usuario pueda escribir un nuevo mensaje.
     */
    public void limpiarTextoMensaje() {
        txtMensage.setText("");
    }

    /**
     * Obtiene el índice del usuario actualmente seleccionado en la lista de usuarios activos.
     * 
     * @return Índice del usuario seleccionado o -1 si no hay selección.
     */
    public int obtenerUsuarioSeleccionado() {
        return lstActivos.getSelectedIndex();

    }
}