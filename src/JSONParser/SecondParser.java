package JSONParser;

import java.util.*;

public class SecondParser {
    public Map<String, String> parse(String json) {

        List<String> splitStrings = new ArrayList<>();

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
                splitStrings.add(stringBuffer.toString());
                stringBuffer.delete(0, stringBuffer.capacity());
            } else {
                stringBuffer.append(c);
            }
        }
        splitStrings.add(stringBuffer.toString());


        // now put them in the map by splutting them again
        Map<String, String> jsonMap = new TreeMap<>();

        for (String string : splitStrings) {
            string = string.replaceAll("\"", "");
            String[] split = string.split(":");
            //remove the speech marks from strings for now

            jsonMap.put(split[0], split[1]);
        }

//TODO we need to handle objects inside objects. each will get its own map


        return jsonMap;
    }
}
