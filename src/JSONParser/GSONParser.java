package JSONParser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GSONParser {

    public JsonObject parse (String jsonInput){

        return new JsonParser().parse(jsonInput).getAsJsonObject();
    }

}
