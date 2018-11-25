package JSONParser;


import com.google.gson.JsonObject;
import org.junit.Test;

public class GSONParserTest {

    @Test
    public void testThing(){
        JsonObject map = new GSONParser().parse("{\"id\":\"s113867\",\"tasks\":\"/task/5447\"}");
        System.out.print(map.toString());

    }

}