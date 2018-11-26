package JSONParser;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ThirdParserTest {

    @Test
    public void testSingleJSOnObject(){
        String json = "{menu:id}";
        System.out.println("Input JSON is: " + json);
        JObject map = new ThirdParser().parse(json);
        System.out.println("Map is: " + map.toString());
        assertEquals("{menu=id}", map.toString());
    }

    @Test
    public void testSingleJSOnObjectWithTwoPairs(){
        String json = "{menu:id,price:10}";
        System.out.println("Input JSON is: " + json);
        JObject map = new ThirdParser().parse(json);
        System.out.println("Map is: " + map.toString());
        assertEquals("{menu=id,price=10}", map.toString());
    }

    @Test
    public void testSingleJSOnObjectWithThreePairs(){
        String json = "{menu:id,price:10,tasty:yes}";
        System.out.println("Input JSON is: " + json);
        JObject map = new ThirdParser().parse(json);
        System.out.println("Map is: " + map.toString());
        assertEquals("{menu=id,price=10,tasty=yes}", map.toString());
    }

    @Test
    public void testNestedJSOnObject(){
        String json = "{a:{id:b}}";
        System.out.println("Input JSON is: " + json);
        JObject map = new ThirdParser().parse(json);
        System.out.println("Map is: " + map.toString());
        assertEquals("{a={id=b}}", map.toString());
    }

    @Test
    public void testTwoNestedJSOnObject(){
        String json = "{a:{id:{di:b}}}";
        System.out.println("Input JSON is: " + json);
        JObject map = new ThirdParser().parse(json);
        System.out.println("Map is: " + map.toString());
        assertEquals("{a={id={di=b}}}", map.toString());

    }

    @Test
    public void testThreeNestedJSOnObject(){
        String json = "{a:{b:{c:{d:e}}}}";
        System.out.println("Input JSON is: " + json);
        JObject map = new ThirdParser().parse(json);
        System.out.println("Map is: " + map.toString());
        assertEquals("{a={b={c={d=e}}}}", map.toString());
    }

    @Test
    public void testTwoNestedJSOnObjectsWithOtherStringsAtTheBeginning(){
        String json = "{tasty:yes,menu:{food:cake}}";
        System.out.println("Input JSON is: " + json);
        JObject map = new ThirdParser().parse(json);
        System.out.println("Map is: " + map.toString());
        assertEquals("{tasty=yes,menu={food=cake}}", map.toString());
    }

    @Test
    public void testTwoNestedJSOnObjectsWithOtherStringsAtTheEnd(){
        String json = "{menu:{food:cake},tasty:yes}";
        System.out.println("Input JSON is: " + json);
        JObject map = new ThirdParser().parse(json);
        System.out.println("Map is: " + map.toString());
        assertEquals("{menu={food=cake},tasty=yes}", map.toString());
    }

}