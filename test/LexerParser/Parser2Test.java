package LexerParser;

import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class Parser2Test {

    @Test
    public void testArray () throws IOException {
        JToken jToken = parseJsonAndAssertOutput("[a,b,c]");
        assertEquals(createExpectedList("a","b","c"), jToken.getAsArray());
    }

    @Test
    public void testObject () throws IOException {
        JToken jToken = parseJsonAndAssertOutput("{a:b}");
        Map<String, JToken> expectedMap = new ExpectedMapBuilder().addNode("a", new JString("b")).buildMap();
        assertEquals(expectedMap, jToken.getAsMap());
    }

    @Test
    public void testObjectWithCommas () throws IOException {
        JToken jToken = parseJsonAndAssertOutput("{a:b,c:d,e:f}");
        Map<String, JToken> expectedMap =
                new ExpectedMapBuilder()
                        .addNode("a", new JString("b"))
                        .addNode("c", new JString("d"))
                        .addNode("e", new JString("f"))
                        .buildMap();
        Map<String, JToken> asMap = jToken.getAsMap();
        assertEquals(expectedMap, asMap);
        assertEquals(asMap.get("c"), new JString("d"));
    }

    @Test
    public void testArrayInObject () throws IOException {
        JToken jToken = parseJsonAndAssertOutput("{z:x,a:[b,c,d],1:2}");
        JArray jArray = new JArray(createExpectedList("b", "c", "d"));
        Map<String, JToken> expectedMap =
                new ExpectedMapBuilder()
                        .addNode("z", new JString("x"))
                        .addNode("a", jArray)
                        .addNode("1", new JString("2"))
                        .buildMap();
        Map<String, JToken> asMap = jToken.getAsMap();
        assertEquals(expectedMap, asMap);
        assertEquals(asMap.get("a"), jArray);
    }

    @Test
    public void testObjectInObject () throws IOException {
        JToken jToken = parseJsonAndAssertOutput("{a:{b:c}}");
    }

    @Test
    public void testObjectInArray () throws IOException {
        JToken jToken = parseJsonAndAssertOutput("[a,b,{c:d}]");
    }

    @Test
    public void testArrayInArray () throws IOException {
        JToken jToken = parseJsonAndAssertOutput("[a,b,[c,d]]");
    }

    @Test
    public void testObjectInArrayAndArrayInObject () throws IOException {
        JToken jToken = parseJsonAndAssertOutput("{parent:[a,b,{c:d,e:[x,y,z]},[p,q]],child:me}");
        JArray xyzArray = new JArray(createExpectedList("x", "y", "z"));

        Map<String, JToken> cdeObjectMap =
                new ExpectedMapBuilder()
                        .addNode("c", new JString("d"))
                        .addNode("e", xyzArray)
                        .buildMap();

        JArray pqArray = new JArray(createExpectedList("p", "q"));
        JObject cdeObject = new JObject(cdeObjectMap);
        JArray abArray = new JArray(createExpectedList("a", "b")).addToList(cdeObject).addToList(pqArray);


        Map<String, JToken> expectedParentMap =
                new ExpectedMapBuilder()
                        .addNode("parent", abArray)
                        .addNode("child", new JString("me"))
                        .buildMap();
        Map<String, JToken> asMap = jToken.getAsMap();
        assertEquals(expectedParentMap, asMap);
        assertEquals(asMap.get("parent"), abArray);
        assertEquals(new JString("b"), abArray.getAsArray().get(1));
        assertEquals(cdeObject, abArray.getAsArray().get(2));
        assertEquals(xyzArray, abArray.getAsArray().get(2).getAsMap().get("e"));
    }

    private JToken parseJsonAndAssertOutput(String json) throws IOException {
        Parser2 parser = new Parser2();
        JToken token = parser.parse(json);
        System.out.println(token);
        assertEquals(json, token.toString());
        return token;
    }

    private List<JToken> createExpectedList(String... strings) {
        List<JToken> expectedList = new ArrayList<>();

        for (String string : strings) {
            expectedList.add(new JString(string));
        }
        return expectedList;
    }

    class ExpectedMapBuilder {
        Map<String, JToken> expectedMap = new HashMap<>();

        ExpectedMapBuilder addNode(String key, JToken value){
            expectedMap.put(key,value);
            return this; //for chaining.
        }

        Map<String, JToken> buildMap(){
            return expectedMap;
        }

    }


    //TODO need a way to get the array bits out of this guy.
    // Then we can swap out GSON with our own parser.
    //TODO work on getting all the validation working.
    //TODO see if we can get it to tell us the position in the JSON string and what it expects where.


}