import java.io.*;
import java.net.*;
import java.util.Scanner;

public class clienteTCP {
    public static boolean coordinador_disponible;
    public static void main(String[]args) throws Exception{

        while(true){
            try {
                Conexion2 t = new Conexion2("servidor");
                t.start();
                System.out.println("Se va a crear el socket del proceso principal");
                Conexion cliente = new Conexion("cliente");
                DataInputStream respuesta_servidor = new DataInputStream(cliente.socket_cliente.getInputStream());
                coordinador_disponible = true;
                String respuesta = respuesta_servidor.readUTF();
                System.out.println(respuesta);
                cliente.salidaServidor = new DataOutputStream(cliente.socket_cliente.getOutputStream());
                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader (isr);
                String mensaje = br.readLine();

                while (!mensaje.equals("fin")){
                    System.out.println("Enviando mensaje....");
                    cliente.salidaServidor.writeUTF(mensaje+"\n");
                    InputStreamReader isr2 = new InputStreamReader(System.in);
                    BufferedReader br2 = new BufferedReader (isr2);
                    mensaje = br2.readLine();

                }

                Thread.sleep(10000);
                cliente.socket_cliente.close();

            }
            catch (Exception e){
                coordinador_disponible = false;
                //System.out.println(e.getMessage());

            }

            if(!coordinador_disponible){
                //System.out.println("El servidor no esta disponible");
            }


        }
    }
}
