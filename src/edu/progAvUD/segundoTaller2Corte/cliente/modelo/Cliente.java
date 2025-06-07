/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progAvUD.segundoTaller2Corte.cliente.modelo;

/**
 *
 * @author Andres Felipe
 */
public class Cliente {

    private static String ipServer;
    private String nombreCliente;
    private String nombreUsuario;

    public Cliente(String nombreCliente, String nombreUsuario) {
        this.nombreCliente = nombreCliente;
        this.nombreUsuario = nombreUsuario;
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
    
    
    
}