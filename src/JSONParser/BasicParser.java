package JSONParser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class BasicParser {

    public JsonObject parse (String jsonInput){

        return new JsonParser().parse(jsonInput).getAsJsonObject();
    }

}
