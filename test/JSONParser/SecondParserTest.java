package JSONParser;

import org.junit.Test;

import java.util.Map;

public class SecondParserTest {

    @Test
    public void testThing(){
        String json = "{\"id\":\"s113867\",\"tasks\":\"/task/5447\"}";
        System.out.println(json);
        Map<String,String> map = new SecondParser().parse(json);
        System.out.println(map.toString());

    }

}