import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Conexion2 extends Thread {
    public final int PUERTO = 6001;
    public final String HOST = "localhost";
    public ServerSocket server_socket;
    public Socket client_socket;

    public Conexion2(String type) throws IOException {
        if(type.equalsIgnoreCase("servidor")){
            server_socket = new ServerSocket(PUERTO);
            client_socket = new Socket();
        }
        else{
            client_socket = new Socket(HOST, PUERTO);
        }
    }

    public void run(){
        try {
            System.out.println("Thread esperando mensaje");
            this.client_socket = server_socket.accept();
            System.out.println("Thread acepto la conexion");
        }
        catch (Exception e){

        }
        while (true){
            try {
                Thread.sleep(1000);
                DataInputStream entrada = new DataInputStream(client_socket.getInputStream());
                String mensaje = entrada.readUTF();
                //System.out.println("Paso el readLine");

               if (mensaje.equals("hola Thread")){
                   System.out.println("Llego el mensaje:");
                   System.out.println(mensaje);
                   mensaje = entrada.readUTF();
               }

            }
            catch (Exception e){
                System.out.println("Se cayo la conexion");

            }
        }
    }
}


