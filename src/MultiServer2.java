import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer2 {
    public static void main(String[] args) throws IOException {
        JsonParser parser = new JsonParser();
        Object object = null;
        //Servidor escuchando en el puerto 6000
        ServerSocket ss = new ServerSocket(6001);
        Socket s;
        Socket cliente = new Socket("10.6.40.196",6000);

        object = parser.parse(new FileReader("/root/tarea3SD/src/json/pacientes.json"));
        JsonArray jobject2 = (JsonArray) object;
        Paciente pacientes = new Paciente(jobject2);
        JsonObject paciente = pacientes.datos_paciente(1);

        DataOutputStream salida_servidor = new DataOutputStream(cliente.getOutputStream());
        DataInputStream entrada_servidor = new DataInputStream(cliente.getInputStream());
        HacerRequerimientos threadReq = new HacerRequerimientos(salida_servidor);
        threadReq.start();
        salida_servidor.writeUTF(paciente.toString());
        String mensajeServidor = entrada_servidor.readUTF();
        System.out.println(mensajeServidor);

        while(true){
            try {
                s = ss.accept(); // Aceptando el request
                System.out.println("New client request received");
                // Inputs y outputs Streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                String mensaje = dis.readUTF();
                System.out.println("Mensaje recibido: " + mensaje);

            }

            catch (Exception e){

            }

        }
    }
}
