/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progAvUD.segundoTaller2Corte.servidor.control;

import edu.progAvUD.segundoTaller2Corte.servidor.modelo.Servidor;
import java.net.Socket;

/**
 *
 * @author Andres Felipe
 */
public class ServidorHilo extends Thread{
    
    private Servidor servidor;
    private ControlServidor controlServidor;

    public ServidorHilo(Socket socketCliente, Socket socketCliente2, ControlServidor controlServidor) {
        
    }
    
    
    
    @Override
    public void run(){
        
    }
    
    
}
