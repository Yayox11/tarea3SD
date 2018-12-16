import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.*;
import java.net.*;


public class Main {
    boolean coordinador_disponible;

    public static void main(String[] args) throws IOException{
        try {
            Socket cs = new Socket("localhost", 6001);
            Conexion servidor = new Conexion("servidor");
            System.out.println("Esperando...");
            servidor.socket_cliente = servidor.socket_servidor.accept();
            System.out.println("Cliente en linea");
            servidor.salidaCliente = new DataOutputStream(servidor.socket_cliente.getOutputStream());
            servidor.salidaCliente.writeUTF("Peticion recibida y aceptada");
            DataInputStream entrada = new DataInputStream(servidor.socket_cliente.getInputStream());


            servidor.mensajeServidor = entrada.readUTF();
            while (!servidor.mensajeServidor.equals("fin")){
                System.out.println("Llego el mensaje del Cliente");
                System.out.println(servidor.mensajeServidor);
                servidor.mensajeServidor = entrada.readUTF();
            }
            DataOutputStream salida_thread = new DataOutputStream(cs.getOutputStream());
            salida_thread.writeUTF("hola Tread");
            System.out.println("Se envio el mensaje al thread");
            System.out.println("Fin de la conexion");
            servidor.socket_servidor.close();

        }

        catch (Exception e){
            System.out.println("Exception");
            System.out.println(e.getMessage());
        }

        System.out.println("Hello World!");
    }
}
