package edu.progAvUD.segundoTaller2Corte.cliente.modelo;

import java.io.DataInputStream;

/**
 *
 * @author Andres Felipe
 */
public class Cliente {

    private static String ipServer;
    private String nombreCliente;
    private String nombreUsuario;
    private DataInputStream entrada;

    public Cliente(String nombreCliente, String nombreUsuario, DataInputStream entrada) {
        this.nombreCliente = nombreCliente;
        this.nombreUsuario = nombreUsuario;
        this.entrada = entrada;
    }
    
    public static String getIpServer() {
        return ipServer;
    }

    public static void setIpServer(String ipServer) {
        Cliente.ipServer = ipServer;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public DataInputStream getEntrada() {
        return entrada;
    }

    public void setEntrada(DataInputStream entrada) {
        this.entrada = entrada;
    }

}