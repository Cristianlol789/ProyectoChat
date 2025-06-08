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

public class ControlGrafico implements ActionListener, WindowListener {

    private ControlPrincipal controlPrincipal;
//    private VentCliente ventanaCliente;
//    private VentPrivada ventanaPrivada;
//    private VentanaAyuda ventanaAyuda;
    private VentanaPrincipal ventanaPrincipal;
    private Vector<String> usuariosActivos;

    public ControlGrafico(ControlPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
        this.usuariosActivos = new Vector<>();
        inicializarVentanas();
    }

    private void inicializarVentanas() {
        ventanaPrincipal = new VentanaPrincipal();
        ventanaPrincipal.panelChatCliente = new PanelChatCliente();
        ventanaPrincipal.dialogChatPrivado = new DialogChatPrivado();
        ventanaPrincipal.mostrarPanel(ventanaPrincipal.panelChatCliente);
        ventanaPrincipal.setVisible(true);
//        ventanaCliente = new VentCliente(this);
//        ventanaPrivada = new VentPrivada(this);
//        ventanaCliente.setVisible(true);
        ventanaPrincipal.dialogChatPrivado.addWindowListener(this);
        creacionActionListenerPanelChatCliente();
        creacionActionListenerDialogChatPrivado();
    }

    public void creacionActionListenerMenu() {
        ventanaPrincipal.jMenuItemAyuda.addActionListener(this);
        ventanaPrincipal.jMenuItemCreditos.addActionListener(this);
    }

    public void creacionActionListenerPanelChatCliente() {
        ventanaPrincipal.panelChatCliente.butEnviar.addActionListener(this);
        ventanaPrincipal.panelChatCliente.txtMensage.addActionListener(this);
        ventanaPrincipal.panelChatCliente.butPrivado.addActionListener(this);
    }

    public void creacionActionListenerDialogChatPrivado() {
        ventanaPrincipal.dialogChatPrivado.butEnviar.addActionListener(this);
        ventanaPrincipal.dialogChatPrivado.txtMensage.addActionListener(this);
    }

    public void setNombreUsuario(String nombre) {
        ventanaPrincipal.panelChatCliente.setNombreUser(nombre);
    }

    public void mostrarMensaje(String mensaje) {
        ventanaPrincipal.panelChatCliente.mostrarMsg(mensaje);
    }

    public void actualizarUsuariosActivos(Vector<String> usuarios) {
        this.usuariosActivos = usuarios;
        ventanaPrincipal.panelChatCliente.ponerActivos(usuarios);
    }

    public void agregarUsuario(String usuario) {
        usuariosActivos.add(usuario);
        ventanaPrincipal.panelChatCliente.ponerActivos(usuariosActivos);
    }

    public void retirarUsuario(String usuario) {
        usuariosActivos.remove(usuario);
        ventanaPrincipal.panelChatCliente.ponerActivos(usuariosActivos);
    }

    public void mostrarMensajePrivado(String amigo, String mensaje) {
        ventanaPrincipal.dialogChatPrivado.setAmigo(amigo);
        ventanaPrincipal.dialogChatPrivado.mostrarMsg(mensaje);
        ventanaPrincipal.dialogChatPrivado.setVisible(true);
    }

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

//        String comando = evt.getActionCommand();
//
//        if ("help".equals(comando)) {
//            mostrarAyuda();
//        } else if ("Acerca".equals(comando)) {
//            mostrarAcercaDe();
//        } else if ("enviar_mensaje".equals(comando)) {
//            enviarMensajePrincipal();
//        } else if ("mensaje_privado".equals(comando)) {
//            abrirVentanaPrivada();
//        } else if ("enviar_privado".equals(comando)) {
//            enviarMensajePrivado();
//        }
    }

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

    private void mostrarAcercaDe() {
        ventanaPrincipal.mostrarMensajeExito("Desarrollado por: \n José Valdez \n Javier Vargas");
    }

    private void enviarMensajePrincipal() {
        String mensaje = ventanaPrincipal.panelChatCliente.obtenerTextoMensaje();
        if (!mensaje.trim().isEmpty()) {
            controlPrincipal.enviarMensaje(mensaje);
            ventanaPrincipal.panelChatCliente.limpiarTextoMensaje();
        }
    }

    private void abrirVentanaPrivada() {
        int posicion = ventanaPrincipal.panelChatCliente.obtenerUsuarioSeleccionado();
        if (posicion >= 0 && posicion < usuariosActivos.size()) {
            String amigo = usuariosActivos.get(posicion);
            ventanaPrincipal.dialogChatPrivado.setAmigo(amigo);
            ventanaPrincipal.dialogChatPrivado.setVisible(true);
        }
    }

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

    public void mostrarMensajeError(String mensaje) {
        ventanaPrincipal.mostrarMensajeError(mensaje);
    }

    public void mostrarMensajeExito(String mensaje) {
        ventanaPrincipal.mostrarMensajeExito(mensaje);
    }

    public String pedirNombreCliente() {
        return ventanaPrincipal.darNombreCliente();
    }

    public String pedirIpServer() {
        return ventanaPrincipal.darIpServidor();
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        ventanaPrincipal.dialogChatPrivado.setVisible(false);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
