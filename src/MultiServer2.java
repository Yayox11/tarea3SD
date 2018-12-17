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
        //Servidor escuchando en el puerto 53001
        ServerSocket ss = new ServerSocket(53001);
        Socket s;
        //Se habre la conexion para el intercambio de mensajes
        Socket cliente = new Socket("10.6.40.193",53000);

        object = parser.parse(new FileReader("/root/tarea3SD/src/json/pacientes.json"));
        JsonArray jobject2 = (JsonArray) object;
       Paciente pacientes = new Paciente(jobject2);
        pacientes.archivo_log("");
        //JsonObject paciente = pacientes.datos_paciente(1);

        //Input y Output streams de la conexion
        DataOutputStream salida_servidor = new DataOutputStream(cliente.getOutputStream());
        DataInputStream entrada_servidor = new DataInputStream(cliente.getInputStream());
        //Thread que manejara la solicitud de los requerimientos
        HacerRequerimientos threadReq = new HacerRequerimientos();
        threadReq.start();

        while(true){
            try {
                s = ss.accept(); // Aceptando el request
                // Inputs y outputs Streams del socket
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                String mensaje = dis.readUTF();// Se lee el mensaje
                JsonParser parser2 = new JsonParser();
                JsonObject pac = parser.parse(mensaje).getAsJsonObject();
                if (pac.get("tipo").equals("requerimiento")) {
                    JsonArray pacs = pac.get("mensaje").getAsJsonArray();
                    pacientes.escribir_pacientes("/root/tarea3SD/src/json/pacientes.json", pacs);
                }

                else{
                    pacientes.archivo_log(pac.get("mensaje").getAsString());
                }


            }

            catch (Exception e){

            }

        }
    }
}
