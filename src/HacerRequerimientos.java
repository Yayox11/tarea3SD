import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;

public class HacerRequerimientos extends Thread {
    private DataOutputStream salida_servidor;
    public HacerRequerimientos(DataOutputStream salida_servidor){
        this.salida_servidor = salida_servidor;
    }
    public void run(){
        try {
            JsonParser parser = new JsonParser();
            Object object = null;
            object = parser.parse(new FileReader("/Users/jp/Desktop/tarea3SD/src/json/requerimientos.json"));
            JsonObject jobject = (JsonObject) object;
            Requerimientos requerimientos = new Requerimientos(jobject);
            JsonArray arrayRequerimientos = requerimientos.obtener_requerimientos();
            int sizeRequerimientos = arrayRequerimientos.size();

            for (int i = 0; i < sizeRequerimientos; i++){
                Socket client = new Socket("10.6.40.193", 6000);
                DataOutputStream salida = new DataOutputStream(client.getOutputStream());
                JsonObject requerimiento = (JsonObject) arrayRequerimientos.get(i);
                salida.writeUTF(requerimiento.toString());
                client.close();
                Thread.sleep(5000);


            }

        }
        catch (IOException e){

        }
        catch (Exception e){

        }

    }
}
