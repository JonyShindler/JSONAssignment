package JSONParser;

import java.util.Map;

public class ThirdParser {

    public JObject parse(String json) {
        JObject jObject = new JObject();
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{') {
                jObject = parseObject(json,1);
            }

        }
        return jObject;
    }

    // this method needs to create a JObject from a string. so it needs to make the key and the value.
    private JObject parseObject(String json, int startChar) {
        StringBuffer stringBuffer = new StringBuffer();
        JObject jObject = new JObject();
        JObject childJObject = null;

        String key = "";
        JToken val;
        //TODO set start chars
        for (int i = startChar; i < json.length(); i++){
            char c = json.charAt(i);
            if (c == ':') {
                //split
                key = stringBuffer.toString();
                stringBuffer.delete(0, stringBuffer.capacity());
            } else if (c == '{') {
                //Go back up and recursively call the parseObject method
                i++;
                childJObject = parseObject(json, i);
                //When we have parsed a child object we need to update i so it doesnt carry on.
                i = childJObject.getEndChar();
                jObject.add(key, childJObject);
            } else if (c == '}') {
                if (childJObject!=null){
                    val = childJObject;
                } else {
                    val = new JString(stringBuffer.toString());
                }
                JObject childObject = jObject.add(key, val);
                childObject.setEndChar(i);
                return childObject;
            } else if (c==','){
                //If we just processed a child object, then we should do anything.
                if (childJObject!=null){
                    childJObject=null;
                    continue;
                } else {
                    val = new JString(stringBuffer.toString());
                }
                jObject.add(key, val);
                stringBuffer.delete(0, stringBuffer.capacity());
            }
            else {
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
