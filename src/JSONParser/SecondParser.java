package JSONParser;

import java.util.*;

public class SecondParser {

    //make a method that given a string, it will extract the objects and add them as children?






    public String parse(String json) {

        //TODO looks like we overwrote it, woopsie.
        StringBuffer stringBuffer = new StringBuffer();
        OlderJSONObject workingJSONObject = new OlderJSONObject();
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{') {
                //if there was already a parent, use that, otherwise add this as a child.
                if (workingJSONObject.getParentObject() == null) {
                    //this must the root node
                    // do nothing
                } else {
                    // this is a child node. so create a new OlderJSONObject and set its parent as the other one.
                    OlderJSONObject childObject = new OlderJSONObject();
                    workingJSONObject.setChildObject(childObject);
                    workingJSONObject = childObject;
                }
                workingJSONObject.setStartChar(i);
            } else if (c == ':'){
                //the string buffer was the key. now lets do the node bit.
                workingJSONObject.setKey(stringBuffer.toString());
                stringBuffer.delete(0, stringBuffer.capacity());

            } else if (c == '}'){
                // go till the end of the object.
                workingJSONObject.setEndChar(i);
                workingJSONObject.setNode(stringBuffer.toString());
                stringBuffer.delete(0, stringBuffer.capacity());
                System.out.println(workingJSONObject.toString());
                //TODO if there are more chars to go, i.e. a new object, then we need to add that as the key...

            } else {
                stringBuffer.append(c);
            }

            //create a JSON object to hold that info for now. later on we can create more JSON objects inside it.
        }

        return workingJSONObject.toString();

        //everything between { and the next : is the key. everything between : and } is the node.
        // so lets try and implement that at least.

//TODO we need to handle objects inside objects. each will get its own map
//TODO we might want to have wrapper objects for the arrays and stuff so they can be iterated over.

    }

    public Map<String, String> processObject(String json) {
        Map<String, String> jsonMap = new TreeMap<>();

        //TODO this all works fine per object. but we need to do this process per object.
        // TODO and each time we get a new object we need to do it again.
        boolean insideArray = false;
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < json.length(); i++){
            char c = json.charAt(i);
            //Dont split on commas if we are inside an array
            if (c=='[') {
                insideArray = true;
            } else if (c==']') {
                insideArray = false;
            } else if (!insideArray && c == ',') {
                //Do a split
                splitAroundColonAndAddToMap(stringBuffer.toString(), jsonMap);
                stringBuffer.delete(0, stringBuffer.capacity());
            } else if (c == '}') {
                //Do a split
                splitAroundColonAndAddToMap(stringBuffer.toString(), jsonMap);
                stringBuffer.delete(0, stringBuffer.capacity());
            } else {
                stringBuffer.append(c);
            }
        }
        return jsonMap;
    }

    private void splitAroundColonAndAddToMap(String string, Map<String, String> jsonMap) {
            string = string.replaceAll("\"", "");
            String[] split = string.split(":");
            //remove the speech marks from strings for now

            jsonMap.put(split[0], split[1]);
    }
}
