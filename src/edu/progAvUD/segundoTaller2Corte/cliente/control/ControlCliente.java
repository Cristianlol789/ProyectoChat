package edu.progAvUD.segundoTaller2Corte.cliente.control;

import edu.progAvUD.segundoTaller2Corte.cliente.modelo.Cliente;
import java.io.*;
import java.net.*;
import java.util.Vector;

public class ControlCliente {

    private Cliente cliente;
    private ControlPrincipal controlPrincipal;
    private ThreadCliente threadCliente;

    // Listados palabras censuradas
    private static final String[] malasPalabras = {
        "carajo", "mierda", "joder", "coño", "pendejo", "pendeja", "cabron", "cabrona", "hijo de puta", "hija de puta", "puta", "puto", "gilipollas", "imbécil", "imbecil", "malparido", "malparida", "culero", "culera", "estúpido", "estúpida", "mamón", "mamona", "boludo", "boluda", "pelotudo", "pelotuda", "zorra", "perra", "baboso", "babosa", "chinga tu madre", "pinche", "maricón", "maricona", "marica", "verga", "chingada", "chingado", "cojudo", "cojuda", "idiota", "tarado", "tarada", "tonto", "tonta", "mierdoso", "mierdosa", "culiao", "culiá", "culiador", "culiadora", "concha", "forro", "forra", "pajero", "pajera", "ñero", "ñera", "culicagado", "culicagada", "güevón", "güevona", "mamaguevo", "mamagueva", "gonorrea", "gonorreta", "careverga", "carechimba", "malnacido", "malnacida", "come mierda", "tragaleche", "pichón", "pichona", "putona", "putón", "bastardo", "bastarda", "pervertido", "pervertida", "asqueroso", "asquerosa", "cagada", "cagado", "cochina", "cochino", "infeliz", "sucia", "sucio", "machorra", "pirobo", "piroba", "loca", "loco", "petardo", "petarda", "mierdín", "mierdina", "carapicha", "carapicho", "soplapollas", "chupapijas", "sarna", "apestoso", "apestosa", "degenerado", "degenerada", "desgraciado", "desgraciada", "desubicado", "desubicada", "imbesil", "tarúpido", "tarúpida"
    };

    public ControlCliente(ControlPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
    }

    public void crearCliente() {
        cliente = new Cliente();
    }

    public void actualizarIP(String ipServer) {
        cliente.setIP_SERVER(ipServer);
    }

    public void conectar() throws IOException {
        try {
            // Crear conexiones
            Socket comunication = new Socket(Cliente.getIP_SERVER(), 8081);
            Socket comunication2 = new Socket(Cliente.getIP_SERVER(), 8082);

            cliente.setComunication(comunication);
            cliente.setComunication2(comunication2);
            cliente.setEntrada(new DataInputStream(comunication.getInputStream()));
            cliente.setSalida(new DataOutputStream(comunication.getOutputStream()));
            cliente.setEntrada2(new DataInputStream(comunication2.getInputStream()));

            // Solicitar nombre de usuario
            String nomCliente = controlPrincipal.pedirNombreCliente();
            if (nomCliente == null || nomCliente.trim().isEmpty()) {
                cerrarConexiones();
                System.exit(0);
            }

            cliente.setNombreCliente(nomCliente);
            controlPrincipal.cambiarNombreUsuario(nomCliente);
            cliente.getSalida().writeUTF(nomCliente);

            // Iniciar hilo de recepción
            threadCliente = new ThreadCliente(cliente.getEntrada2(), this);
            threadCliente.start();

            // Obtener lista de usuarios activos
            Vector<String> usuarios = pedirUsuarios();
            controlPrincipal.actualizarUsuariosActivos(usuarios);

        } catch (IOException e) {
            controlPrincipal.mostrarMensajeError("\tEl servidor no esta levantado \n \t=============================");
        }
    }

    public Vector<String> pedirUsuarios() {
        Vector<String> users = new Vector<>();
        try {
            cliente.getSalida().writeInt(2);
            int numUsers = cliente.getEntrada().readInt();
            for (int i = 0; i < numUsers; i++) {
                users.add(cliente.getEntrada().readUTF());
            }
        } catch (IOException ex) {
            controlPrincipal.mostrarMensajeError("Hay un problema con los listados");
        }
        return users;
    }

    // Method to censor bad words in a message
    private String censorMessage(String message) {
        String[] words = message.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            for (String badWord : malasPalabras) {
                if (words[i].equalsIgnoreCase(badWord)) {
                    words[i] = words[i].replaceAll(".", "*");
                }
            }
        }
        return String.join(" ", words);
    }

    public void enviarMensaje(String mensaje) {
        try {
            String censoredMessage = censorMessage(mensaje);
            cliente.getSalida().writeInt(1);
            cliente.getSalida().writeUTF(censoredMessage);
        } catch (IOException e) {
            controlPrincipal.mostrarMensajeError("error...." + e);
        }
    }

    public void enviarMensajePrivado(String amigo, String mensaje) {
        try {
            String censoredMessage = censorMessage(mensaje);
            cliente.getSalida().writeInt(3);
            cliente.getSalida().writeUTF(amigo);
            cliente.getSalida().writeUTF(censoredMessage);
        } catch (IOException e) {
            controlPrincipal.mostrarMensajeError("error...." + e);
        }
    }

    public String getNombreCliente() {
        return cliente.getNombreCliente();
    }

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

    public void mostarMensaje(String mensaje) {
        controlPrincipal.mostarMensaje(mensaje);
    }

    public void agregarUsuario(String mensaje) {
        controlPrincipal.agregarUsuario(mensaje);
    }

    public void mostrarMensajePrivado(String amigo, String mensaje) {
        controlPrincipal.mostrarMensajePrivado(amigo, mensaje);
    }
    
    public void mostrarMensajeError(String mensaje){
        controlPrincipal.mostrarMensajeError(mensaje);
    }
}
