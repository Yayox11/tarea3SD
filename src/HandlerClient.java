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

//Constructor del Thread
    public HandlerClient(DataInputStream dis, DataOutputStream dos, Paciente pacientes){
        this.colaReqPendientes = new LinkedList<JsonObject>();
        this.dis = dis;
        this.dos = dos;
        this.pacientes = pacientes;
    }

    //public static synchronized void getElement(){
       // System.out.println("id siendo utilizada: "+ numThreads);
    //}

    public static synchronized void editarPacientes(JsonObject procedimiento, String cargo){
        pacientes.editar_procedimiento(procedimiento, cargo);
    }

//Verifica si actualmente un paciente esta siendo utilizado
    public static synchronized Boolean editandoPaciente(String id){
        return lista_pacientes_editando.contains(id);
    }
    public static synchronized void removeEditando(String id){
        lista_pacientes_editando.remove(id);

    }
//Identifica si quedan requerimientos por satisfacer
    public Boolean quedanReq(){
        if (colaReqPendientes.size() > 0){
            return true;
        }

        else{
            return false;
        }
    }
//Escritura en el archivo local de los pacientes (para los cambios)
    public static synchronized void escribirArchivo(){
        pacientes.escribirServidor("/root/tarea3SD/src/json/pacientes.json");
    }

    //Envio de los cambios a los clientes
    public static synchronized void enviarCambios(){
        try {
            //Conexion a los sockets de los clientes
            Socket maquina54 = new Socket("10.6.40.194", 53001);
            Socket maquina55 =  new Socket("10.6.40.195", 53001);
            Socket maquina56 = new Socket("10.6.40.196", 53001);
            //Output streams hacia los clientes
            DataOutputStream o54 = new DataOutputStream( maquina54.getOutputStream());
            DataOutputStream o55 = new DataOutputStream( maquina55.getOutputStream());
            DataOutputStream o56 = new DataOutputStream( maquina56.getOutputStream());
            String cadena = "";
            for(int i = 0; i < pacientes.getPacientes().size(); i++){
                JsonObject pac = pacientes.getPacientes().get(i).getAsJsonObject();
                String pacString = pac.toString();
                cadena = cadena + pacString;
            }
            //System.out.println("Enviando datos: " + cadena);
            //Envio de los datos hacia los clientes
            o54.writeUTF(cadena);
            o55.writeUTF(cadena);
            o56.writeUTF(cadena);
            //Cierre de los sockets
            maquina54.close();
            maquina55.close();
            maquina56.close();


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
                //Obtencion del requrimiento
                JsonObject req = requerimientos.get(i).getAsJsonObject();
                String [] split = requerimiento.toString().split(" ");
                String id_pacienteEditar = split[0];
                //Verificar si el paciente esta siendo editado
                if(!editandoPaciente(id_pacienteEditar)){
                    lista_pacientes_editando.add(id_pacienteEditar);
                    editarPacientes(req, usuario.get("cargo").getAsString());
                    removeEditando(id_pacienteEditar);
                }
                else{
                    //Agrega a la cola de requerimientos pendientes
                    colaReqPendientes.add(req);
                }
            }
            //Verificación de que quedan requerimientos por satisfacer
            while(quedanReq()){
                JsonObject req = colaReqPendientes.element().getAsJsonObject();
                String[] split = req.toString().split(" ");
                String id_pacienteEditar = split[0];
                //Verifica que no se este ocupando
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
            //Envia los cambios a los clientes
            enviarCambios();
            //Escribe los cambios en el archivo local
            escribirArchivo();

            numThreads++;
        }

        catch (IOException e){
        }

    }
}
