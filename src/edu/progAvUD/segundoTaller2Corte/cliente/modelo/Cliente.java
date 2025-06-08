package edu.progAvUD.segundoTaller2Corte.cliente.modelo;

import java.io.*;
import java.net.*;

/**
 * Clase Cliente
 * 
 * Esta clase representa el modelo del cliente en una aplicación de chat.
 * Su función principal es mantener la información de la conexión del cliente
 * con el servidor, incluyendo los sockets y flujos de entrada/salida que permiten
 * el envío y recepción de mensajes.
 * 
 * También almacena información como la IP del servidor y el nombre del cliente.
 * 
 * @author Andres Felipe
 */
public class Cliente {

    // Dirección IP del servidor al cual se conecta el cliente
    private static String IP_SERVER;

    // Flujo de entrada desde el servidor (lectura de datos)
    private DataInputStream entrada;

    // Flujo de salida hacia el servidor (envío de datos)
    private DataOutputStream salida;

    // Segundo flujo de entrada (posiblemente para mensajes privados o monitoreo)
    private DataInputStream entrada2;

    // Socket principal de comunicación con el servidor
    private Socket comunication;

    // Segundo socket de comunicación (posiblemente para otro canal)
    private Socket comunication2;

    // Nombre del cliente (usuario)
    private String nombreCliente;

    /**
     * Constructor por defecto.
     * Inicializa una instancia vacía del cliente.
     */
    public Cliente() {
    }

    /**
     * Obtiene la IP del servidor.
     * @return IP del servidor
     */
    public static String getIP_SERVER() {
        return IP_SERVER;
    }

    /**
     * Establece la IP del servidor.
     * @param IP_SERVER IP a la que se debe conectar el cliente
     */
    public void setIP_SERVER(String IP_SERVER) {
        Cliente.IP_SERVER = IP_SERVER;
    }

    /**
     * Obtiene el flujo de entrada principal.
     * @return DataInputStream de entrada
     */
    public DataInputStream getEntrada() {
        return entrada;
    }

    /**
     * Establece el flujo de entrada principal.
     * @param entrada DataInputStream
     */
    public void setEntrada(DataInputStream entrada) {
        this.entrada = entrada;
    }

    /**
     * Obtiene el flujo de salida principal.
     * @return DataOutputStream de salida
     */
    public DataOutputStream getSalida() {
        return salida;
    }

    /**
     * Establece el flujo de salida principal.
     * @param salida DataOutputStream
     */
    public void setSalida(DataOutputStream salida) {
        this.salida = salida;
    }

    /**
     * Obtiene el segundo flujo de entrada.
     * @return DataInputStream secundario
     */
    public DataInputStream getEntrada2() {
        return entrada2;
    }

    /**
     * Establece el segundo flujo de entrada.
     * @param entrada2 DataInputStream secundario
     */
    public void setEntrada2(DataInputStream entrada2) {
        this.entrada2 = entrada2;
    }

    /**
     * Obtiene el socket de comunicación principal.
     * @return Socket principal
     */
    public Socket getComunication() {
        return comunication;
    }

    /**
     * Establece el socket de comunicación principal.
     * @param comunication Socket principal
     */
    public void setComunication(Socket comunication) {
        this.comunication = comunication;
    }

    /**
     * Obtiene el segundo socket de comunicación.
     * @return Socket secundario
     */
    public Socket getComunication2() {
        return comunication2;
    }

    /**
     * Establece el segundo socket de comunicación.
     * @param comunication2 Socket secundario
     */
    public void setComunication2(Socket comunication2) {
        this.comunication2 = comunication2;
    }

    /**
     * Obtiene el nombre del cliente.
     * @return Nombre del cliente
     */
    public String getNombreCliente() {
        return nombreCliente;
    }

    /**
     * Establece el nombre del cliente.
     * @param nombreCliente Nombre del cliente
     */
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
}