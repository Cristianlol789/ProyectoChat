package edu.progAvUD.segundoTaller2Corte.cliente.control;

import edu.progAvUD.segundoTaller2Corte.cliente.vista.VentCliente;
import edu.progAvUD.segundoTaller2Corte.cliente.vista.VentPrivada;
import edu.progAvUD.segundoTaller2Corte.cliente.vista.VentanaAyuda;
import edu.progAvUD.segundoTaller2Corte.cliente.vista.VentanaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;

public class ControlGrafico implements ActionListener, WindowListener {

    private ControlPrincipal controlPrincipal;
    private VentCliente ventanaCliente;
    private VentPrivada ventanaPrivada;
    private VentanaAyuda ventanaAyuda;
    private VentanaPrincipal ventanaPrincipal;
    private Vector<String> usuariosActivos;

    public ControlGrafico(ControlPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
        this.usuariosActivos = new Vector<>();
        inicializarVentanas();
    }

    private void inicializarVentanas() {
        ventanaCliente = new VentCliente(this);
        ventanaPrivada = new VentPrivada(this);
        ventanaCliente.setVisible(true);
        ventanaPrivada.addWindowListener(this);
        ventanaPrincipal = new VentanaPrincipal(this);
    }

    public void setNombreUsuario(String nombre) {
        ventanaCliente.setNombreUser(nombre);
    }

    public void mostrarMensaje(String mensaje) {
        ventanaCliente.mostrarMsg(mensaje);
    }

    public void actualizarUsuariosActivos(Vector<String> usuarios) {
        this.usuariosActivos = usuarios;
        ventanaCliente.ponerActivos(usuarios);
    }

    public void agregarUsuario(String usuario) {
        usuariosActivos.add(usuario);
        ventanaCliente.ponerActivos(usuariosActivos);
    }

    public void retirarUsuario(String usuario) {
        usuariosActivos.remove(usuario);
        ventanaCliente.ponerActivos(usuariosActivos);
    }

    public void mostrarMensajePrivado(String amigo, String mensaje) {
        ventanaPrivada.setAmigo(amigo);
        ventanaPrivada.mostrarMsg(mensaje);
        ventanaPrivada.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        String comando = evt.getActionCommand();

        if ("help".equals(comando)) {
            mostrarAyuda();
        } else if ("Acerca".equals(comando)) {
            mostrarAcercaDe();
        } else if ("enviar_mensaje".equals(comando)) {
            enviarMensajePrincipal();
        } else if ("mensaje_privado".equals(comando)) {
            abrirVentanaPrivada();
        } else if ("enviar_privado".equals(comando)) {
            enviarMensajePrivado();
        }
    }

    private void mostrarAyuda() {
        if (ventanaAyuda == null) {
            ventanaAyuda = new VentanaAyuda();
        }
        ventanaAyuda.setVisible(true);
    }

    private void mostrarAcercaDe() {
        ventanaPrincipal.mostrarMensajeExito("Desarrollado por: \n José Valdez \n Javier Vargas");
    }

    private void enviarMensajePrincipal() {
        String mensaje = ventanaCliente.obtenerTextoMensaje();
        if (!mensaje.trim().isEmpty()) {
            controlPrincipal.enviarMensaje(mensaje);
            ventanaCliente.limpiarTextoMensaje();
        }
    }

    private void abrirVentanaPrivada() {
        int posicion = ventanaCliente.obtenerUsuarioSeleccionado();
        if (posicion >= 0 && posicion < usuariosActivos.size()) {
            String amigo = usuariosActivos.get(posicion);
            ventanaPrivada.setAmigo(amigo);
            ventanaPrivada.setVisible(true);
        }
    }

    private void enviarMensajePrivado() {
        
        String mensaje = ventanaPrivada.obtenerTextoMensaje();
        String amigo = ventanaPrivada.getAmigo();

        if (!mensaje.trim().isEmpty() && amigo != null && !amigo.isEmpty()) {
            String mensajeCompleto = controlPrincipal.obtenerNombreCliente(mensaje);
            ventanaPrivada.mostrarMsg(mensajeCompleto);
            controlPrincipal.enviarMensajePrivado(amigo, mensaje);
            ventanaPrivada.limpiarTextoMensaje();
        }
    }
    
    public void mostrarMensajeError(String mensaje){
        ventanaPrincipal.mostrarMensajeError(mensaje);
    }
    
    public void mostrarMensajeExito(String mensaje){
        ventanaPrincipal.mostrarMensajeExito(mensaje);
    }
    
    public String pedirNombreCliente(){
        return ventanaPrincipal.darNombreCliente();
    }
    
    public String pedirIpServer(){
        return ventanaPrincipal.darIpServidor();
    }
    
    @Override
    public void windowOpened(WindowEvent e) {
        
    }

    @Override
    public void windowClosing(WindowEvent e) {
        ventanaPrivada.setVisible(false);
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
