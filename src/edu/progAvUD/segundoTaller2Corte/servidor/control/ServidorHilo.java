package edu.progAvUD.segundoTaller2Corte.servidor.control;

import edu.progAvUD.segundoTaller2Corte.servidor.modelo.Servidor;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Hilo que gestiona la comunicación entre un servidor y un cliente.
 * 
 * Este hilo recibe mensajes del cliente, los interpreta y realiza acciones como:
 * enviar mensajes a todos los clientes, a un cliente específico o actualizar la lista de usuarios activos.
 * 
 * Se apoya en la clase {@code ControlServidor} para gestionar la lista de clientes activos
 * y actualizar la interfaz del servidor.
 * 
 * @author Andres Felipe
 */
public class ServidorHilo extends Thread {

    /** Objeto que representa la conexión con el cliente */
    private Servidor servidor;

    /** Controlador principal del servidor para acceder a la consola y a la lista de usuarios */
    private ControlServidor controlServidor;

    /**
     * Constructor del hilo servidor que inicia la conexión con los clientes.
     *
     * @param socketCliente1 Socket de comunicación para entrada/salida del cliente.
     * @param socketCliente2 Socket adicional para enviar mensajes desde el servidor.
     * @param controlServidor Referencia al controlador general del servidor.
     */
    public ServidorHilo(Socket socketCliente1, Socket socketCliente2, ControlServidor controlServidor) {
        String nombreUsuario = "";
        this.servidor = new Servidor(socketCliente1, socketCliente2, nombreUsuario);
        this.controlServidor = controlServidor;
    }

    /**
     * Método que se ejecuta cuando se inicia el hilo.
     * Establece los flujos de entrada/salida y maneja el ciclo de escucha del cliente.
     */
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

        int opcion;
        String amigo;
        String mencli;

        while (true) {
            try {
                opcion = this.servidor.getServidorInformacionEntrada1().readInt();

                switch (opcion) {
                    case 1: // Enviar mensaje a todos los clientes
                        mencli = this.servidor.getServidorInformacionEntrada1().readUTF();
                        controlServidor.mostrarMensajeConsolaServidor("Mensaje recibido: " + mencli);
                        enviaMsg(mencli);
                        break;
                    case 2: // Enviar lista de usuarios activos
                        int numUsers = ControlServidor.getClientesActivos().size();
                        this.servidor.getServidorInformacionSalida1().writeInt(numUsers);
                        for (int i = 0; i < numUsers; i++) {
                            this.servidor.getServidorInformacionSalida1().writeUTF(
                                ControlServidor.getClientesActivos().get(i).getServidor().getNombreUsuario()
                            );
                        }
                        break;
                    case 3: // Enviar mensaje privado a un amigo
                        amigo = this.servidor.getServidorInformacionEntrada1().readUTF();
                        mencli = this.servidor.getServidorInformacionEntrada1().readUTF();
                        enviaMsg(amigo, mencli);
                        break;
                }
            } catch (IOException e) {
                controlServidor.mostrarMensajeConsolaServidor("El cliente terminó la conexión");
                break;
            }
        }

        controlServidor.mostrarMensajeConsolaServidor("Se removió un usuario");
        ControlServidor.getClientesActivos().removeElement(this);
        try {
            controlServidor.mostrarMensajeConsolaServidor("Se desconectó un usuario");
            this.servidor.getServidorCliente1().close();
        } catch (Exception et) {
            controlServidor.mostrarMensajeConsolaServidor("No se puede cerrar el socket");
        }
    }

    /**
     * Envía el nombre del nuevo usuario a todos los usuarios activos, 
     * para que lo agreguen a sus listas.
     */
    public void enviaUserActivos() {
        ServidorHilo user;
        for (int i = 0; i < ControlServidor.getClientesActivos().size(); i++) {
            try {
                user = ControlServidor.getClientesActivos().get(i);
                if (user == this) continue; // No se envía a sí mismo
                user.getServidor().getServidorInformacionSalida2().writeInt(2);
                user.getServidor().getServidorInformacionSalida2().writeUTF(this.servidor.getNombreUsuario());
            } catch (IOException e) {
                controlServidor.mostrarMensajeConsolaServidor("No se pudo validar la salida del servidor");
            }
        }
    }

    /**
     * Envía un mensaje a todos los usuarios activos.
     *
     * @param mencli2 Mensaje que se desea enviar.
     */
    public void enviaMsg(String mencli2) {
        ServidorHilo user;
        for (int i = 0; i < ControlServidor.getClientesActivos().size(); i++) {
            controlServidor.mostrarMensajeConsolaServidor("MENSAJE DEVUELTO: " + mencli2);
            try {
                user = ControlServidor.getClientesActivos().get(i);
                user.getServidor().getServidorInformacionSalida2().writeInt(1);
                user.getServidor().getServidorInformacionSalida2().writeUTF(this.servidor.getNombreUsuario() + " > " + mencli2);
            } catch (IOException e) {
                controlServidor.mostrarMensajeConsolaServidor("No se pudo enviar el mensaje");
            }
        }
    }

    /**
     * Envía un mensaje privado a un usuario específico.
     *
     * @param amigo Nombre del usuario destinatario.
     * @param mencli Mensaje que se desea enviar.
     */
    private void enviaMsg(String amigo, String mencli) {
        ServidorHilo user;
        for (int i = 0; i < ControlServidor.getClientesActivos().size(); i++) {
            try {
                user = ControlServidor.getClientesActivos().get(i);
                if (user.getServidor().getNombreUsuario().equals(amigo)) {
                    user.getServidor().getServidorInformacionSalida2().writeInt(3);
                    user.getServidor().getServidorInformacionSalida2().writeUTF(this.servidor.getNombreUsuario());
                    user.getServidor().getServidorInformacionSalida2().writeUTF(this.servidor.getNombreUsuario() + " > " + mencli);
                }
            } catch (IOException e) {
                controlServidor.mostrarMensajeConsolaServidor("No se pudo enviar el mensaje");
            }
        }
    }

    /**
     * Obtiene el objeto {@link Servidor} asociado a este hilo.
     *
     * @return Objeto servidor con los sockets y flujos de comunicación.
     */
    public Servidor getServidor() {
        return servidor;
    }

    /**
     * Asigna un nuevo objeto {@link Servidor} a este hilo.
     *
     * @param servidor Objeto servidor con nueva configuración.
     */
    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }
}