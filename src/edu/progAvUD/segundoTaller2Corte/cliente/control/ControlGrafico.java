package edu.progAvUD.segundoTaller2Corte.cliente.control;

import edu.progAvUD.segundoTaller2Corte.cliente.vista.DialogAyuda;
import edu.progAvUD.segundoTaller2Corte.cliente.vista.DialogChatPrivado;
import edu.progAvUD.segundoTaller2Corte.cliente.vista.PanelChatCliente;
import edu.progAvUD.segundoTaller2Corte.cliente.vista.VentanaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;

/**
 * Clase que maneja los eventos gráficos del cliente, incluyendo interacciones
 * con botones, ventanas, menús, y control de diálogos.
 * 
 * @author Cristianlol789
 */
public class ControlGrafico implements ActionListener, WindowListener {

    private ControlPrincipal controlPrincipal;
    private VentanaPrincipal ventanaPrincipal;
    private Vector<String> usuariosActivos;

    /**
     * Constructor que inicializa el controlador gráfico y las ventanas.
     * @param controlPrincipal Referencia al controlador principal del cliente.
     */
    public ControlGrafico(ControlPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
        this.usuariosActivos = new Vector<>();
        inicializarVentanas();
    }

    /**
     * Método que crea e inicializa las ventanas principales y agrega los listeners necesarios.
     */
    private void inicializarVentanas() {
        ventanaPrincipal = new VentanaPrincipal();
        ventanaPrincipal.panelChatCliente = new PanelChatCliente();
        ventanaPrincipal.dialogChatPrivado = new DialogChatPrivado();
        ventanaPrincipal.mostrarPanel(ventanaPrincipal.panelChatCliente);
        ventanaPrincipal.setVisible(true);
        ventanaPrincipal.dialogChatPrivado.addWindowListener(this);
        creacionActionListenerPanelChatCliente();
        creacionActionListenerDialogChatPrivado();
    }

    /**
     * Agrega listeners a los ítems del menú (ayuda y créditos).
     */
    public void creacionActionListenerMenu() {
        ventanaPrincipal.jMenuItemAyuda.addActionListener(this);
        ventanaPrincipal.jMenuItemCreditos.addActionListener(this);
    }

    /**
     * Agrega listeners a los botones y campos de texto del panel de chat principal.
     */
    public void creacionActionListenerPanelChatCliente() {
        ventanaPrincipal.panelChatCliente.butEnviar.addActionListener(this);
        ventanaPrincipal.panelChatCliente.txtMensage.addActionListener(this);
        ventanaPrincipal.panelChatCliente.butPrivado.addActionListener(this);
    }

    /**
     * Agrega listeners a los elementos del diálogo de chat privado.
     */
    public void creacionActionListenerDialogChatPrivado() {
        ventanaPrincipal.dialogChatPrivado.butEnviar.addActionListener(this);
        ventanaPrincipal.dialogChatPrivado.txtMensage.addActionListener(this);
    }

    /**
     * Actualiza el nombre del usuario en la interfaz gráfica.
     * @param nombre Nombre del usuario.
     */
    public void setNombreUsuario(String nombre) {
        ventanaPrincipal.panelChatCliente.setNombreUser(nombre);
    }

    /**
     * Muestra un mensaje en el área principal de chat.
     * @param mensaje Mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        ventanaPrincipal.panelChatCliente.mostrarMsg(mensaje);
    }

    /**
     * Actualiza la lista de usuarios activos en la interfaz.
     * @param usuarios Vector con los nombres de los usuarios activos.
     */
    public void actualizarUsuariosActivos(Vector<String> usuarios) {
        this.usuariosActivos = usuarios;
        ventanaPrincipal.panelChatCliente.ponerActivos(usuarios);
    }

    /**
     * Agrega un nuevo usuario a la lista de usuarios activos.
     * @param usuario Nombre del nuevo usuario.
     */
    public void agregarUsuario(String usuario) {
        usuariosActivos.add(usuario);
        ventanaPrincipal.panelChatCliente.ponerActivos(usuariosActivos);
    }

    /**
     * Retira un usuario de la lista de usuarios activos.
     * @param usuario Nombre del usuario a eliminar.
     */
    public void retirarUsuario(String usuario) {
        usuariosActivos.remove(usuario);
        ventanaPrincipal.panelChatCliente.ponerActivos(usuariosActivos);
    }

    /**
     * Muestra un mensaje privado en el diálogo correspondiente.
     * @param amigo Usuario con quien se mantiene el chat privado.
     * @param mensaje Mensaje recibido.
     */
    public void mostrarMensajePrivado(String amigo, String mensaje) {
        ventanaPrincipal.dialogChatPrivado.setAmigo(amigo);
        ventanaPrincipal.dialogChatPrivado.mostrarMsg(mensaje);
        ventanaPrincipal.dialogChatPrivado.setVisible(true);
    }

    /**
     * Método que captura todos los eventos de botones, menús y campos de texto.
     * @param evt Evento capturado.
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        Object fuente = evt.getSource();

        if (fuente == ventanaPrincipal.jMenuItemAyuda) {
            mostrarAyuda();
        }
        if (fuente == ventanaPrincipal.jMenuItemCreditos) {
            mostrarAcercaDe();
        }
        if (fuente == ventanaPrincipal.panelChatCliente.butEnviar
                || fuente == ventanaPrincipal.panelChatCliente.txtMensage) {
            enviarMensajePrincipal();
        }
        if(fuente == ventanaPrincipal.panelChatCliente.butPrivado){
            abrirVentanaPrivada();
        }
        if(fuente == ventanaPrincipal.dialogChatPrivado.butEnviar
                || fuente == ventanaPrincipal.dialogChatPrivado.txtMensage){
            enviarMensajePrivado();
        }
    }

    /**
     * Muestra el diálogo de ayuda.
     */
    private void mostrarAyuda() {
        if (ventanaPrincipal.dialogAyuda == null) {
            try {
                ventanaPrincipal.dialogAyuda = new DialogAyuda();
            } catch (Exception ex) {
                ventanaPrincipal.dialogAyuda.errorAyuda(ex);
            }
        }
        ventanaPrincipal.dialogAyuda.setVisible(true);
    }

    /**
     * Muestra un mensaje de créditos o información "Acerca de".
     */
    private void mostrarAcercaDe() {
        ventanaPrincipal.mostrarMensajeExito("Desarrollado por: \n José Valdez \n Javier Vargas");
    }

    /**
     * Envía un mensaje desde el chat principal.
     */
    private void enviarMensajePrincipal() {
        String mensaje = ventanaPrincipal.panelChatCliente.obtenerTextoMensaje();
        if (!mensaje.trim().isEmpty()) {
            controlPrincipal.enviarMensaje(mensaje);
            ventanaPrincipal.panelChatCliente.limpiarTextoMensaje();
        }
    }

    /**
     * Abre el diálogo de chat privado con el usuario seleccionado.
     */
    private void abrirVentanaPrivada() {
        int posicion = ventanaPrincipal.panelChatCliente.obtenerUsuarioSeleccionado();
        if (posicion >= 0 && posicion < usuariosActivos.size()) {
            String amigo = usuariosActivos.get(posicion);
            ventanaPrincipal.dialogChatPrivado.setAmigo(amigo);
            ventanaPrincipal.dialogChatPrivado.setVisible(true);
        }
    }

    /**
     * Envía un mensaje privado al usuario seleccionado.
     */
    private void enviarMensajePrivado() {
        String mensaje = ventanaPrincipal.dialogChatPrivado.obtenerTextoMensaje();
        String amigo = ventanaPrincipal.dialogChatPrivado.getAmigo();

        if (!mensaje.trim().isEmpty() && amigo != null && !amigo.isEmpty()) {
            String mensajeCompleto = controlPrincipal.obtenerNombreCliente(mensaje);
            ventanaPrincipal.dialogChatPrivado.mostrarMsg(mensajeCompleto);
            controlPrincipal.enviarMensajePrivado(amigo, mensaje);
            ventanaPrincipal.dialogChatPrivado.limpiarTextoMensaje();
        }
    }

    /**
     * Muestra un mensaje de error en la interfaz.
     * @param mensaje Mensaje de error.
     */
    public void mostrarMensajeError(String mensaje) {
        ventanaPrincipal.mostrarMensajeError(mensaje);
    }

    /**
     * Muestra un mensaje de éxito en la interfaz.
     * @param mensaje Mensaje a mostrar.
     */
    public void mostrarMensajeExito(String mensaje) {
        ventanaPrincipal.mostrarMensajeExito(mensaje);
    }

    /**
     * Solicita el nombre del cliente a través de la interfaz gráfica.
     * @return Nombre ingresado.
     */
    public String pedirNombreCliente() {
        return ventanaPrincipal.darNombreCliente();
    }

    /**
     * Solicita la IP del servidor desde la interfaz gráfica.
     * @return IP del servidor.
     */
    public String pedirIpServer() {
        return ventanaPrincipal.darIpServidor();
    }

    // Métodos de la interfaz WindowListener

    @Override
    public void windowOpened(WindowEvent e) {
        // No se utiliza
    }

    @Override
    public void windowClosing(WindowEvent e) {
        // Cierra el diálogo de chat privado cuando se intenta cerrar
        ventanaPrincipal.dialogChatPrivado.setVisible(false);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        // No se utiliza
    }

    @Override
    public void windowIconified(WindowEvent e) {
        // No se utiliza
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // No se utiliza
    }

    @Override
    public void windowActivated(WindowEvent e) {
        // No se utiliza
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // No se utiliza
    }
}