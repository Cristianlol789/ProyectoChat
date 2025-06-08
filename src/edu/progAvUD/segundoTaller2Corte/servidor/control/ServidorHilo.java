package edu.progAvUD.segundoTaller2Corte.servidor.control;

import edu.progAvUD.segundoTaller2Corte.servidor.modelo.Servidor;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Hilo que gestiona la comunicación entre un servidor y un cliente.
 *
 * Este hilo recibe mensajes del cliente, los interpreta y realiza acciones
 * como: enviar mensajes a todos los clientes, a un cliente específico o
 * actualizar la lista de usuarios activos.
 *
 * Se apoya en la clase {@code ControlServidor} para gestionar la lista de
 * clientes activos y actualizar la interfaz del servidor.
 *
 * @author Andres Felipe
 */
public class ServidorHilo extends Thread {

    /**
     * Objeto que representa la conexión con el cliente
     */
    private Servidor servidor;

    /**
     * Controlador principal del servidor para acceder a la consola y a la lista
     * de usuarios
     */
    private ControlServidor controlServidor;

    private static final String[] malasPalabras = {
        "carajo", "mierda", "joder", "coño", "pendejo", "pendeja", "cabron", "cabrona", "hijo de puta", "hija de puta", "puta", "puto", "gilipollas", "imbécil", "imbecil", "malparido", "malparida", "culero", "culera", "estúpido", "estúpida", "mamón", "mamona", "boludo", "boluda", "pelotudo", "pelotuda", "zorra", "perra", "baboso", "babosa", "chinga tu madre", "pinche", "maricón", "maricona", "marica", "verga", "chingada", "chingado", "cojudo", "cojuda", "idiota", "tarado", "tarada", "tonto", "tonta", "mierdoso", "mierdosa", "culiao", "culiá", "culiador", "culiadora", "concha", "forro", "forra", "pajero", "pajera", "ñero", "ñera", "culicagado", "culicagada", "güevón", "güevona", "mamaguevo", "mamagueva", "gonorrea", "gonorreta", "careverga", "carechimba", "malnacido", "malnacida", "come mierda", "tragaleche", "pichón", "pichona", "putona", "putón", "bastardo", "bastarda", "pervertido", "pervertida", "asqueroso", "asquerosa", "cagada", "cagado", "cochina", "cochino", "infeliz", "sucia", "sucio", "machorra", "pirobo", "piroba", "loca", "loco", "petardo", "petarda", "mierdín", "mierdina", "carapicha", "carapicho", "soplapollas", "chupapijas", "sarna", "apestoso", "apestosa", "degenerado", "degenerada", "desgraciado", "desgraciada", "desubicado", "desubicada", "imbesil", "tarúpido", "tarúpida"
    };

    private int advertencias = 0;
    private static final int MAX_ADVERTENCIAS = 3;

    /**
     * Constructor del hilo servidor que inicia la conexión con los clientes.
     *
     * @param socketCliente1 Socket de comunicación para entrada/salida del
     * cliente.
     * @param socketCliente2 Socket adicional para enviar mensajes desde el
     * servidor.
     * @param controlServidor Referencia al controlador general del servidor.
     */
    public ServidorHilo(Socket socketCliente1, Socket socketCliente2, ControlServidor controlServidor) {
        String nombreUsuario = "";
        this.servidor = new Servidor(socketCliente1, socketCliente2, nombreUsuario);
        this.controlServidor = controlServidor;
    }

    /**
     * Método que se ejecuta cuando se inicia el hilo. Establece los flujos de
     * entrada/salida y maneja el ciclo de escucha del cliente.
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
            enviarListaCompletaUsuarios();

        } catch (IOException e) {
            e.printStackTrace();
            return; // Si hay error en la inicialización, terminar el hilo
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
                        controlServidor.mostrarMensajeConsolaServidor("Mensaje recibido de " + servidor.getNombreUsuario() + ": " + mencli);

                        // Censurar mensaje y verificar si el usuario debe ser baneado
                        String mensajeCensurado = censorMessage(mencli);

                        // Si censorMessage retorna null, significa que el usuario fue baneado
                        if (mensajeCensurado == null) {
                            return; // Terminar el hilo
                        }

                        // Enviar mensaje censurado a todos los usuarios
                        enviaMsg(mensajeCensurado);
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
                        controlServidor.mostrarMensajeConsolaServidor("Mensaje privado de " + servidor.getNombreUsuario() + " para " + amigo + ": " + mencli);

                        // Censurar mensaje privado
                        String mensajePrivadoCensurado = censorMessage(mencli);

                        // Si censorMessage retorna null, significa que el usuario fue baneado
                        if (mensajePrivadoCensurado == null) {
                            return; // Terminar el hilo
                        }

                        // Enviar mensaje privado censurado
                        enviaMsg(amigo, mensajePrivadoCensurado);
                        break;
                }
            } catch (IOException e) {
                controlServidor.mostrarMensajeConsolaServidor("El cliente " + servidor.getNombreUsuario() + " terminó la conexión");
                break;
            }
        }

        // AQUÍ ESTÁ LA CORRECCIÓN: Llamar a notificarDesconexion() antes de limpiar
        controlServidor.mostrarMensajeConsolaServidor("Se removió un usuario: " + servidor.getNombreUsuario());

        // Notificar a otros usuarios sobre la desconexión
        notificarDesconexion();

        // Remover de la lista de usuarios activos
        ControlServidor.getClientesActivos().removeElement(this);

        try {
            controlServidor.mostrarMensajeConsolaServidor("Se desconectó un usuario");
            this.servidor.getServidorCliente1().close();
            this.servidor.getServidorCliente2().close(); // También cerrar el segundo socket
        } catch (Exception et) {
            controlServidor.mostrarMensajeConsolaServidor("No se puede cerrar el socket: " + et.getMessage());
        }
    }

    /**
     * Notifica a todos los usuarios activos que alguien se desconectó y envía
     * la lista actualizada de usuarios
     */
    private void notificarDesconexion() {
        for (ServidorHilo user : ControlServidor.getClientesActivos()) {
            if (user != this) {
                try {
                    // Notificar desconexión
                    user.getServidor().getServidorInformacionSalida2().writeInt(5);
                    user.getServidor().getServidorInformacionSalida2().writeUTF(this.servidor.getNombreUsuario() + " se ha desconectado");

                    // Enviar lista actualizada de usuarios (código 7)
                    user.getServidor().getServidorInformacionSalida2().writeInt(7);

                    // Contar usuarios activos (excluyendo el que se va a desconectar)
                    int usuariosActivos = ControlServidor.getClientesActivos().size() - 1;
                    user.getServidor().getServidorInformacionSalida2().writeInt(usuariosActivos);

                    // Enviar nombres de usuarios activos
                    for (ServidorHilo activeUser : ControlServidor.getClientesActivos()) {
                        if (activeUser != this) { // No incluir el usuario que se desconecta
                            user.getServidor().getServidorInformacionSalida2().writeUTF(
                                    activeUser.getServidor().getNombreUsuario()
                            );
                        }
                    }

                } catch (IOException e) {
                    controlServidor.mostrarMensajeConsolaServidor("Error notificando desconexión: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Envía la lista completa de usuarios activos a todos los clientes
     */
    private void enviarListaCompletaUsuarios() {
        for (ServidorHilo user : ControlServidor.getClientesActivos()) {
            try {
                user.getServidor().getServidorInformacionSalida2().writeInt(7); // Código para lista completa
                user.getServidor().getServidorInformacionSalida2().writeInt(ControlServidor.getClientesActivos().size());

                for (ServidorHilo activeUser : ControlServidor.getClientesActivos()) {
                    user.getServidor().getServidorInformacionSalida2().writeUTF(
                            activeUser.getServidor().getNombreUsuario()
                    );
                }
            } catch (IOException e) {
                controlServidor.mostrarMensajeConsolaServidor("Error enviando lista de usuarios: " + e.getMessage());
            }
        }
    }

    /**
     * Envía el nombre del nuevo usuario a todos los usuarios activos, para que
     * lo agreguen a sus listas.
     */
    public void enviaUserActivos() {
        ServidorHilo user;
        for (int i = 0; i < ControlServidor.getClientesActivos().size(); i++) {
            try {
                user = ControlServidor.getClientesActivos().get(i);
                if (user == this) {
                    continue; // No se envía a sí mismo
                }
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

    private String censorMessage(String message) throws IOException {
        String[] words = message.split("\\s+");
        boolean containsBadWord = false;
        StringBuilder censurado = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String cleanWord = words[i].replaceAll("[^a-zA-ZáéíóúÁÉÍÓÚñÑ]", "").toLowerCase();
            boolean esMalaPalabra = false;

            for (String badWord : malasPalabras) {
                if (cleanWord.equalsIgnoreCase(badWord)) {
                    containsBadWord = true;
                    esMalaPalabra = true;
                    break;
                }
            }

            if (esMalaPalabra) {
                // Censurar la palabra con asteriscos
                censurado.append("*".repeat(words[i].length())).append(" ");
            } else {
                censurado.append(words[i]).append(" ");
            }
        }

        if (containsBadWord) {
            advertencias++;
            controlServidor.mostrarMensajeConsolaServidor("Advertencia " + advertencias + "/" + MAX_ADVERTENCIAS + " para usuario: " + servidor.getNombreUsuario());

            if (advertencias >= MAX_ADVERTENCIAS) {
                // Banear después de 3 advertencias
                banearUsuario("Has sido baneado por uso repetido de lenguaje inapropiado.");
                return null;
            } else {
                // Enviar advertencia al usuario
                try {
                    this.servidor.getServidorInformacionSalida2().writeInt(6); // Nuevo código para advertencia
                    this.servidor.getServidorInformacionSalida2().writeUTF("Advertencia " + advertencias + "/" + MAX_ADVERTENCIAS + ": Evita usar lenguaje inapropiado.");
                    this.servidor.getServidorInformacionSalida2().flush();
                } catch (IOException e) {
                    controlServidor.mostrarMensajeConsolaServidor("Error enviando advertencia: " + e.getMessage());
                }
            }

            return censurado.toString().trim(); // Retornar mensaje censurado
        }

        return message; // Retornar mensaje original
    }

    private void banearUsuario(String razon) {
        controlServidor.mostrarMensajeConsolaServidor("Usuario baneado: " + servidor.getNombreUsuario() + " - Razón: " + razon);

        try {
            this.servidor.getServidorInformacionSalida2().writeInt(4);
            this.servidor.getServidorInformacionSalida2().writeUTF(razon);
            this.servidor.getServidorInformacionSalida2().flush();

            notificarDesconexion();

        } catch (IOException e) {
            controlServidor.mostrarMensajeConsolaServidor("Error enviando mensaje de baneo: " + e.getMessage());
        } finally {

            notificarDesconexion();
            ControlServidor.getClientesActivos().removeElement(this);

            try {
                this.servidor.getServidorCliente1().close();
                this.servidor.getServidorCliente2().close();
            } catch (IOException e) {
                controlServidor.mostrarMensajeConsolaServidor("Error cerrando conexiones: " + e.getMessage());
            }
        }
    }
}
