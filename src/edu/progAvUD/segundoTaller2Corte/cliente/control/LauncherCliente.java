package edu.progAvUD.segundoTaller2Corte.cliente.control;

/**
 * Clase LauncherCliente
 *
 * Esta clase actúa como el punto de entrada (entry point) principal
 * para la aplicación cliente del sistema de chat.
 * 
 * Su única responsabilidad es iniciar la ejecución del programa
 * creando una instancia de la clase ControlPrincipal, que a su vez
 * se encarga de inicializar todos los componentes necesarios para
 * el funcionamiento del cliente (interfaz gráfica, conexión de red, etc.).
 * 
 * Autor: Andres Felipe
 */
public class LauncherCliente {

    /**
     * Método main
     * 
     * Este método es el punto de inicio de la aplicación cliente.
     * Al ejecutarse, instancia la clase ControlPrincipal para dar
     * inicio a la configuración y ejecución del cliente.
     *
     * @param args Argumentos pasados por línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        new ControlPrincipal(); // Inicia el controlador principal del cliente
    }
}