import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

public class Conexion {
    public final int PUERTO = 6000;
    public final String HOST = "localhost";
    public String mensajeServidor;
    public ServerSocket socket_servidor;
    public Socket socket_cliente;
    protected   DataOutputStream salidaServidor, salidaCliente;

    public Conexion(String tipo) throws IOException{
        if (tipo.equalsIgnoreCase("servidor")){
            socket_servidor = new ServerSocket(PUERTO);
            socket_cliente = new Socket();
        }

        else{
            socket_cliente = new Socket(HOST, PUERTO);
        }

    }


}

