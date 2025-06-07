/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progAvUD.segundoTaller2Corte.servidor.control;

/**
 *
 * @author Andres Felipe
 */
public class ControlPrincipal {
    
    private ControlGrafico controlGrafico;
    private ControlServidor controlServidor;
    
    public ControlPrincipal() {
        this.controlGrafico = new ControlGrafico(this);
        this.controlServidor = new ControlServidor(this);
        
    }
    
    public void mostrarMensajeConsolaServidor(String mensaje){
        controlGrafico.mostrarMensajeConsolaServidor(mensaje);
    }
    
    public void mostrarMensajeExito(String mensaje) {
        controlGrafico.mostrarMensajeExito(mensaje);
    }

    
    public void mostrarMensajeError(String mensaje) {
        controlGrafico.mostrarMensajeError(mensaje);
    }
    
    
}
