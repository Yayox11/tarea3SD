import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Requerimientos {
    private JsonArray requerimientos;
    // Obtener el array de requerimientos
    public Requerimientos(JsonObject requerimientos) {
        this.requerimientos = requerimientos.getAsJsonArray("requerimientos");
    }
    // Retornar el array de requerimientos guardado
    public JsonArray obtener_requerimientos(){
        return this.requerimientos;
    }

}
