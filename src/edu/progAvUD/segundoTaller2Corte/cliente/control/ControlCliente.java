package edu.progAvUD.segundoTaller2Corte.cliente.control;

import edu.progAvUD.segundoTaller2Corte.cliente.modelo.Cliente;
import edu.progAvUD.segundoTaller2Corte.cliente.vista.PanelPrincipal;
import edu.progAvUD.segundoTaller2Corte.cliente.vista.PanelPrivado;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Vector;
import javax.swing.SwingUtilities;

/**
 *
 * @author Andres Felipe
 */
public class ControlCliente {

    private ControlPrincipal controlPrincipal;
    private Cliente cliente;
    private PanelPrincipal panelPrincipal;
    private PanelPrivado panelPrivado;

    private DataInputStream entrada = null;
    private DataOutputStream salida = null;
    private DataInputStream entrada2 = null;
    private Socket comunication = null; // para la comunicacion
    private Socket comunication2 = null; // para recibir msg

    private String nomCliente;

    private Thread hiloLectura;

    public ControlCliente(ControlPrincipal controlPrincipal, PanelPrincipal panelPrincipal, PanelPrivado panelPrivado) {
        this.controlPrincipal = controlPrincipal;
        this.panelPrincipal = panelPrincipal;
        this.panelPrivado = panelPrivado;
    }

    public void conexion(String ipServer) throws IOException {
        try {
            comunication = new Socket(ipServer, 8081);
            comunication2 = new Socket(ipServer, 8082);
            entrada = new DataInputStream(comunication.getInputStream());
            salida = new DataOutputStream(comunication.getOutputStream());
            entrada2 = new DataInputStream(comunication2.getInputStream());

            nomCliente = javax.swing.JOptionPane.showInputDialog("Introducir Nick :");
            cliente = new Cliente(nomCliente, nomCliente, entrada);
            controlPrincipal.mostrarMensajeEnPanelPrincipal("Usuario " + nomCliente);
            salida.writeUTF(nomCliente);

            startListening();

        } catch (IOException e) {
            System.out.println("\tEl servidor no esta levantado");
            System.out.println("\t=============================");
            throw e;
        }
    }

    private void startListening() {
        hiloLectura = new Thread(() -> {
            int opcion = 0;
            try {
                while (true) {
                    opcion = entrada2.readInt();
                    switch (opcion) {
                        case 1: // mensaje enviado
                            final String menser1 = entrada2.readUTF();
                            System.out.println("ECO del servidor:" + menser1);
                            SwingUtilities.invokeLater(() -> controlPrincipal.mostrarMensajeEnPanelPrincipal(menser1));
                            break;
                        case 2: // se agrega usuario
                            final String menser2 = entrada2.readUTF();
                            SwingUtilities.invokeLater(() -> {
                                Vector<String> users = new Vector<>();
                                for (int i = 0; i < panelPrincipal.lstActivos.getModel().getSize(); i++) {
                                    users.add(panelPrincipal.lstActivos.getModel().getElementAt(i).toString());
                                }
                                users.add(menser2);
                                controlPrincipal.actualizarListaUsuarios(users.toArray(new String[0]));
                            });
                            break;
                        case 3: // mensaje de amigo
                            final String amigo = entrada2.readUTF();
                            final String menser3 = entrada2.readUTF();
                            SwingUtilities.invokeLater(() -> {
                                controlPrincipal.mostrarPanelPrivado(amigo, menser3);
                            });
                            System.out.println("ECO del servidor:" + menser3);
                            break;
                    }
                }
            } catch (IOException e) {
                System.out.println("Error en la comunicación. Información para el usuario");
            }
            System.out.println("se desconecto el servidor");
        });
        hiloLectura.start();
    }

    public Vector<String> pedirUsuarios() {
        Vector<String> users = new Vector<>();
        try {
            salida.writeInt(2);
            int numUsers = entrada.readInt();
            for (int i = 0; i < numUsers; i++) {
                users.add(entrada.readUTF());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return users;
    }

    public void flujo(String mens) {
        try {
            System.out.println("el mensaje enviado desde el cliente es :" + mens);
            salida.writeInt(1);
            salida.writeUTF(mens);
        } catch (IOException e) {
            System.out.println("error...." + e);
        }
    }

    public void flujo(String amigo, String mens) {
        try {
            System.out.println("el mensaje enviado desde el cliente es :" + mens);
            salida.writeInt(3); // opcion de mensaje a amigo
            salida.writeUTF(amigo);
            salida.writeUTF(mens);
        } catch (IOException e) {
            System.out.println("error...." + e);
        }
    }

    public String getNombre() {
        return nomCliente;
    }
}
