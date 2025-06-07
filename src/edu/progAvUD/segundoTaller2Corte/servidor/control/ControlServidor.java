/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progAvUD.segundoTaller2Corte.servidor.control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Andres Felipe
 */
public class ControlServidor {

    private ControlPrincipal controlPrincipal;

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
                
            }
        } catch (IOException e) {
            controlPrincipal.mostrarMensajeConsolaServidor("error :" + e);
        }
    }

}
