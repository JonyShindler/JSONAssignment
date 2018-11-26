package JSONParser;

import java.util.Map;

public class ThirdParser {

    public JObject parse(String json) {
        JObject jObject = new JObject();
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{') {
                jObject = parseObject(json.substring(i+1, json.length()));
            }

        }
        return jObject;
    }

    private JObject parseObject(String json) {
        StringBuffer stringBuffer = new StringBuffer();
        JObject jObject = new JObject();

        String key = "";
        JToken val;
        for (int i = 0; i < json.length(); i++){
            char c = json.charAt(i);
            if (c == ':') {
                //split
                key = stringBuffer.toString();
                stringBuffer.delete(0, stringBuffer.capacity());
            } else if (c == '}') {
                val = new JString(stringBuffer.toString());
                jObject.add(key, val);
                stringBuffer.delete(0, stringBuffer.capacity());
            } else {
                stringBuffer.append(c);
            }
        }

        return jObject;
    }

    private void splitAroundColonAndAddToMap(String string, Map<String, String> jsonMap) {
        string = string.replaceAll("\"", "");
        String[] split = string.split(":");
        //remove the speech marks from strings for now

        jsonMap.put(split[0], split[1]);
    }

    private JToken readObject(String substring) {
        return null;
    }

    private JToken readString(String substring) {
        //read forwards until we get to the next } and store everything inside the JObject

        return new JString(null);
    }

}
