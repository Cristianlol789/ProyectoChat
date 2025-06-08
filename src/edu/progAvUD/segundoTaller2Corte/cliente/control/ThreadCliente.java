package edu.progAvUD.segundoTaller2Corte.cliente.control;

import java.io.*;

public class ThreadCliente extends Thread {

    private DataInputStream entrada;
    private ControlCliente controlCliente;

    public ThreadCliente(DataInputStream entrada, ControlCliente controlCliente) {
        this.entrada = entrada;
        this.controlCliente = controlCliente;
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
                        controlCliente.mostarMensaje(mensaje);
                        break;
                    case 2: // se agrega usuario
                        mensaje = entrada.readUTF();
                        controlCliente.agregarUsuario(mensaje);
                        break;
                    case 3: // mensaje de amigo
                        amigo = entrada.readUTF();
                        mensaje = entrada.readUTF();
                        controlCliente.mostrarMensajePrivado(amigo, mensaje);
                        break;
                }
            } catch (IOException e) {
                controlCliente.mostrarMensajeError("Error en la comunicación. Información para el usuario");
                break;
            }
        }
        controlCliente.mostrarMensajeError("Se desconectó el servidor");
    }
}
