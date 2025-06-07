/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progAvUD.segundoTaller2Corte.servidor.control;

import edu.progAvUD.segundoTaller2Corte.servidor.vista.VentanaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Andres Felipe
 */
public class ControlGrafico implements ActionListener{
    
    private ControlPrincipal controlPrincipal;
    private VentanaPrincipal ventanaPrincipal;

    public ControlGrafico(ControlPrincipal controlPrincipal) {
        this.controlPrincipal =controlPrincipal;
        this.ventanaPrincipal = new VentanaPrincipal();
        
        ventanaPrincipal.mostrarPanel(ventanaPrincipal.panelConsolaServidor);
        ventanaPrincipal.setVisible(true);
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
    
    public void mostrarMensajeConsolaServidor(String mensaje){
        ventanaPrincipal.mostrarMensajeConsolaServidor(mensaje);
    }
    
    public void mostrarMensajeExito(String mensaje) {
        ventanaPrincipal.mostrarMensajeExito(mensaje);
    }

    
    public void mostrarMensajeError(String mensaje) {
        ventanaPrincipal.mostrarMensajeError(mensaje);
    }
    
    
    
}
