package edu.progAvUD.segundoTaller2Corte.cliente.modelo;

import java.io.*;
import java.net.*;

public class Cliente {
    
    private static String IP_SERVER;
    private DataInputStream entrada;
    private DataOutputStream salida;
    private DataInputStream entrada2;
    private Socket comunication;
    private Socket comunication2;
    private String nombreCliente;
    
    public Cliente() {
    }
    
    public static String getIP_SERVER() {
        return IP_SERVER;
    }
    
    public void setIP_SERVER(String IP_SERVER) {
        Cliente.IP_SERVER = IP_SERVER;
    }
    
    public DataInputStream getEntrada() {
        return entrada;
    }
    
    public void setEntrada(DataInputStream entrada) {
        this.entrada = entrada;
    }
    
    public DataOutputStream getSalida() {
        return salida;
    }
    
    public void setSalida(DataOutputStream salida) {
        this.salida = salida;
    }
    
    public DataInputStream getEntrada2() {
        return entrada2;
    }
    
    public void setEntrada2(DataInputStream entrada2) {
        this.entrada2 = entrada2;
    }
    
    public Socket getComunication() {
        return comunication;
    }
    
    public void setComunication(Socket comunication) {
        this.comunication = comunication;
    }
    
    public Socket getComunication2() {
        return comunication2;
    }
    
    public void setComunication2(Socket comunication2) {
        this.comunication2 = comunication2;
    }
    
    public String getNombreCliente() {
        return nombreCliente;
    }
    
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
}