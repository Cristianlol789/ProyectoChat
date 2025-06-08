/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.io.*;

public class ThreadCliente extends Thread {

    private DataInputStream entrada;
    private ControlGrafico controlGrafico;

    public ThreadCliente(DataInputStream entrada, ControlGrafico controlGrafico) {
        this.entrada = entrada;
        this.controlGrafico = controlGrafico;
    }

    @Override
    public void run() {
        String mensaje = "";
        String amigo = "";
        int opcion = 0;

        while (true) {
            try {
                opcion = entrada.readInt();
                switch (opcion) {
                    case 1: // mensaje enviado
                        mensaje = entrada.readUTF();
                        System.out.println("ECO del servidor:" + mensaje);
                        controlGrafico.mostrarMensaje(mensaje);
                        break;
                    case 2: // se agrega usuario
                        mensaje = entrada.readUTF();
                        controlGrafico.agregarUsuario(mensaje);
                        break;
                    case 3: // mensaje de amigo
                        amigo = entrada.readUTF();
                        mensaje = entrada.readUTF();
                        controlGrafico.mostrarMensajePrivado(amigo, mensaje);
                        System.out.println("ECO del servidor:" + mensaje);
                        break;
                }
            } catch (IOException e) {
                System.out.println("Error en la comunicación. Información para el usuario");
                break;
            }
        }
        System.out.println("Se desconectó el servidor");
    }
}
