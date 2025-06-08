package control;

import control.ControlGrafico;
import control.ThreadCliente;
import modelo.Cliente;
import java.io.*;
import java.net.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ControlCliente {
    private Cliente cliente;
    private ControlGrafico controlGrafico;
    private ThreadCliente threadCliente;
    
    public ControlCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public void setControlGrafico(ControlGrafico controlGrafico) {
        this.controlGrafico = controlGrafico;
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
            String nomCliente = JOptionPane.showInputDialog("Introducir Nick :");
            if (nomCliente == null || nomCliente.trim().isEmpty()) {
                cerrarConexiones();
                System.exit(0);
            }
            
            cliente.setNombreCliente(nomCliente);
            controlGrafico.setNombreUsuario(nomCliente);
            cliente.getSalida().writeUTF(nomCliente);
            
            // Iniciar hilo de recepción
            threadCliente = new ThreadCliente(cliente.getEntrada2(), controlGrafico);
            threadCliente.start();
            
            // Obtener lista de usuarios activos
            Vector<String> usuarios = pedirUsuarios();
            controlGrafico.actualizarUsuariosActivos(usuarios);
            
        } catch (IOException e) {
            System.out.println("\tEl servidor no esta levantado");
            System.out.println("\t=============================");
            throw e;
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
            Logger.getLogger(ControlCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }
    
    public void enviarMensaje(String mensaje) {
        try {
            System.out.println("el mensaje enviado desde el cliente es: " + mensaje);
            cliente.getSalida().writeInt(1);
            cliente.getSalida().writeUTF(mensaje);
        } catch (IOException e) {
            System.out.println("error...." + e);
        }
    }
    
    public void enviarMensajePrivado(String amigo, String mensaje) {
        try {
            System.out.println("el mensaje privado enviado desde el cliente es: " + mensaje);
            cliente.getSalida().writeInt(3);
            cliente.getSalida().writeUTF(amigo);
            cliente.getSalida().writeUTF(mensaje);
        } catch (IOException e) {
            System.out.println("error...." + e);
        }
    }
    
    public String getNombreCliente() {
        return cliente.getNombreCliente();
    }
    
    private void cerrarConexiones() {
        try {
            if (cliente.getEntrada() != null) cliente.getEntrada().close();
            if (cliente.getSalida() != null) cliente.getSalida().close();
            if (cliente.getEntrada2() != null) cliente.getEntrada2().close();
            if (cliente.getComunication() != null) cliente.getComunication().close();
            if (cliente.getComunication2() != null) cliente.getComunication2().close();
        } catch (IOException e) {
            System.out.println("Error cerrando conexiones: " + e.getMessage());
        }
    }
}