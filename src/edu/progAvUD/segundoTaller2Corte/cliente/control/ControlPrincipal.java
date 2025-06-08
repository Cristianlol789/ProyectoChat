/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package control;

import modelo.Cliente;
import javax.swing.JOptionPane;
import java.io.IOException;

public class ControlPrincipal {
    private ControlGrafico controlGrafico;
    private ControlCliente controlCliente;
    
    public ControlPrincipal() {
        try {
            inicializar();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al inicializar la aplicación: " + e.getMessage());
            System.exit(1);
        }
    }
    
    private void inicializar() throws IOException {
        // Solicitar IP del servidor
        String ipServer = JOptionPane.showInputDialog("Introducir IP_SERVER :", "localhost");
        if (ipServer == null || ipServer.trim().isEmpty()) {
            System.exit(0);
        }
        
        // Crear modelo Cliente
        Cliente cliente = new Cliente();
        cliente.setIP_SERVER(ipServer);
        
        // Crear controladores
        controlCliente = new ControlCliente(cliente);
        controlGrafico = new ControlGrafico(controlCliente);
        
        // Establecer referencias cruzadas
        controlCliente.setControlGrafico(controlGrafico);
        
        // Iniciar conexión
        controlCliente.conectar();
    }
    
}