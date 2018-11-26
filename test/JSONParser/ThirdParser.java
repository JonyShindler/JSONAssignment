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
        JToken childJObject = null;

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
            }  else if (c == '[') {
                //this is an array.
                i++;
                childJObject = parseArray(json, i);
                i = childJObject.getEndChar();
                jObject.add(key, childJObject);
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

    //An array is something which contains other objects...
    private JArray parseArray(String json, int startChar) {
        JArray jArray = new JArray();
        for (int i = startChar; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{') {
                JObject obj = parseObject(json, i+1);
                jArray.add(obj);
                i = obj.getEndChar();
            } else if (c==']') {
                jArray.setEndChar(i);
            }
        }
        return jArray;
    }
}
