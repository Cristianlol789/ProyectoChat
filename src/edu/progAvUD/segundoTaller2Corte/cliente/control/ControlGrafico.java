package edu.progAvUD.segundoTaller2Corte.cliente.control;

import edu.progAvUD.segundoTaller2Corte.cliente.modelo.Cliente;
import edu.progAvUD.segundoTaller2Corte.cliente.vista.VentanaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Andres Felipe
 */
public class ControlGrafico implements ActionListener {

    private ControlPrincipal controlPrincipal;
    private VentanaPrincipal ventanaPrincipal;

    public ControlGrafico(ControlPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
        this.ventanaPrincipal = new VentanaPrincipal(this);
        Cliente.setIpServer(ventanaPrincipal.pedirIPServer());
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        String comand = evt.getActionCommand();

        if (comand.compareTo("help") == 0) {
            ventanaPrincipal.mostrarPanel(ventanaPrincipal.panelAyuda);
        }

        if (comand.compareTo("Acerca") == 0) {
            ventanaPrincipal.mostrarAcera();
        }

        if (evt.getSource() == ventanaPrincipal.panelPrincipal.butEnviar || evt.getSource() == ventanaPrincipal.panelPrincipal.txtMensage) {
            String mensaje = ventanaPrincipal.panelPrincipal.txtMensage.getText();
            controlPrincipal.getControlCliente().flujo(mensaje);
            ventanaPrincipal.panelPrincipal.txtMensage.setText("");
        } else if (evt.getSource() == ventanaPrincipal.panelPrincipal.butPrivado) {
            int pos = ventanaPrincipal.panelPrincipal.lstActivos.getSelectedIndex();
            if (pos >= 0) {
                String amigo = ventanaPrincipal.panelPrincipal.lstActivos.getModel().getElementAt(pos).toString();
                ventanaPrincipal.panelPrivado.setAmigo(amigo);
                ventanaPrincipal.mostrarPanel(ventanaPrincipal.panelPrivado);
            }
        }
    }

    // Methods to update UI panels called from ControlPrincipal / ControlCliente
    public void mostrarMensajeEnPanelPrincipal(String mensaje) {
        ventanaPrincipal.panelPrincipal.panMostrar.append(mensaje + "\n");
    }

    public void actualizarListaUsuarios(String[] usuarios) {
        ventanaPrincipal.panelPrincipal.lstActivos.setListData(usuarios);
    }

    public void mostrarPanelPrivado(String amigo, String mensaje) {
        ventanaPrincipal.panelPrivado.setAmigo(amigo);
        ventanaPrincipal.panelPrivado.panMostrar.append(mensaje + "\n");
        ventanaPrincipal.mostrarPanel(ventanaPrincipal.panelPrivado);
    }
}
