import com.google.gson.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Paciente {
    private JsonArray datos_pacientes;
    private int accesos = 0;
    // Poblar el array de la clase
    public Paciente(JsonArray datos_pacientes) {
        this.datos_pacientes = datos_pacientes;
    }
    // Retornar el dato de un paciente basado en el id
    public JsonObject datos_paciente(Integer id){
        return (JsonObject) this.datos_pacientes.get(id-1);
    }
    // Retornar el array de datos de los pacientes
    public JsonArray getPacientes(){
        return this.datos_pacientes;
    }

    // Segun el requerimiento de la forma {"1":"pedir tomografia"} se valida si es correcto
    // quien solicito el procedimiento y se agrega en el array de datos de clientes segun corresponda
    public String editar_procedimiento(JsonObject requerimiento, String cargo){
        for (Map.Entry<String, JsonElement> e : requerimiento.entrySet()) {
            Integer llave = Integer.parseInt(e.getKey());
            String accion = requerimiento.get(String.valueOf(llave)).getAsString();
            String[] partes = accion.split(" ");
            if (partes[0].equals("recetar") && cargo.equals("doctor")){
                JsonObject paciente = this.datos_paciente(llave);
                JsonPrimitive medicamento = new JsonPrimitive(partes[1]);
                ((JsonObject) paciente.getAsJsonArray("medicamentos").get(0)).getAsJsonArray("recetados").add(medicamento);
                }

                else if (partes[0].equals("pedir") && cargo.equals("doctor")){
                    JsonObject paciente = this.datos_paciente(llave);
                    JsonPrimitive examen = new JsonPrimitive(partes[1]);
                    ((JsonObject) paciente.getAsJsonArray("examenes").get(1)).getAsJsonArray("no realizados").add(examen);
                }

                else if (partes[0].equals("realizar") && (cargo.equals("doctor") || cargo.equals("paramedico"))){
                    JsonObject paciente = this.datos_paciente(llave);
                    JsonPrimitive examen = new JsonPrimitive(partes[1]);
                    ((JsonObject) paciente.getAsJsonArray("examenes").get(1)).getAsJsonArray("realizados").add(examen);
                }

                else if (partes[0].equals("colocar") && (cargo.equals("doctor") || cargo.equals("enfermero"))){
                    JsonObject paciente = this.datos_paciente(llave);
                    JsonPrimitive procedimiento = new JsonPrimitive(partes[1]);
                    ((JsonObject) paciente.getAsJsonArray("tratamientos/procedimientos").get(1)).getAsJsonArray("completados").add(procedimiento);
                }

                else if (partes[0].equals("asignar") && cargo.equals("doctor")){
                    JsonObject paciente = this.datos_paciente(llave);
                    JsonPrimitive procedimiento = new JsonPrimitive(partes[1]);
                    ((JsonObject) paciente.getAsJsonArray("tratamientos/procedimientos").get(1)).getAsJsonArray("asignados").add(procedimiento);
                }

                else if (partes[0].equals("suministrar") && (cargo.equals("doctor") || cargo.equals("enfermero"))){
                    JsonObject paciente = this.datos_paciente(llave);
                    JsonPrimitive medicamento = new JsonPrimitive(partes[1]);
                    ((JsonObject) paciente.getAsJsonArray("medicamentos").get(1)).getAsJsonArray("suministrados").add(medicamento);
                }
                else return "Persona no puede realizar ese procedimiento";
            }
        return "Ok";
    }
    // Escribir el archivo json con los datos de los pacientes
    public void escribir_pacientes(String path, JsonArray arreglo){
        try {
            Writer file = new FileWriter(path);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            this.datos_pacientes = arreglo;
            String json = gson.toJson(arreglo);
            file.write(json);
            file.close();
            System.out.println("Actualizacion correcta");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Funcion que escribe un archivo json con los datos de los pacientes a usar en el servidor
    public void escribirServidor(String path){
        try {
            Writer file = new FileWriter(path);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(this.datos_pacientes);
            file.write(json);
            file.close();
            System.out.println("Actualizacion correcta");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    // Funcion para ir creando y agregando en el archivo log los sucesos que van ocurriendo
    public void archivo_log(String info){
        Logger logger = Logger.getLogger("MyLog");
        FileHandler fh;
        if(this.accesos == 0){
            try {

                // This block configure the logger with handler and formatter
                fh = new FileHandler("/root/tarea3SD/archivo_log.log");
                logger.addHandler(fh);
                SimpleFormatter formatter = new SimpleFormatter();
                fh.setFormatter(formatter);

                // the following statement is used to log any messages
                logger.info("Apertura del Hospital");
                this.accesos = this.accesos + 1;

            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else logger.info(info);

    }

}
