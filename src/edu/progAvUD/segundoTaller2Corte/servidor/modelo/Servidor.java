/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progAvUD.segundoTaller2Corte.servidor.modelo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 *
 * @author Andres Felipe
 */
public class Servidor {

    private Socket servidorCliente1;
    private Socket servidorCliente2;
    
    private DataInputStream servidorInformacionEntrada1;
    private DataOutputStream servidorInformacionSalida1;
    private DataOutputStream servidorInformacionSalida2;
    
    private String nombreUsuario;

    public Servidor(Socket servidorCliente1, Socket servidorCliente2, String nombreUsuario) {
        this.servidorCliente1 = servidorCliente1;
        this.servidorCliente2 = servidorCliente2;
        this.nombreUsuario = nombreUsuario;
    }

    
    
    public Socket getServidorCliente1() {
        return servidorCliente1;
    }

    public void setServidorCliente1(Socket servidorCliente1) {
        this.servidorCliente1 = servidorCliente1;
    }

    public Socket getServidorCliente2() {
        return servidorCliente2;
    }

    public void setServidorCliente2(Socket servidorCliente2) {
        this.servidorCliente2 = servidorCliente2;
    }

    
    
    public DataInputStream getServidorInformacionEntrada1() {
        return servidorInformacionEntrada1;
    }

    public void setServidorInformacionEntrada1(DataInputStream servidorInformacionEntrada1) {
        this.servidorInformacionEntrada1 = servidorInformacionEntrada1;
    }

    public DataOutputStream getServidorInformacionSalida1() {
        return servidorInformacionSalida1;
    }

    public void setServidorInformacionSalida1(DataOutputStream servidorInformacionSalida1) {
        this.servidorInformacionSalida1 = servidorInformacionSalida1;
    }

    public DataOutputStream getServidorInformacionSalida2() {
        return servidorInformacionSalida2;
    }

    public void setServidorInformacionSalida2(DataOutputStream servidorInformacionSalida2) {
        this.servidorInformacionSalida2 = servidorInformacionSalida2;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    

}
