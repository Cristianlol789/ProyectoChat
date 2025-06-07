package edu.progAvUD.segundoTaller2Corte.servidor.control;

import edu.progAvUD.segundoTaller2Corte.servidor.vista.VentanaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase encargada de manejar la lógica de la interfaz gráfica del servidor.
 * 
 * Esta clase sirve de puente entre la lógica del programa (servidor) y 
 * la interfaz gráfica representada por {@link VentanaPrincipal}.
 * 
 * Permite mostrar mensajes de consola, éxito y error en la ventana principal.
 * También implementa {@link ActionListener}, aunque en esta versión no se 
 * manejan eventos de acción.
 * 
 * Se inicializa automáticamente desde {@link ControlPrincipal}.
 * 
 * @author Andres Felipe
 */
public class ControlGrafico implements ActionListener {

    /** Referencia al controlador principal que gestiona la aplicación. */
    private ControlPrincipal controlPrincipal;

    /** Ventana principal del servidor donde se muestran los mensajes. */
    private VentanaPrincipal ventanaPrincipal;

    /**
     * Constructor que crea e inicializa la ventana principal del servidor.
     * 
     * @param controlPrincipal Referencia al controlador principal.
     */
    public ControlGrafico(ControlPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
        this.ventanaPrincipal = new VentanaPrincipal();

        // Muestra el panel de consola del servidor al iniciar
        ventanaPrincipal.mostrarPanel(ventanaPrincipal.panelConsolaServidor);
        ventanaPrincipal.setVisible(true);
    }

    /**
     * Método sobrescrito de la interfaz {@link ActionListener}.
     * Actualmente no implementa ninguna acción específica.
     * 
     * @param e Evento generado por algún componente gráfico.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // No implementado actualmente
    }

    /**
     * Muestra un mensaje en la consola gráfica del servidor.
     * 
     * @param mensaje Texto a mostrar en el área de consola.
     */
    public void mostrarMensajeConsolaServidor(String mensaje) {
        ventanaPrincipal.mostrarMensajeConsolaServidor(mensaje);
    }

    /**
     * Muestra un mensaje de éxito en la interfaz.
     * 
     * @param mensaje Texto de confirmación o notificación positiva.
     */
    public void mostrarMensajeExito(String mensaje) {
        ventanaPrincipal.mostrarMensajeExito(mensaje);
    }

    /**
     * Muestra un mensaje de error en la interfaz.
     * 
     * @param mensaje Texto indicando que ocurrió un error.
     */
    public void mostrarMensajeError(String mensaje) {
        ventanaPrincipal.mostrarMensajeError(mensaje);
    }
}