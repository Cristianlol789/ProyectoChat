package edu.progAvUD.segundoTaller2Corte.servidor.control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 * Clase encargada de gestionar la lógica principal del servidor.
 * 
 * Controla la recepción de conexiones entrantes, el manejo de múltiples clientes 
 * mediante hilos, y el registro de los usuarios activos.
 * 
 * Usa dos sockets (`server1` y `server2`) para establecer la comunicación entre clientes y servidor.
 * Uno puede ser usado para entrada y el otro para salida, dependiendo de la lógica implementada.
 * 
 * Esta clase es invocada desde {@link ControlPrincipal} y es clave en la gestión del servidor de chat.
 * 
 * @author Andres Felipe
 */
public class ControlServidor {

    private ControlPrincipal controlPrincipal;

    /**
     * Lista de clientes actualmente conectados al servidor.
     * Cada cliente se representa mediante un hilo de tipo {@link ServidorHilo}.
     */
    public static Vector<ServidorHilo> clientesActivos = new Vector<>();

    /**
     * Constructor que recibe la instancia del controlador principal.
     *
     * @param controlPrincipal Instancia que representa la ventana principal de control del servidor.
     */
    public ControlServidor(ControlPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
    }

    /**
     * Método que inicia los sockets del servidor y espera por conexiones de clientes.
     * Por cada pareja de conexiones (dos sockets), se crea un hilo para manejar al cliente.
     * Los clientes conectados se agregan al vector de usuarios activos.
     */
    public void runServer() {
        ServerSocket server1 = null;
        ServerSocket server2 = null;
        boolean listening = true;

        try {
            server1 = new ServerSocket(8081);
            server2 = new ServerSocket(8082);
            controlPrincipal.mostrarMensajeConsolaServidor(".::Servidor activo :");

            while (listening) {
                Socket socket1 = null;
                Socket socket2 = null;
                try {
                    controlPrincipal.mostrarMensajeConsolaServidor("Esperando Usuarios");
                    socket1 = server1.accept();
                    socket2 = server2.accept();
                } catch (IOException e) {
                    controlPrincipal.mostrarMensajeConsolaServidor("Accept failed: " + server1 + ", " + e.getMessage());
                    continue;
                }

                ServidorHilo usuario = new ServidorHilo(socket1, socket2, this);
                clientesActivos.add(usuario);
                controlPrincipal.mostrarMensajeConsolaServidor("Cliente agregado: " + usuario);
                usuario.start(); // inicia el hilo que manejará la comunicación con este cliente
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Se puede mostrar mensaje en consola si se desea: controlPrincipal.mostrarMensajeConsolaServidor("error :" + e);
        }
    }

    /**
     * Envía un mensaje de texto a la consola del servidor.
     *
     * @param mensaje Mensaje que se desea mostrar en la interfaz de la consola.
     */
    public void mostrarMensajeConsolaServidor(String mensaje) {
        controlPrincipal.mostrarMensajeConsolaServidor(mensaje);
    }

    /**
     * Obtiene el vector que contiene todos los hilos de clientes activos conectados al servidor.
     *
     * @return Vector de clientes activos.
     */
    public static Vector<ServidorHilo> getClientesActivos() {
        return clientesActivos;
    }

    /**
     * Reemplaza la lista de clientes activos por otra.
     *
     * @param clientesActivos Nuevo vector de clientes activos.
     */
    public static void setClientesActivos(Vector<ServidorHilo> clientesActivos) {
        ControlServidor.clientesActivos = clientesActivos;
    }
}