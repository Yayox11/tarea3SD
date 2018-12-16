import java.io.*;
import java.net.*;


public class Main {

    public static void main(String[] args) throws IOException{
        try {
            Conexion servidor = new Conexion("servidor");
            System.out.println("Esperando...");
            servidor.socket_cliente = servidor.socket_servidor.accept();
            System.out.println("Cliente en linea");
            servidor.salidaCliente = new DataOutputStream(servidor.socket_cliente.getOutputStream());
            servidor.salidaCliente.writeUTF("Peticion recibida y aceptada");
            BufferedReader entrada = new BufferedReader(new InputStreamReader(servidor.socket_cliente.getInputStream()));

            servidor.mensajeServidor = entrada.readLine();
            System.out.println(servidor.mensajeServidor);
            while (!servidor.mensajeServidor.equals("fin")){
                System.out.println("Llego el mensaje del Cliente");
                System.out.println(servidor.mensajeServidor);
                servidor.mensajeServidor = entrada.readLine();
            }
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
