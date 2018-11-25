package JSONParser;

import java.util.*;

public class SecondParser {
    public Map<String, String> parse(String json) {

        String[] splitStrings = json.split(",");
        List<String> strings = new ArrayList<>();

        for (String aString : Arrays.asList(splitStrings)) {
            System.out.println("String is " + aString);
            strings.add(aString);
        }

        // now put them in the map by splutting them again
        Map<String, String> jsonMap = new HashMap<>();

        for (String string : strings) {
            //TODO dont split on commas if we are inside an array?
            string = string.replaceAll("\"", "");
            String[] split = string.split(":");
            //remove the speech marks from strings for now

//            jsonMap.put(split[0], split[1]);
        }




        return jsonMap;
    }
}
