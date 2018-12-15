import com.google.gson.*;

import java.util.Map;

public class Paciente {
    private JsonArray datos_pacientes;

    public Paciente(JsonArray datos_pacientes) {
        this.datos_pacientes = datos_pacientes;
    }

    public JsonObject datos_paciente(Integer id){
        return (JsonObject) this.datos_pacientes.get(id-1);
    }

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

    public void requerimiento_externo(int llave, String accion, String cargo){
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

    }

}
