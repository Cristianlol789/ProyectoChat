package edu.progAvUD.segundoTaller2Corte.cliente.control;

import java.io.*;

/**
 * Clase ThreadCliente
 *
 * Esta clase extiende de Thread y representa un hilo de ejecución
 * dedicado a la recepción de mensajes desde el servidor por parte del cliente.
 * Su propósito principal es escuchar continuamente mensajes entrantes,
 * interpretarlos según un protocolo de comunicación basado en códigos enteros,
 * y notificar al controlador del cliente para que actualice la interfaz o realice acciones.
 * 
 * Autor: User
 */
public class ThreadCliente extends Thread {

    // Flujo de entrada desde el servidor, por donde se recibirán los mensajes
    private DataInputStream entrada;

    // Referencia al controlador principal del cliente, usado para delegar acciones
    private ControlCliente controlCliente;

    /**
     * Constructor del hilo cliente
     *
     * @param entrada Flujo de entrada para recibir datos desde el servidor
     * @param controlCliente Referencia al controlador cliente para comunicación y manejo de mensajes
     */
    public ThreadCliente(DataInputStream entrada, ControlCliente controlCliente) {
        this.entrada = entrada;
        this.controlCliente = controlCliente;
    }

    /**
     * Método run()
     * 
     * Ciclo principal del hilo. Escucha de forma continua al servidor,
     * esperando un entero que indica el tipo de mensaje, seguido de la
     * información específica según el caso.
     */
    @Override
    public void run() {
        String mensaje = "";
        String amigo = "";
        int opcion = 0;

        while (true) {
            try {
                // Leer el código de operación enviado por el servidor
                opcion = entrada.readInt();

                switch (opcion) {
                    case 1: // Mensaje público
                        mensaje = entrada.readUTF();
                        controlCliente.mostarMensaje(mensaje);
                        break;

                    case 2: // Usuario nuevo se ha conectado
                        mensaje = entrada.readUTF();
                        controlCliente.agregarUsuario(mensaje);
                        // Actualizar la lista completa de usuarios
                        controlCliente.actualizarListaUsuarios();
                        break;

                    case 3: // Mensaje privado recibido
                        amigo = entrada.readUTF();
                        mensaje = entrada.readUTF();
                        controlCliente.mostrarMensajePrivado(amigo, mensaje);
                        break;

                    case 4: // Usuario baneado
                        mensaje = entrada.readUTF();
                        controlCliente.mostrarMensajeError("BANEADO: " + mensaje);
                        controlCliente.cerrarConexiones(); // Cierra conexiones activas
                        System.exit(0); // Finaliza la aplicación
                        break;

                    case 5: // Usuario desconectado
                        mensaje = entrada.readUTF();
                        controlCliente.mostarMensaje("Sistema: " + mensaje);
                        controlCliente.actualizarListaUsuarios(); // Refrescar lista
                        break;

                    case 6: // Advertencia por comportamiento inapropiado
                        mensaje = entrada.readUTF();
                        controlCliente.mostrarMensajeError("ADVERTENCIA: " + mensaje);
                        break;

                    case 7: // Actualización completa de la lista de usuarios activos
                        int numUsuarios = entrada.readInt();
                        java.util.Vector<String> usuariosActualizados = new java.util.Vector<>();

                        for (int i = 0; i < numUsuarios; i++) {
                            usuariosActualizados.add(entrada.readUTF());
                        }

                        controlCliente.actualizarListaCompletaUsuarios(usuariosActualizados);
                        break;
                }

            } catch (IOException e) {
                // Manejo de error si ocurre una excepción de entrada/salida
                controlCliente.mostrarMensajeError("Error en la comunicación con el servidor");
                System.exit(0); // Finaliza la aplicación
                break;
            }
        }

        // Mensaje mostrado si se rompe el bucle (generalmente por desconexión)
        controlCliente.mostrarMensajeError("Se desconectó el servidor");
    }
}