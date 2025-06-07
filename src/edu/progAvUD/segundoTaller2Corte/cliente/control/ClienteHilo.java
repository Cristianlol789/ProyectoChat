package edu.progAvUD.segundoTaller2Corte.cliente.control;

import edu.progAvUD.segundoTaller2Corte.cliente.modelo.Cliente;
import java.io.IOException;

/**
 *
 * @author Andres Felipe
 */
public class ClienteHilo extends Thread {

    private Cliente cliente;
    private String mensajeCliente;
    private ControlCliente controlCliente;

    public ClienteHilo(Cliente cliente, ControlCliente controlCliente) {
        this.cliente = cliente;
        this.controlCliente = controlCliente;
    }

//    @Override
//    public void run(){
//        String menser = "", amigo = "";
//        int opcion = 0;
//        while (true) {
//            try {
//                opcion = cliente.getEntrada().readInt();
//                switch (opcion) {
//                    case 1:
//                        menser = cliente.getEntrada().readUTF();
//                        System.out.println("ECO del servidor:" + menser);
//                        controlCliente.mostrarMsg(menser);
//                        break;
//                    case 2://se agrega
//                        menser = cliente.getEntrada().readUTF();
//                        controlCliente.agregarUser(menser);
//                        break;
//                    case 3://mensage de amigo
//                        amigo = cliente.getEntrada().readUTF();
//                        menser = cliente.getEntrada().readUTF();
//                        controlCliente.mensageAmigo(amigo, menser);
//                        System.out.println("ECO del servidor:" + menser);
//                        break;
//                }
//            } catch (IOException e) {
//                System.out.println("Error en la comunicaci�n " + "Informaci�n para el usuario");
//                break;
//            }
//        }
//        System.out.println("se desconecto el servidor");
//    }
}