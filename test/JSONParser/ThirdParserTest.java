package JSONParser;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ThirdParserTest {

    @Test
    public void testSingleJSOnObject(){
        String json = "{menu:id}";
        System.out.println("Input JSON is: " + json);
        OldOldJObject map = new ThirdParser().parse(json);
        System.out.println("Map is: " + map.toString());
        assertEquals("{menu=id}", map.toString());
    }

    @Test
    public void testSingleJSOnObjectWithTwoPairs(){
        String json = "{menu:id,price:10}";
        System.out.println("Input JSON is: " + json);
        OldOldJObject map = new ThirdParser().parse(json);
        System.out.println("Map is: " + map.toString());
        assertEquals("{menu=id,price=10}", map.toString());
    }

    @Test
    public void testSingleJSOnObjectWithThreePairs(){
        String json = "{menu:id,price:10,tasty:yes}";
        System.out.println("Input JSON is: " + json);
        OldOldJObject map = new ThirdParser().parse(json);
        System.out.println("Map is: " + map.toString());
        assertEquals("{menu=id,price=10,tasty=yes}", map.toString());
    }

    @Test
    public void testNestedJSOnObject(){
        String json = "{a:{id:b}}";
        System.out.println("Input JSON is: " + json);
        OldOldJObject map = new ThirdParser().parse(json);
        System.out.println("Map is: " + map.toString());
        assertEquals("{a={id=b}}", map.toString());
    }

    @Test
    public void testTwoNestedJSOnObject(){
        String json = "{a:{id:{di:b}}}";
        System.out.println("Input JSON is: " + json);
        OldOldJObject map = new ThirdParser().parse(json);
        System.out.println("Map is: " + map.toString());
        assertEquals("{a={id={di=b}}}", map.toString());

    }

    @Test
    public void testThreeNestedJSOnObject(){
        String json = "{a:{b:{c:{d:e}}}}";
        System.out.println("Input JSON is: " + json);
        OldOldJObject map = new ThirdParser().parse(json);
        System.out.println("Map is: " + map.toString());
        assertEquals("{a={b={c={d=e}}}}", map.toString());
    }

    @Test
    public void testTwoNestedJSOnObjectsWithOtherStringsAtTheBeginning(){
        String json = "{tasty:yes,menu:{food:cake}}";
        System.out.println("Input JSON is: " + json);
        OldOldJObject map = new ThirdParser().parse(json);
        System.out.println("Map is: " + map.toString());
        assertEquals("{tasty=yes,menu={food=cake}}", map.toString());
    }

    @Test
    public void testTwoNestedJSOnObjectsWithOtherStringsAtTheEnd(){
        String json = "{menu:{food:cake},tasty:yes}";
        System.out.println("Input JSON is: " + json);
        OldOldJObject map = new ThirdParser().parse(json);
        System.out.println("Map is: " + map.toString());
        assertEquals("{menu={food=cake},tasty=yes}", map.toString());
    }

    @Test
    public void testTwoNestedJSOnObjectsWithOtherStringsAtTheEndAndBeginning(){
        String json = "{price:10,menu:{food:cake},tasty:yes}";
        System.out.println("Input JSON is: " + json);
        OldOldJObject map = new ThirdParser().parse(json);
        System.out.println("Map is: " + map.toString());
        assertEquals("{price=10,menu={food=cake},tasty=yes}", map.toString());
    }

    @Test
    public void testTwoNestedJSOnObjectsWithOtherStringsOnChildAndParent(){
        String json = "{price:10,menu:{food:cake,sugar:lots},tasty:yes}";
        System.out.println("Input JSON is: " + json);
        OldOldJObject map = new ThirdParser().parse(json);
        System.out.println("Map is: " + map.toString());
        assertEquals("{price=10,menu={food=cake,sugar=lots},tasty=yes}", map.toString());

        assertEquals(new OldJString("10"),map.get("price"));
        assertEquals(new OldJString("yes"),map.get("tasty"));

        OldOldJObject menu = new OldOldJObject();
        menu.add("food", new OldJString("cake"));
        menu.add("sugar", new OldJString("lots"));

        assertEquals(menu,map.get("menu"));
        assertEquals(new OldJString("cake"), menu.get("food"));
        assertEquals(new OldJString("lots"), menu.get("sugar"));
    }


    @Test
    public void testThreeNestedJSOnObjectsWithOtherStringsOnChildAndParent(){
        String json = "{price:10,menu:{food:cake,ingredients:{sugar:lots,veg:none}},tasty:yes}";
        System.out.println("Input JSON is: " + json);
        OldOldJObject map = new ThirdParser().parse(json);
        System.out.println("Map is: " + map.toString());
        assertEquals("{price=10,menu={food=cake,ingredients={sugar=lots,veg=none}},tasty=yes}", map.toString());
    }

    @Test
    public void testSimpleArray(){
        String json = "{menu:[{food:cake},{food:bun}]}";
        System.out.println("Input JSON is: " + json);
        OldOldJObject map = new ThirdParser().parse(json);
        System.out.println("Map is: " + map.toString());
        assertEquals("{menu=[{food=cake},{food=bun}]}", map.toString());
    }

    @Test
    public void testSimpleArrayWithOtherStrings(){
        String json = "{price:10,menu:[{food:cake},{food:bun}],tasty:yes}";
        System.out.println("Input JSON is: " + json);
        OldOldJObject map = new ThirdParser().parse(json);
        System.out.println("Map is: " + map.toString());
        assertEquals("{price=10,menu=[{food=cake},{food=bun}],tasty=yes}", map.toString());

        List<OldJToken> oldJToken = map.getAsArray("menu");
        List<OldJToken> expectedList = new ArrayList<>();
        expectedList.add(new OldOldJObject("food", new OldJString("cake")));
        expectedList.add(new OldOldJObject("food", new OldJString("bun")));
        assertEquals(expectedList, oldJToken);
    }

    @Test
    public void testSimpleArrayWithMultipleStringsInEachObject(){
        String json = "{price:10,menu:[{food:cake,calories:lots},{food:bun,calories:low}],tasty:yes}";
        System.out.println("Input JSON is: " + json);
        OldOldJObject map = new ThirdParser().parse(json);
        System.out.println("Map is: " + map.toString());
        assertEquals("{price=10,menu=[{food=cake,calories=lots},{food=bun,calories=low}],tasty=yes}", map.toString());


    }


}