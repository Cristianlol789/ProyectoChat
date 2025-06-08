/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

// ControlGrafico.java
import vista.VentCliente;
import vista.VentPrivada;
import vista.VentanaAyuda;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;
import javax.swing.JOptionPane;

public class ControlGrafico implements ActionListener, WindowListener {

    private ControlCliente controlCliente;
    private VentCliente ventanaPrincipal;
    private VentPrivada ventanaPrivada;
    private VentanaAyuda ventanaAyuda;
    private Vector<String> usuariosActivos;

    public ControlGrafico(ControlCliente controlCliente) {
        this.controlCliente = controlCliente;
        this.usuariosActivos = new Vector<>();
        inicializarVentanas();
    }

    private void inicializarVentanas() {
        ventanaPrincipal = new VentCliente(this);
        ventanaPrivada = new VentPrivada(this);
        ventanaPrincipal.setVisible(true);
        ventanaPrivada.addWindowListener(this);
    }

    public void setNombreUsuario(String nombre) {
        ventanaPrincipal.setNombreUser(nombre);
    }

    public void mostrarMensaje(String mensaje) {
        ventanaPrincipal.mostrarMsg(mensaje);
    }

    public void actualizarUsuariosActivos(Vector<String> usuarios) {
        this.usuariosActivos = usuarios;
        ventanaPrincipal.ponerActivos(usuarios);
    }

    public void agregarUsuario(String usuario) {
        usuariosActivos.add(usuario);
        ventanaPrincipal.ponerActivos(usuariosActivos);
    }

    public void retirarUsuario(String usuario) {
        usuariosActivos.remove(usuario);
        ventanaPrincipal.ponerActivos(usuariosActivos);
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
        JOptionPane.showMessageDialog(ventanaPrincipal,
                "José Valdez/Javier Vargas",
                "Desarrollado por",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void enviarMensajePrincipal() {
        String mensaje = ventanaPrincipal.obtenerTextoMensaje();
        if (!mensaje.trim().isEmpty()) {
            controlCliente.enviarMensaje(mensaje);
            ventanaPrincipal.limpiarTextoMensaje();
        }
    }

    private void abrirVentanaPrivada() {
        int posicion = ventanaPrincipal.obtenerUsuarioSeleccionado();
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
            String mensajeCompleto = controlCliente.getNombreCliente() + ">" + mensaje;
            ventanaPrivada.mostrarMsg(mensajeCompleto);
            controlCliente.enviarMensajePrivado(amigo, mensaje);
            ventanaPrivada.limpiarTextoMensaje();
        }
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
