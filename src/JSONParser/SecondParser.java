package JSONParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecondParser {
    public Map<String, String> parse(String json) {

        String[] splitStrings = json.split(",");
        String string1 = splitStrings[0];
        String string2 = splitStrings[1];
        System.out.println("String1 is " +string1);
        System.out.println("String2 is " +string2);
        string1 = string1.replaceAll("\\{", "");
        string2 = string2.replaceAll("\\}", "");

        List<String> strings = new ArrayList<>();
        strings.add(string1);
        strings.add(string2);

        // now put them in the map by splutting them again
        Map<String, String> jsonMap = new HashMap<>();

        for (String string : strings) {
            String[] split = string.split(":");
            jsonMap.put(split[0], split[1]);
        }



        return jsonMap;
    }
}
