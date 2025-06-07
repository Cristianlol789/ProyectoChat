package edu.progAvUD.segundoTaller2Corte.servidor.control;

/**
 * Clase que actúa como punto de coordinación entre los componentes gráficos
 * y lógicos del servidor.
 * 
 * Esta clase se encarga de inicializar tanto la lógica del servidor 
 * (control de conexiones) como la interfaz gráfica de administración.
 * 
 * También sirve como puente para mostrar mensajes desde el `ControlServidor`
 * hacia la interfaz a través del `ControlGrafico`.
 * 
 * Se invoca directamente desde la clase {@link LauncherServidor}.
 * 
 * @author Andres Felipe
 */
public class ControlPrincipal {

    /** Controlador encargado de manejar la interfaz gráfica del servidor. */
    private ControlGrafico controlGrafico;

    /** Controlador encargado de manejar la lógica de conexión del servidor. */
    private ControlServidor controlServidor;

    /**
     * Constructor que inicializa los componentes gráfico y lógico del servidor.
     * También arranca el servidor para que empiece a aceptar conexiones.
     */
    public ControlPrincipal() {
        this.controlServidor = new ControlServidor(this);
        this.controlGrafico = new ControlGrafico(this);

        // Inicia el servidor para aceptar clientes
        controlServidor.runServer();
    }

    /**
     * Envía un mensaje a la consola del servidor (interfaz gráfica).
     *
     * @param mensaje Mensaje que se quiere mostrar.
     */
    public void mostrarMensajeConsolaServidor(String mensaje) {
        controlGrafico.mostrarMensajeConsolaServidor(mensaje);
    }

    /**
     * Muestra un mensaje de éxito en la interfaz gráfica.
     *
     * @param mensaje Mensaje de confirmación o éxito.
     */
    public void mostrarMensajeExito(String mensaje) {
        controlGrafico.mostrarMensajeExito(mensaje);
    }

    /**
     * Muestra un mensaje de error en la interfaz gráfica.
     *
     * @param mensaje Mensaje de error.
     */
    public void mostrarMensajeError(String mensaje) {
        controlGrafico.mostrarMensajeError(mensaje);
    }
}