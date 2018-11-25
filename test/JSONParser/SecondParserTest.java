package JSONParser;

import org.junit.Test;

import java.util.Map;

public class SecondParserTest {

    @Test
    public void testThing(){
        String json = "{\"id\":\"s113867\",\"tasks\":\"/task/5447\"}";
        Map<String,String> map = new SecondParser().parse(json);
        System.out.print(map.toString());

    }

}