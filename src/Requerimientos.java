import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Requerimientos {
    private JsonArray requerimientos;

    public Requerimientos(JsonObject requerimientos) {
        this.requerimientos = requerimientos.getAsJsonArray("requerimientos");
    }

    public JsonArray obtener_requerimientos(){
        return this.requerimientos;
    }

}
