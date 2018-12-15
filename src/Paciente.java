import com.google.gson.*;

public class Paciente {
    private JsonArray datos_pacientes;

    public Paciente(JsonArray datos_pacientes) {
        this.datos_pacientes = datos_pacientes;
    }

    public JsonObject datos_paciente(Integer id){
        return (JsonObject) this.datos_pacientes.get(id-1);
    }

}
