/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progAvUD.segundoTaller2Corte.servidor.control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 *
 * @author Andres Felipe
 */
public class ControlServidor {

    private ControlPrincipal controlPrincipal;
    public static Vector<ServidorHilo> clientesActivos = new Vector();
    
    public ControlServidor(ControlPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
    }

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
                controlPrincipal.mostrarMensajeConsolaServidor("cliente agregado:" + usuario);
                usuario.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            //controlPrincipal.mostrarMensajeConsolaServidor("error :" + e);
        }
    }

    public void mostrarMensajeConsolaServidor(String mensaje){
        controlPrincipal.mostrarMensajeConsolaServidor(mensaje);
    }

    public static Vector<ServidorHilo> getClientesActivos() {
        return clientesActivos;
    }

    public static void setClientesActivos(Vector<ServidorHilo> clientesActivos) {
        ControlServidor.clientesActivos = clientesActivos;
    }
    
    
}
