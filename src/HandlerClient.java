import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class HandlerClient extends Thread {
    private DataInputStream dis;
    private DataOutputStream dos;
    volatile static ArrayList <Integer> lista_pacientes_editando = new ArrayList<Integer>();
    volatile static int numThreads = 0;
    volatile static Paciente pacientes;
    public HandlerClient(DataInputStream dis, DataOutputStream dos, Paciente pacientes){
        this.dis = dis;
        this.dos = dos;
        this.pacientes = pacientes;
    }

    public static synchronized void getElement(){
        int num = lista_pacientes_editando.get(numThreads);
        System.out.println("id siendo utilizada: "+num);
    }

    public static synchronized void editarPacientes(JsonObject procedimiento, String cargo){
        pacientes.editar_procedimiento(procedimiento, cargo);
    }

    public void run(){
        try {
            String requerimiento = dis.readUTF();
            JsonParser parser = new JsonParser();
            JsonObject usuario = parser.parse(requerimiento).getAsJsonObject();
            dos.writeUTF("Me llego tu mensaje");
            System.out.println("Se recibio el requerimiento de id:" + usuario.get("id"));
            JsonArray requerimientos = usuario.getAsJsonArray("pacientes");


            for (int i = 0; i< requerimientos.size(); i++){
                JsonObject req = requerimientos.get(i).getAsJsonObject();
                editarPacientes(req, usuario.get("cargo").getAsString());
            }
            System.out.println(requerimientos);
            lista_pacientes_editando.add(usuario.get("id").getAsInt());
            getElement();
            numThreads++;
            //System.out.println(lista_pacientes_editando.size());

        }

        catch (IOException e){
        }

    }
}
