package edu.progAvUD.segundoTaller2Corte.cliente.control;

import java.io.IOException;
import java.util.Vector;

public class ControlPrincipal {

    private ControlGrafico controlGrafico;
    private ControlCliente controlCliente;

    public ControlPrincipal(){
        inicializar();
    }

    private void inicializar() {
        try {
            // Crea los controles
            controlGrafico = new ControlGrafico(this);
            controlCliente = new ControlCliente(this);

            // Solicitar IP del servidor
            String ipServer = controlGrafico.pedirIpServer();
            if (ipServer == null || ipServer.trim().isEmpty()) {
                System.exit(0);
            }

            // Crear modelo Cliente
            controlCliente.crearCliente();
            controlCliente.actualizarIP(ipServer);

            // Iniciar conexión
            controlCliente.conectar();
        } catch (IOException e) {
            controlGrafico.mostrarMensajeError("Error al inicializar la aplicación: " + e.getMessage());
            System.exit(1);
        }
    }

    public void cambiarNombreUsuario(String nomCliente) {
        controlGrafico.setNombreUsuario(nomCliente);
    }

    public void actualizarUsuariosActivos(Vector<String> usuarios) {
        controlGrafico.actualizarUsuariosActivos(usuarios);
    }

    public void mostarMensaje(String mensaje) {
        controlGrafico.mostrarMensaje(mensaje);
    }

    public void agregarUsuario(String mensaje) {
        controlGrafico.agregarUsuario(mensaje);
    }

    public void mostrarMensajePrivado(String amigo, String mensaje) {
        controlGrafico.mostrarMensajePrivado(amigo, mensaje);
    }

    public String pedirNombreCliente() {
        return controlGrafico.pedirNombreCliente();
    }
    
    public void mostrarMensajeError(String mensaje){
        controlGrafico.mostrarMensajeError(mensaje);
    }
    
    public String obtenerNombreCliente(String mensaje){
        return controlCliente.getNombreCliente() + ">" + mensaje;
    }
    
    public void enviarMensajePrivado(String amigo, String mensaje){
        controlCliente.enviarMensajePrivado(amigo, mensaje);
    }
    
    public void enviarMensaje(String mensaje){
        controlCliente.enviarMensaje(mensaje);
    }
}
