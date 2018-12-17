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
        //Lectura del archivo Json con los pacientes
        object = parser.parse(new FileReader("/root/tarea3SD/src/json/pacientes.json"));
        JsonArray pacientes = (JsonArray) object;
        Paciente paciente = new Paciente(pacientes);
        paciente.archivo_log("");

        //Servidor escuchando en el puerto 53000
        ServerSocket ss = new ServerSocket(53000);
        Socket s;

        while(true){
            try {
                s = ss.accept(); // Aceptando el request
                System.out.println("New client request received");

                // Inputs y outputs Streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                // Thread que manejara la llegada de solicitudes de requerimientos (manejo concurrente)
                HandlerClient t = new HandlerClient(dis, dos, paciente);
                t.start();

            }

            catch (Exception e){

            }

        }
    }
}
