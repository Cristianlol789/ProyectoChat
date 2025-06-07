/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progAvUD.segundoTaller2Corte.cliente.control;

/**
 *
 * @author Andres Felipe
 */
public class ControlPrincipal {
    
    private ControlGrafico controlGrafico;
    private ControlCliente controlCliente;

    public ControlPrincipal() {
        controlGrafico = new ControlGrafico(this);
    }
    
    public ControlGrafico getControlGrafico() {
        return controlGrafico;
    }
    
    public ControlCliente getControlCliente() {
        return controlCliente;
    }
    
    public void setControlCliente(ControlCliente controlCliente) {
        this.controlCliente = controlCliente;
    }
    
    // Methods to delegate UI updates to ControlGrafico / VentanaPrincipal
    
    public void mostrarMensajeEnPanelPrincipal(String mensaje) {
        controlGrafico.mostrarMensajeEnPanelPrincipal(mensaje);
    }
    
    public void actualizarListaUsuarios(String[] usuarios) {
        controlGrafico.actualizarListaUsuarios(usuarios);
    }
    
    public void mostrarPanelPrivado(String amigo, String mensaje) {
        controlGrafico.mostrarPanelPrivado(amigo, mensaje);
    }
}
