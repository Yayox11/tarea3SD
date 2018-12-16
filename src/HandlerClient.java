import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

public class HandlerClient extends Thread {
    private DataInputStream dis;
    private DataOutputStream dos;
    private LinkedList<JsonObject> colaReqPendientes;
    volatile static ArrayList <String> lista_pacientes_editando = new ArrayList<String>();
    volatile static int numThreads = 0;
    volatile static Paciente pacientes;



    public HandlerClient(DataInputStream dis, DataOutputStream dos, Paciente pacientes){
        this.colaReqPendientes = new LinkedList<JsonObject>();
        this.dis = dis;
        this.dos = dos;
        this.pacientes = pacientes;
    }

    public static synchronized void getElement(){
        System.out.println("id siendo utilizada: "+ numThreads);
    }

    public static synchronized void editarPacientes(JsonObject procedimiento, String cargo){
        pacientes.editar_procedimiento(procedimiento, cargo);
    }


    public static synchronized Boolean editandoPaciente(String id){
        return lista_pacientes_editando.contains(id);
    }
    public static synchronized void removeEditando(String id){
        lista_pacientes_editando.remove(id);

    }

    public Boolean quedanReq(){
        if (colaReqPendientes.size() > 0){
            return true;
        }

        else{
            return false;
        }
    }

    public static synchronized void enviarCambios(){
        try {
            Socket maquinas54 = new Socket("10.6,40.194", 6001);
            Socket maquina55 =  new Socket("10.6,40.195", 6001);
            Socket maquina56 = new Socket("10.6,40.196", 6001);
        }
        catch (Exception e){

        }

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
                String [] split = requerimiento.toString().split(" ");
                String id_pacienteEditar = split[0];
                if(!editandoPaciente(id_pacienteEditar)){
                    lista_pacientes_editando.add(id_pacienteEditar);
                    editarPacientes(req, usuario.get("cargo").getAsString());
                    removeEditando(id_pacienteEditar);
                }
                else{
                    colaReqPendientes.add(req);
                }
            }
            while(quedanReq()){
                JsonObject req = colaReqPendientes.element().getAsJsonObject();
                String[] split = req.toString().split(" ");
                String id_pacienteEditar = split[0];
                if (!editandoPaciente(id_pacienteEditar)){
                    lista_pacientes_editando.add(id_pacienteEditar);
                    editarPacientes(req, usuario.get("cargo").getAsString());
                    removeEditando(id_pacienteEditar);
                }
                else{
                    colaReqPendientes.remove(req);
                    colaReqPendientes.add(req);
                }
            }
            System.out.println(requerimientos);

            getElement();
            numThreads++;
            //System.out.println(lista_pacientes_editando.size());

        }

        catch (IOException e){
        }

    }
}
