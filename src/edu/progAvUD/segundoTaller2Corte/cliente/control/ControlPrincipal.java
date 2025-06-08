package edu.progAvUD.segundoTaller2Corte.cliente.control;

import java.io.IOException;
import java.util.Vector;

/**
 * Clase ControlPrincipal
 * 
 * Esta clase es el controlador principal de la aplicación cliente en un sistema de chat.
 * Tiene como función coordinar la interacción entre la interfaz gráfica del usuario
 * (a través de la clase ControlGrafico) y la lógica de comunicación con el servidor
 * (a través de la clase ControlCliente).
 * 
 * Se encarga de:
 * - Inicializar los componentes principales de la aplicación cliente.
 * - Solicitar al usuario información inicial como la IP del servidor.
 * - Coordinar el flujo de mensajes entre la vista y el modelo de red.
 * - Manejar errores de inicialización y mostrar mensajes al usuario.
 * 
 * Este controlador es el punto de partida de la aplicación cliente.
 * 
 * Autor: Cristianlol789
 */
public class ControlPrincipal {

    // Controlador de la interfaz gráfica del cliente
    private ControlGrafico controlGrafico;

    // Controlador de la lógica de red del cliente
    private ControlCliente controlCliente;

    /**
     * Constructor de la clase ControlPrincipal
     * Llama al método de inicialización de la aplicación.
     */
    public ControlPrincipal(){
        inicializar(); // Inicia el proceso de configuración y conexión
    }

    /**
     * Método encargado de inicializar la aplicación cliente:
     * - Crea los controladores de GUI y red.
     * - Solicita la IP del servidor al usuario.
     * - Establece la conexión con el servidor.
     * - Muestra errores si ocurren durante la inicialización.
     */
    private void inicializar() {
        try {
            // Crea las instancias de los controladores necesarios
            controlGrafico = new ControlGrafico(this);
            controlCliente = new ControlCliente(this);

            // Solicita la IP del servidor al usuario
            String ipServer = controlGrafico.pedirIpServer();
            if (ipServer == null || ipServer.trim().isEmpty()) {
                System.exit(0); // Termina si el usuario no introduce una IP válida
            }

            // Inicializa el cliente y le asigna la IP proporcionada
            controlCliente.crearCliente();
            controlCliente.actualizarIP(ipServer);

            // Establece la conexión con el servidor
            controlCliente.conectar();
        } catch (IOException e) {
            // Muestra un mensaje de error si falla la inicialización
            controlGrafico.mostrarMensajeError("Error al inicializar la aplicación: " + e.getMessage());
            System.exit(1); // Termina la aplicación con error
        }
    }

    /**
     * Establece el nombre de usuario en la interfaz gráfica
     * @param nomCliente Nombre del usuario que se mostrará en la GUI
     */
    public void cambiarNombreUsuario(String nomCliente) {
        controlGrafico.setNombreUsuario(nomCliente);
    }

    /**
     * Actualiza la lista completa de usuarios activos en la GUI
     * @param usuarios Vector con los nombres de usuarios conectados
     */
    public void actualizarUsuariosActivos(Vector<String> usuarios) {
        controlGrafico.actualizarUsuariosActivos(usuarios);
    }

    /**
     * Muestra un mensaje general (público) en el área de chat principal
     * @param mensaje Texto del mensaje a mostrar
     */
    public void mostarMensaje(String mensaje) {
        controlGrafico.mostrarMensaje(mensaje);
    }

    /**
     * Agrega un nuevo usuario a la lista de usuarios activos
     * @param mensaje Nombre del nuevo usuario conectado
     */
    public void agregarUsuario(String mensaje) {
        controlGrafico.agregarUsuario(mensaje);
    }

    /**
     * Muestra un mensaje privado en la ventana de conversación privada con otro usuario
     * @param amigo Nombre del usuario con el que se está chateando
     * @param mensaje Mensaje recibido o enviado
     */
    public void mostrarMensajePrivado(String amigo, String mensaje) {
        controlGrafico.mostrarMensajePrivado(amigo, mensaje);
    }

    /**
     * Solicita al usuario su nombre de cliente mediante la interfaz gráfica
     * @return Nombre del cliente ingresado por el usuario
     */
    public String pedirNombreCliente() {
        return controlGrafico.pedirNombreCliente();
    }
    
    /**
     * Muestra un mensaje de error en la interfaz
     * @param mensaje Texto del mensaje de error
     */
    public void mostrarMensajeError(String mensaje){
        controlGrafico.mostrarMensajeError(mensaje);
    }
    
    /**
     * Retorna el mensaje a mostrar con el formato: [nombreUsuario]> mensaje
     * @param mensaje Contenido del mensaje a enviar
     * @return Mensaje formateado con el nombre del usuario
     */
    public String obtenerNombreCliente(String mensaje){
        return controlCliente.getNombreCliente() + ">" + mensaje;
    }
    
    /**
     * Envía un mensaje privado a un usuario específico
     * @param amigo Nombre del usuario destinatario
     * @param mensaje Contenido del mensaje privado
     */
    public void enviarMensajePrivado(String amigo, String mensaje){
        controlCliente.enviarMensajePrivado(amigo, mensaje);
    }
    
    /**
     * Envía un mensaje público a todos los usuarios conectados
     * @param mensaje Contenido del mensaje público
     */
    public void enviarMensaje(String mensaje){
        controlCliente.enviarMensaje(mensaje);
    }
}