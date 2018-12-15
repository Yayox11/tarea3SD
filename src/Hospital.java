import com.google.gson.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Hospital {
    private JsonArray doctores;
    private JsonArray enfermeros;
    private JsonArray paramedicos;
    private Integer anios_totales;

    public Hospital(JsonObject datos) {
        this.doctores = datos.getAsJsonArray("Doctor");
        this.enfermeros = datos.getAsJsonArray("enfermero");
        this.paramedicos = datos.getAsJsonArray("Paramedico");
        for (JsonElement i:this.doctores
             ) {
            JsonObject arreglo = i.getAsJsonObject();
            this.anios_totales += arreglo.get("experiencia").getAsInt();
            this.anios_totales += arreglo.get("estudios").getAsInt();
        }
    }
    

    public String obtener_datos(String trabajador, String elemento, Integer posicion){
        if (trabajador.equals("Doctor")){
            return ((JsonObject)this.doctores.get(posicion-1)).get(elemento).toString();
        }
        else if (trabajador.equals("enfermero")){
            return ((JsonObject)this.enfermeros.get(posicion-1)).get(elemento).toString();
        }
        else {
            return ((JsonObject)this.paramedicos.get(posicion-1)).get(elemento).toString();
        }
    }

    public Integer obtener_experiencia(){
        return this.anios_totales;
    }

    public void editar_procedimientos(JsonObject datos){

    }

    public void editar_remedios(JsonObject datos){

    }


    public static void main(String[] args){
        JsonParser parser = new JsonParser();
        Object object = null;
        try {
            object = parser.parse(new FileReader("C:\\Users\\Usuario\\Desktop\\Yayo\\2018-2\\Sistemas Distribuidos\\tarea_3\\src\\json\\test.json"));
            JsonObject jobject = (JsonObject) object;
            JsonArray arreglo = jobject.getAsJsonArray("Doctor");
            System.out.println(((JsonObject)arreglo.get(0)).get("id"));
            String message = jobject.get("Doctor").toString();
            System.out.println(message);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
