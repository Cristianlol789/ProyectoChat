/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progAvUD.segundoTaller2Corte.servidor.modelo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Andres Felipe
 */
public class Servidor {

    private ServerSocket servidorEntrada1;
    private ServerSocket servidorEntrada2;
    private Socket servidorSalida1;
    private Socket servidorSalida2;
    
    private DataInputStream servidorInformacionEntrada1;
    private DataOutputStream servidorInformacionSalida1;
    private DataOutputStream servidorInformacionSalida2;
    
    private String nombreUsuario;
    private ArrayList<String> usuarios;
    private boolean servidorEscuchando;

    public Servidor(ServerSocket servidorEntrada1, ServerSocket servidorEntrada2, Socket servidorSalida1, Socket servidorSalida2, DataInputStream servidorInformacionEntrada1, DataOutputStream servidorInformacionSalida1, DataOutputStream servidorInformacionSalida2, ArrayList<String> usuarios, boolean servidorEscuchando) {
        this.servidorEntrada1 = servidorEntrada1;
        this.servidorEntrada2 = servidorEntrada2;
        this.servidorSalida1 = servidorSalida1;
        this.servidorSalida2 = servidorSalida2;
        this.servidorInformacionEntrada1 = servidorInformacionEntrada1;
        this.servidorInformacionSalida1 = servidorInformacionSalida1;
        this.servidorInformacionSalida2 = servidorInformacionSalida2;
        this.usuarios = usuarios;
        this.servidorEscuchando = servidorEscuchando;
    }

    public Servidor(ServerSocket servidorEntrada1, ServerSocket servidorEntrada2, String nombreUsuario) {
        this.servidorEntrada1 = servidorEntrada1;
        this.servidorEntrada2 = servidorEntrada2;
        this.nombreUsuario = nombreUsuario;
    }

    
    
    

    public ServerSocket getServidorEntrada1() {
        return servidorEntrada1;
    }

    public void setServidorEntrada1(ServerSocket servidorEntrada1) {
        this.servidorEntrada1 = servidorEntrada1;
    }

    public ServerSocket getServidorEntrada2() {
        return servidorEntrada2;
    }

    public void setServidorEntrada2(ServerSocket servidorEntrada2) {
        this.servidorEntrada2 = servidorEntrada2;
    }

    public Socket getServidorSalida1() {
        return servidorSalida1;
    }

    public void setServidorSalida1(Socket servidorSalida1) {
        this.servidorSalida1 = servidorSalida1;
    }

    public Socket getServidorSalida2() {
        return servidorSalida2;
    }

    public void setServidorSalida2(Socket servidorSalida2) {
        this.servidorSalida2 = servidorSalida2;
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

    public ArrayList<String> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<String> usuarios) {
        this.usuarios = usuarios;
    }

    public boolean isServidorEscuchando() {
        return servidorEscuchando;
    }

    public void setServidorEscuchando(boolean servidorEscuchando) {
        this.servidorEscuchando = servidorEscuchando;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    

}
