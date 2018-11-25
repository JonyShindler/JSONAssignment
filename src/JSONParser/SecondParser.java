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
                System.out.println("Its an array");
            } else if (c==']') {
                insideArray = false;
                System.out.println("Array over");
            } else if (!insideArray && c == ',') {
                //Do a split
                splitStrings.add(stringBuffer.toString());
                stringBuffer.delete(0, stringBuffer.capacity());
            } else {
                stringBuffer.append(c);
            }
            //Process char
        }
        splitStrings.add(stringBuffer.toString());


//        String[] splitStrings = json.split(",");
        List<String> strings = new ArrayList<>();

        for (String aString : splitStrings) {
            System.out.println("String is " + aString);
            strings.add(aString);
        }

        // now put them in the map by splutting them again
        Map<String, String> jsonMap = new HashMap<>();

        for (String string : strings) {
            string = string.replaceAll("\"", "");
            String[] split = string.split(":");
            //remove the speech marks from strings for now

            jsonMap.put(split[0], split[1]);
        }




        return jsonMap;
    }
}
