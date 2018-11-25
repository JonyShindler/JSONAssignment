package JSONParser;

import org.junit.Test;

import java.util.Map;

public class SecondParserTest {

    @Test
    public void testSuperBasicCase(){
        String json = "{\"id\":\"s113867\",\"task\":\"/task/5447\"}";
        System.out.println("Input JSON is: " + json);
        Map<String,String> map = new SecondParser().parse(json);
        System.out.println("Map is: " + map.toString());

    }

    @Test
    public void testArray(){
        String json = "{\"id\":\"s113867\",\"tasks\":[\"/task/5447\",\"/task/2867\",\"/task/8060\"]}";
        System.out.println("Input JSON is: " + json);
        Map<String,String> map = new SecondParser().parse(json);
        System.out.println("Map is: " + map.toString());

    }

}