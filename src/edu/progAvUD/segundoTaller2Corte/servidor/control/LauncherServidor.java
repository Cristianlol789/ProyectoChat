package edu.progAvUD.segundoTaller2Corte.servidor.control;

/**
 * Clase principal (launcher) del servidor.
 * 
 * Esta clase contiene el método `main` que se encarga de iniciar la aplicación del servidor,
 * creando una instancia de la clase {@link ControlPrincipal}, la cual se encarga de gestionar
 * la interfaz gráfica y la lógica de control del servidor.
 * 
 * Normalmente, este punto de entrada se utiliza para ejecutar la aplicación desde la consola o un IDE.
 * 
 * @author Andres Felipe
 */
public class LauncherServidor {
    
    /**
     * Método principal que inicia la ejecución del servidor.
     *
     * @param args Argumentos de línea de comandos (no utilizados en esta implementación).
     */
    public static void main(String[] args) {
        new ControlPrincipal();
    }
}