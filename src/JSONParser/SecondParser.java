package JSONParser;

import java.util.*;

public class SecondParser {
    public Map<String, String> parse(String json) {

        Map<String, String> jsonMap = new TreeMap<>();

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

//TODO we need to handle objects inside objects. each will get its own map
//TODO we might want to have wrapper objects for the arrays and stuff so they can be iterated over.

        return jsonMap;
    }

    private void splitAroundColonAndAddToMap(String string, Map<String, String> jsonMap) {
            string = string.replaceAll("\"", "");
            String[] split = string.split(":");
            //remove the speech marks from strings for now

            jsonMap.put(split[0], split[1]);
    }
}
