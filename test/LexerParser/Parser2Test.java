package LexerParser;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class Parser2Test {

    @Test
    public void testObject () throws IOException {
        JToken jToken = parseJsonAndAssertOutput("{\"a\":\"b\"}");
        Map<JString, JToken> expectedMap = new ExpectedMapBuilder().addNode(new JString("a"), new JString("b")).buildMap();
        assertEquals(expectedMap, jToken.getAsObject().getMap());
    }

    @Test
    public void testObjectWithCommas () throws IOException {
        JToken jToken = parseJsonAndAssertOutput("{\"a\":\"b\",\"c\":\"d\",\"e\":\"f\"}");
        Map<JString, JToken> expectedMap =
                new ExpectedMapBuilder()
                        .addNode(new JString("a"), new JString("b"))
                        .addNode(new JString("c"), new JString("d"))
                        .addNode(new JString("e"), new JString("f"))
                        .buildMap();
        Map<JString, JToken> asMap = jToken.getAsObject().getMap();
        assertEquals(expectedMap, asMap);
        assertEquals(asMap.get(new JString("c")), new JString("d"));
    }

    @Test
    public void testArrayInObject () throws IOException {
        JToken jToken = parseJsonAndAssertOutput("{\"z\":\"x\",\"a\":[\"b\",\"c\",\"d\"],\"numbers\":2}");
        JArray jArray = new JArray(createExpectedList("\"b\"", "\"c\"", "\"d\""));
        Map<JString, JToken> expectedMap =
                new ExpectedMapBuilder()
                        .addNode(new JString("z"), new JString("\"x\""))
                        .addNode(new JString("a"), jArray)
                        .addNode(new JString("numbers"), new JNumber("2"))
                        .buildMap();
        Map<JString, JToken> asMap = jToken.getAsObject().getMap();
        assertEquals(expectedMap, asMap);
        assertEquals(asMap.get(new JString("a")), jArray);
    }

    @Test
    public void testObjectInObject () throws IOException {
        parseJsonAndAssertOutput("{\"a\":{\"b\":\"c\"}}");
    }

    @Test
    public void testObjectInArray () throws IOException {
        parseJsonAndAssertOutput("{\"array\":[\"a\",\"b\",{\"c\":\"d\"}]}");
    }

    @Test
    public void testArrayInArray () throws IOException {
        parseJsonAndAssertOutput("{\"array\":[\"a\",\"b\",[\"c\",\"d\"]]}");
    }

    @Test
    public void testTrueFalseNullInArray () throws IOException {
        parseJsonAndAssertOutput("[true, false, null, 107, \"Bob\"]", "[true,false,null,107,\"Bob\"]");
    }

    @Test
    public void testObjectInArrayAndArrayInObject () throws IOException {
        JToken jToken = parseJsonAndAssertOutput("{\"parent\":[\"a\",\"b\",{\"c\":\"d\",\"e\":[\"x\",\"y\",\"z\"]},[\"p\",\"q\"]],\"child\":\"me\"}");
        JArray xyzArray = new JArray(createExpectedList("\"x\"", "\"y\"", "\"z\""));

        Map<JString, JToken> cdeObjectMap =
                new ExpectedMapBuilder()
                        .addNode(new JString("c"), new JString("\"d\""))
                        .addNode(new JString("e"), xyzArray)
                        .buildMap();

        JArray pqArray = new JArray(createExpectedList("p", "q"));
        JObject cdeObject = new JObject(cdeObjectMap);
        JArray abArray = new JArray(createExpectedList("a", "b")).addToList(cdeObject).addToList(pqArray);


        Map<JString, JToken> expectedParentMap =
                new ExpectedMapBuilder()
                        .addNode(new JString("parent"), abArray)
                        .addNode(new JString("child"), new JString("me"))
                        .buildMap();
        Map<JString, JToken> asMap = jToken.getAsObject().getMap();
        assertEquals(expectedParentMap, asMap);
        assertEquals(asMap.get(new JString("parent")), abArray);
        assertEquals(new JString("b"), abArray.getAsArray().get(1));
        assertEquals(cdeObject, abArray.getAsArray().get(2));
        assertEquals(xyzArray, abArray.getAsArray().get(2).getAsObject().get("e"));
    }

    @Test
    public void testArrayWithQuote () throws IOException {
        parseJsonAndAssertOutput("[\"a\"]");
    }

    @Test
    public void testArrayWithQuotes () throws IOException {
        parseJsonAndAssertOutput("[\"a\",\"b\"]");
    }

    @Test
    public void testActualTask () throws IOException {
        String inputJSON = "{\"instruction\":\"add\",\"parameters\":[3979, 1990],\"response URL\":\"/answer/3070\"}";
        String expectedToString = "{\"instruction\":\"add\",\"parameters\":[3979,1990],\"response URL\":\"/answer/3070\"}";
        parseJsonAndAssertOutput(inputJSON, expectedToString);
    }


    private JToken parseJsonAndAssertOutput(String json) throws IOException {
        return getAndAssertJToken(json, json);
    }

    private JToken parseJsonAndAssertOutput(String json, String expectedToString) throws IOException {
        return getAndAssertJToken(json, expectedToString);
    }

    private JToken getAndAssertJToken(String json, String expectedToString) throws IOException
    {
        Parser2 parser = new Parser2();
        JToken token = parser.parse(json);
        System.out.println(token);
        assertEquals(expectedToString, token.toString());
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
        Map<JString, JToken> expectedMap = new HashMap<>();

        ExpectedMapBuilder addNode(JString key, JToken value){
            expectedMap.put(key,value);
            return this; //for chaining.
        }

        Map<JString, JToken> buildMap(){
            return expectedMap;
        }

    }


    //TODO need a way to get the array bits out of this guy.
    // Then we can swap out GSON with our own parser.
    //TODO work on getting all the validation working.
    //TODO see if we can get it to tell us the position in the JSON string and what it expects where.


}