package edu.progAvUD.segundoTaller2Corte.cliente.vista;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Clase DialogChatPrivado
 * 
 * Representa una ventana de diálogo para la comunicación privada entre usuarios.
 * Contiene un área para mostrar mensajes recibidos, un campo de texto para escribir
 * nuevos mensajes, y un botón para enviar esos mensajes al destinatario privado.
 * 
 * La ventana está diseñada para mostrar y enviar mensajes a un "amigo" específico,
 * cuyo nombre se puede establecer y visualizar en el título de la ventana.
 * 
 * Autor: and
 */
public class DialogChatPrivado extends JDialog {
    
    /**
     * Área de texto donde se muestran los mensajes recibidos y enviados en el chat privado.
     * No editable por el usuario para evitar modificaciones accidentales.
     */
    public JTextArea panMostrar;

    /**
     * Campo de texto donde el usuario escribe el mensaje que desea enviar.
     */
    public JTextField txtMensage;

    /**
     * Botón que al ser presionado envía el mensaje escrito en el campo de texto.
     */
    public JButton butEnviar;
    
    /**
     * Nombre del usuario destinatario del chat privado.
     */
    private String amigo;
    
    // Comentado porque no está activo en esta versión, pero podría usarse para manejar eventos
    // private ControlGrafico controlGrafico;

    /**
     * Constructor de la clase DialogChatPrivado.
     * Inicializa los componentes gráficos, establece el diseño de la ventana,
     * y configura tamaño y posición inicial.
     * 
     * El diálogo se inicia con un nombre de amigo vacío y el título de la ventana no está establecido.
     * 
     * @param controlGrafico (comentado) referencia para manejar eventos, actualmente no utilizada.
     */
    public DialogChatPrivado(/*ControlGrafico controlGrafico*/) {
        
        //super("Amigo");
        //this.controlGrafico = controlGrafico;
        this.amigo = "";  // Inicializa el destinatario como cadena vacía
        
        initComponents(); // Crea y configura los componentes gráficos
        setupLayout();    // Organiza los componentes en la ventana
        //setupEventListeners(); // Comentado, para agregar eventos si se desea

        setSize(300, 300);        // Tamaño de la ventana
        setLocation(570, 90);     // Posición de la ventana en la pantalla
    }

    /**
     * Método que crea los componentes visuales del diálogo:
     * campo de texto, botón de enviar y área para mostrar mensajes.
     */
    private void initComponents() {
        txtMensage = new JTextField(30);
        butEnviar = new JButton("Enviar");
        panMostrar = new JTextArea();
        panMostrar.setEditable(false);  // El área de texto para mensajes no es editable
    }

    /**
     * Método que organiza los componentes gráficos en la ventana utilizando
     * un BorderLayout. Se crea un panel inferior con el campo de texto y el botón,
     * mientras que el centro contiene un JScrollPane para mostrar los mensajes.
     */
    private void setupLayout() {
        JPanel panAbajo = new JPanel();
        panAbajo.setLayout(new BorderLayout());
        
        panAbajo.add(new JLabel("  Ingrese mensage a enviar:"), BorderLayout.NORTH);
        panAbajo.add(txtMensage, BorderLayout.CENTER);
        panAbajo.add(butEnviar, BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(new JScrollPane(panMostrar), BorderLayout.CENTER);
        add(panAbajo, BorderLayout.SOUTH);

        txtMensage.requestFocus(); // Coloca el cursor en el campo de texto para que el usuario pueda escribir directamente
    }

    /**
     * Establece el nombre del usuario destinatario (amigo) para el chat privado.
     * También actualiza el título de la ventana para mostrar el nombre del amigo.
     * 
     * @param ami Nombre del amigo destinatario.
     */
    public void setAmigo(String ami) {
        this.amigo = ami;
        setTitle(ami);
    }

    /**
     * Devuelve el nombre del usuario destinatario del chat privado.
     * 
     * @return El nombre del amigo destinatario.
     */
    public String getAmigo() {
        return amigo;
    }

    /**
     * Muestra un mensaje en el área de texto principal del chat privado.
     * El mensaje se agrega en una nueva línea para mantener la lectura clara.
     * 
     * @param msg Mensaje a mostrar en la ventana.
     */
    public void mostrarMsg(String msg) {
        panMostrar.append(msg + "\n");
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
     * Limpia el campo de texto donde se escribe el mensaje, dejándolo vacío.
     * Usualmente se llama después de enviar un mensaje para preparar el campo para uno nuevo.
     */
    public void limpiarTextoMensaje() {
        txtMensage.setText("");
    }
    
}
