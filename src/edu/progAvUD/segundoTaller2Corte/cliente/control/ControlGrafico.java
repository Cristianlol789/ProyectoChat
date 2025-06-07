/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progAvUD.segundoTaller2Corte.cliente.control;

import edu.progAvUD.segundoTaller2Corte.cliente.modelo.Cliente;
import edu.progAvUD.segundoTaller2Corte.cliente.vista.VentanaPrincipal;

/**
 *
 * @author Andres Felipe
 */
public class ControlGrafico {
    
    ControlPrincipal controlPrincipal;
    VentanaPrincipal ventanaPrincipal;

    public ControlGrafico(ControlPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
        this.ventanaPrincipal = new VentanaPrincipal();
        
        
        
        
        
        
        Cliente.setIpServer(ventanaPrincipal.pedirIPServer());
    }
    
    
    
}
