package edu.progAvUD.segundoTaller2Corte.cliente.control;

import edu.progAvUD.segundoTaller2Corte.cliente.modelo.Cliente;
import java.io.*;
import java.net.*;
import java.util.Vector;

/**
 * Esta clase se encarga de manejar el cliente y de manejar los hilos correspondientes a la conexion con el servidor
 * @author Cristianlol789
 */
public class ControlCliente {

    // Atributos principales: instancia del cliente, del controlador principal y del hilo de escucha
    private Cliente cliente;
    private ControlPrincipal controlPrincipal;
    private ThreadCliente threadCliente;

    /**
     * Constructor que recibe una instancia del controlador principal de la interfaz
     * @param controlPrincipal
     */
    public ControlCliente(ControlPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
    }

    /**
     * Método que establece la conexión con el servidor y configura los flujos de entrada y salida
     * @throws IOException
     */
    public void conectar() throws IOException {
        try {
            // Crear dos sockets: uno para enviar/recibir mensajes, otro para escuchar en segundo plano
            Socket comunication = new Socket(Cliente.getIP_SERVER(), 8081);
            Socket comunication2 = new Socket(Cliente.getIP_SERVER(), 8082);

            // Asignar sockets y flujos al cliente
            cliente.setComunication(comunication);
            cliente.setComunication2(comunication2);
            cliente.setEntrada(new DataInputStream(comunication.getInputStream()));
            cliente.setSalida(new DataOutputStream(comunication.getOutputStream()));
            cliente.setEntrada2(new DataInputStream(comunication2.getInputStream()));

            // Solicitar al usuario un nombre
            String nomCliente = controlPrincipal.pedirNombreCliente();
            if (nomCliente == null || nomCliente.trim().isEmpty()) {
                // Si no se proporciona nombre, se cierran las conexiones y se termina el programa
                cerrarConexiones();
                System.exit(0);
            }

            // Configurar el nombre en el cliente y notificar al servidor
            cliente.setNombreCliente(nomCliente);
            controlPrincipal.cambiarNombreUsuario(nomCliente);
            cliente.getSalida().writeUTF(nomCliente);

            // Iniciar el hilo de recepción de mensajes
            threadCliente = new ThreadCliente(cliente.getEntrada2(), this);
            threadCliente.start();

            // Solicitar la lista inicial de usuarios activos al servidor
            Vector<String> usuarios = pedirUsuarios();
            controlPrincipal.actualizarUsuariosActivos(usuarios);

        } catch (IOException e) {
            // Mostrar error si el servidor no está activo
            controlPrincipal.mostrarMensajeError("\tEl servidor no esta levantado \n \t=============================");
        }
    }

    /**
     * Método que solicita al servidor la lista de usuarios conectados
     * @return Vector con los nombres de los usuarios activos
     */
    public Vector<String> pedirUsuarios() {
        Vector<String> users = new Vector<>();
        try {
            // Enviar solicitud tipo 2 para obtener usuarios
            cliente.getSalida().writeInt(2);
            int numUsers = cliente.getEntrada().readInt(); // Leer cantidad de usuarios
            for (int i = 0; i < numUsers; i++) {
                users.add(cliente.getEntrada().readUTF()); // Agregar cada nombre
            }
        } catch (IOException ex) {
            // Error en la obtención de la lista
            controlPrincipal.mostrarMensajeError("Hay un problema con los listados");
        }
        return users;
    }

    /**
     * Método para enviar un mensaje público al servidor
     * @param mensaje mensaje a enviar
     */
    public void enviarMensaje(String mensaje) {
        try {
            cliente.getSalida().writeInt(1); // Código para mensaje público
            cliente.getSalida().writeUTF(mensaje);
        } catch (IOException e) {
            controlPrincipal.mostrarMensajeError("error...." + e);
        }
    }

    /**
     * Método para enviar un mensaje privado a un usuario específico
     * @param amigo nombre del destinatario
     * @param mensaje mensaje a enviar
     */
    public void enviarMensajePrivado(String amigo, String mensaje) {
        try {
            cliente.getSalida().writeInt(3); // Código para mensaje privado
            cliente.getSalida().writeUTF(amigo); // Nombre del destinatario
            cliente.getSalida().writeUTF(mensaje);
        } catch (IOException e) {
            controlPrincipal.mostrarMensajeError("error...." + e);
        }
    }

    /**
     * Método para cerrar todas las conexiones y flujos abiertos del cliente
     */
    public void cerrarConexiones() {
        try {
            if (cliente.getEntrada() != null) {
                cliente.getEntrada().close();
            }
            if (cliente.getSalida() != null) {
                cliente.getSalida().close();
            }
            if (cliente.getEntrada2() != null) {
                cliente.getEntrada2().close();
            }
            if (cliente.getComunication() != null) {
                cliente.getComunication().close();
            }
            if (cliente.getComunication2() != null) {
                cliente.getComunication2().close();
            }
        } catch (IOException e) {
            controlPrincipal.mostrarMensajeError("Error cerrando conexiones: " + e.getMessage());
        }
    }

    /**
     * Solicita al servidor una actualización de la lista de usuarios activos
     */
    public void actualizarListaUsuarios() {
        try {
            cliente.getSalida().writeInt(2); // Solicitar lista de usuarios
        } catch (IOException e) {
            controlPrincipal.mostrarMensajeError("Error solicitando actualización de usuarios: " + e.getMessage());
        }
    }

    /**
     * Actualiza completamente la lista de usuarios en la interfaz
     * @param usuarios lista nueva de usuarios activos
     */
    public void actualizarListaCompletaUsuarios(java.util.Vector<String> usuarios) {
        controlPrincipal.actualizarUsuariosActivos(usuarios);
    }

    /**
     * Método para mostrar un mensaje recibido en la interfaz
     * @param mensaje mensaje recibido
     */
    public void mostarMensaje(String mensaje) {
        controlPrincipal.mostarMensaje(mensaje);
    }

    /**
     * Método para agregar un nuevo usuario a la lista mostrada en la interfaz
     * @param mensaje nombre del usuario a agregar
     */
    public void agregarUsuario(String mensaje) {
        controlPrincipal.agregarUsuario(mensaje);
    }

    /**
     * Método para mostrar un mensaje privado en la interfaz
     * @param amigo remitente del mensaje
     * @param mensaje contenido del mensaje
     */
    public void mostrarMensajePrivado(String amigo, String mensaje) {
        controlPrincipal.mostrarMensajePrivado(amigo, mensaje);
    }

    /**
     * Muestra un mensaje de error en la interfaz
     * @param mensaje contenido del error
     */
    public void mostrarMensajeError(String mensaje) {
        controlPrincipal.mostrarMensajeError(mensaje);
    }

    /**
     * Retorna el nombre del cliente actual
     * @return nombre del cliente
     */
    public String getNombreCliente() {
        return cliente.getNombreCliente();
    }

    /**
     * Crea una nueva instancia del objeto Cliente
     */
    public void crearCliente() {
        cliente = new Cliente();
    }

    /**
     * Actualiza la dirección IP del servidor en el objeto cliente
     * @param ipServer nueva IP del servidor
     */
    public void actualizarIP(String ipServer) {
        cliente.setIP_SERVER(ipServer);
    }
}
