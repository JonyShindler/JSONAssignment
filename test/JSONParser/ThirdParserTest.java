package JSONParser;


import org.junit.Test;

public class ThirdParserTest {

    @Test
    public void testSingleJSOnObject(){
        String json = "{\"menu\":\"id\"}";
        System.out.println("Input JSON is: " + json);
        JObject map = new ThirdParser().parse(json);
        System.out.println("Map is: " + map.toString());

    }

}