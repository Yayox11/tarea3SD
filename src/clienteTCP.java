import java.io.*;
import java.net.*;
import java.util.Scanner;

public class clienteTCP {
    public static void main(String[]args) throws Exception{
        try {
            Conexion cliente = new Conexion("cliente");
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
            System.out.println(e.getMessage());

        }


    }
}
