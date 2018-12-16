import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jdk.nashorn.internal.parser.JSONParser;

import java.lang.String;

import java.io.*;
import java.net.*;
import java.util.*;

public class MultiServer {
    public static void main(String[] args) throws IOException{
        System.out.println("Servidor arriba!!!!");
        JsonParser parser = new JsonParser();
        Object object = null;
        object = parser.parse(new FileReader("/root/src/json/pacientes.json"));
        JsonArray pacientes = (JsonArray) object;
        Paciente paciente = new Paciente(pacientes);

        //Servidor escuchando en el puerto 6000
        ServerSocket ss = new ServerSocket(6000);
        Socket s;

        while(true){
            try {
                s = ss.accept(); // Aceptando el request
                System.out.println("New client request received");
                // Inputs y outputs Streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                HandlerClient t = new HandlerClient(dis, dos, paciente);
                t.start();
                //dos.writeUTF("SI ESTOY CTM!!!");
                //String mensaje = dis.readUTF();
                //JsonParser parser = new JsonParser();
                //JsonObject paciente = parser.parse(mensaje).getAsJsonObject();
                //System.out.println("Mensaje recibido: " + mensaje);
                //System.out.println("JSON :"+ paciente.get("cargo"));

            }

            catch (Exception e){

            }

        }
    }
}
