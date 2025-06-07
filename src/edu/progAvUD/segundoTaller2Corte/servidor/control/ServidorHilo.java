/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progAvUD.segundoTaller2Corte.servidor.control;

import edu.progAvUD.segundoTaller2Corte.servidor.modelo.Servidor;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Andres Felipe
 */
public class ServidorHilo extends Thread {

    private Servidor servidor;
    private ControlServidor controlServidor;

    public ServidorHilo(Socket socketCliente1, Socket socketCliente2, ControlServidor controlServidor) {
        String nombreUsuario = "";
        this.servidor = new Servidor(socketCliente1, socketCliente2, nombreUsuario);
        this.controlServidor = controlServidor;
    }

    @Override
    public void run() {
        controlServidor.mostrarMensajeConsolaServidor(".::Esperando Mensajes :");

        try {
            DataInputStream entrada = new DataInputStream(this.servidor.getServidorCliente1().getInputStream());
            this.servidor.setServidorInformacionEntrada1(entrada);
            DataOutputStream salida1 = new DataOutputStream(this.servidor.getServidorCliente1().getOutputStream());
            this.servidor.setServidorInformacionSalida1(salida1);
            DataOutputStream salida2 = new DataOutputStream(this.servidor.getServidorCliente2().getOutputStream());
            this.servidor.setServidorInformacionSalida2(salida2);
            servidor.setNombreUsuario(entrada.readUTF());
            enviaUserActivos();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int opcion = 0;
        int numUsers = 0;
        String amigo = "";
        String mencli = "";

        while (true) {
            try {
                opcion = this.servidor.getServidorInformacionEntrada1().readInt();
                switch (opcion) {
                    case 1:// envio de mensage a todos
                        mencli = this.servidor.getServidorInformacionEntrada1().readUTF();
                        controlServidor.mostrarMensajeConsolaServidor("mensaje recibido " + mencli);
                        enviaMsg(mencli);
                        break;
                    case 2:// envio de lista de activos
                        numUsers = ControlServidor.getClientesActivos().size();
                        this.servidor.getServidorInformacionSalida1().writeInt(numUsers);
                        for (int i = 0; i < numUsers; i++) {
                            this.servidor.getServidorInformacionSalida1().writeUTF(ControlServidor.getClientesActivos().get(i).getServidor().getNombreUsuario());
                        }
                        break;
                    case 3: // envia mensage a uno solo
                        amigo = this.servidor.getServidorInformacionEntrada1().readUTF();// captura nombre de amigo
                        mencli = this.servidor.getServidorInformacionEntrada1().readUTF();// mensage enviado
                        enviaMsg(amigo, mencli);
                        break;
                }
            } catch (IOException e) {
                controlServidor.mostrarMensajeConsolaServidor("El cliente termino la conexion");
                break;
            }
        }

        controlServidor.mostrarMensajeConsolaServidor("Se removio un usuario");
        ControlServidor.getClientesActivos().removeElement(this);
        try {
            controlServidor.mostrarMensajeConsolaServidor("Se desconecto un usuario");
            this.servidor.getServidorCliente1().close();
        } catch (Exception et) {
            controlServidor.mostrarMensajeConsolaServidor("no se puede cerrar el socket");
        }

    }

    public void enviaUserActivos() {
        ServidorHilo user = null;
        for (int i = 0; i < ControlServidor.getClientesActivos().size(); i++) {
            try {
                user = ControlServidor.getClientesActivos().get(i);
                if (user == this) {
                    continue;// ya se lo envie
                }
                user.getServidor().getServidorInformacionSalida2().writeInt(2);// opcion de agregar
                user.getServidor().getServidorInformacionSalida2().writeUTF(this.servidor.getNombreUsuario());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void enviaMsg(String mencli2) {
        ServidorHilo user = null;
        for (int i = 0; i < ControlServidor.getClientesActivos().size(); i++) {
            controlServidor.mostrarMensajeConsolaServidor("MENSAJE DEVUELTO:" + mencli2);
            try {
                user = ControlServidor.getClientesActivos().get(i);
                user.getServidor().getServidorInformacionSalida2().writeInt(1);// opcion de mensage
                user.getServidor().getServidorInformacionSalida2().writeUTF("" + this.servidor.getNombreUsuario() + " >" + mencli2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void enviaMsg(String amigo, String mencli) {
        ServidorHilo user = null;
        for (int i = 0; i < ControlServidor.getClientesActivos().size(); i++) {
            try {
                user = ControlServidor.getClientesActivos().get(i);
                if (user.getServidor().getNombreUsuario().equals(amigo)) {
                    user.getServidor().getServidorInformacionSalida2().writeInt(3);// opcion de mensage amigo
                    user.getServidor().getServidorInformacionSalida2().writeUTF(this.servidor.getNombreUsuario());
                    user.getServidor().getServidorInformacionSalida2().writeUTF("" + this.servidor.getNombreUsuario() + ">" + mencli);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Servidor getServidor() {
        return servidor;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

}
