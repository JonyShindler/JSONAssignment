package LexerParser;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class Parser2Test {

    @Test
    public void testObject () throws IOException {
        JToken jToken = parseJsonAndAssertOutput("{\"a\":\"b\"}");
        Map<JString, JToken> expectedMap = new ExpectedMapBuilder().addNode(createJString("a"), createJString("b")).buildMap();
        assertEquals(expectedMap, jToken.getAsObject().getMap());
    }

    @Test
    public void testObjectWithCommas () throws IOException {
        JToken jToken = parseJsonAndAssertOutput("{\"a\":\"b\",\"c\":\"d\",\"e\":\"f\"}");
        Map<JString, JToken> expectedMap =
                new ExpectedMapBuilder()
                        .addNode(createJString("a"), createJString("b"))
                        .addNode(createJString("c"), createJString("d"))
                        .addNode(createJString("e"), createJString("f"))
                        .buildMap();
        Map<JString, JToken> asMap = jToken.getAsObject().getMap();
        assertEquals(expectedMap, asMap);
        assertEquals(asMap.get(createJString("c")), createJString("d"));
    }

    @Test
    public void testArrayInObject () throws IOException {
        JToken jToken = parseJsonAndAssertOutput("{\"z\":\"x\",\"a\":[\"b\",\"c\",\"d\"],\"numbers\":2}");
        JArray jArray = new JArray(createExpectedList("\"b\"", "\"c\"", "\"d\""));
        Map<JString, JToken> expectedMap =
                new ExpectedMapBuilder()
                        .addNode(createJString("z"), createJString("\"x\""))
                        .addNode(createJString("a"), jArray)
                        .addNode(createJString("numbers"), new JNumber("2"))
                        .buildMap();
        Map<JString, JToken> asMap = jToken.getAsObject().getMap();
        assertEquals(expectedMap, asMap);
        assertEquals(asMap.get(createJString("a")), jArray);
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
                        .addNode(createJString("c"), createJString("d"))
                        .addNode(createJString("e"), xyzArray)
                        .buildMap();

        JArray pqArray = new JArray(createExpectedList("p", "q"));
        JObject cdeObject = new JObject(cdeObjectMap);
        JArray abArray = new JArray(createExpectedList("a", "b")).addToList(cdeObject).addToList(pqArray);


        Map<JString, JToken> expectedParentMap =
                new ExpectedMapBuilder()
                        .addNode(createJString("parent"), abArray)
                        .addNode(createJString("child"), createJString("me"))
                        .buildMap();
        Map<JString, JToken> asMap = jToken.getAsObject().getMap();
        assertEquals(expectedParentMap, asMap);
        assertEquals(asMap.get(createJString("parent")), abArray);
        assertEquals(createJString("b"), abArray.getAsArray().get(1));
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

    //TODO add tests for the object verification.

    @Test
    public void testArrayWhichStartsWithComma(){
        try {
         new Parser2().parse("[,true,false]");
         fail();
         } catch (IOException e){
            assertEquals(illegalComma(1), e.getMessage());
        }
    }

    @Test
    public void testArrayWithTwoCommas() {
        try {
            new Parser2().parse("[true,,false]");
            fail();
        } catch (IOException e){
            assertEquals(illegalComma(6), e.getMessage());
        }
    }

    @Test
    public void testDoubleArrayWithNoCommas() {
        try {
            new Parser2().parse("[[true][false]]");
            fail();
        } catch (IOException e){
            assertEquals(expectedComma(7), e.getMessage());
        }
    }

    @Test
    public void testArrayWithCommaAtEnd() {
        try {
            new Parser2().parse("[true,false,]");
            fail();
        } catch (IOException e){
            assertEquals(illegalComma(11), e.getMessage());
        }
    }

    @Test
    public void testArrayWithNoCommaBetweenStrings() {
        try {
            new Parser2().parse("[\"abc\"\"bob\"]");
            fail();
        } catch (IOException e){
            assertEquals(expectedComma(6), e.getMessage());
        }
    }

    @Test
    public void testArrayWithObjectsWithNoCommas() {
        try {
            new Parser2().parse("[{\"a\":true}{\"c\":false}]");
            fail();
        } catch (IOException e){
            assertEquals(expectedComma(11), e.getMessage());
        }
    }

    @Test
    public void testArrayWithColon() {
        try {
            new Parser2().parse("[true,false:null]");
            fail();
        } catch (IOException e){
            assertEquals(unexpectedCharacter(11, ":"), e.getMessage());
        }
    }

    @Test
    public void testArrayWithRandomClosedObjectBracket() {
        try {
            new Parser2().parse("[true,false}]");
            fail();
        } catch (IOException e){
            assertEquals(unexpectedCharacter(11, "}"), e.getMessage());
        }
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
        assertEquals(expectedToString, token.toString());
        return token;
    }

    private List<JToken> createExpectedList(String... strings) throws IOException {
        List<JToken> expectedList = new ArrayList<>();

        for (String string : strings) {
            expectedList.add(createJString(string));
        }
        return expectedList;
    }

    private String expectedComma(int startChar){
        return Parser2.EXPECTED_COMMA + "at position " + startChar;
    }

    private String illegalComma(int startChar){
        return Parser2.ILLEGAL_COMMA + "at position " + startChar;
    }

    private String unexpectedCharacter(int startChar, String unexpectedString){
        return Parser2.UNEXPECTED_CHARACTER + unexpectedString + " at position " + startChar;
    }

    private JString createJString(String string) throws IOException {
        return new JString(string);
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
}