package JSONParser;

public class ThirdParser {

    public OldOldJObject parse(String json) {
        OldOldJObject oldJObject = new OldOldJObject();
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{') {
                oldJObject = parseObject(json,1);
            }

        }
        return oldJObject;
    }

    // this method needs to create a OldOldJObject from a string. so it needs to make the key and the value.
    private OldOldJObject parseObject(String json, int startChar) {
        StringBuffer stringBuffer = new StringBuffer();
        OldOldJObject oldJObject = new OldOldJObject();
        OldJToken childJObject = null;

        String key = "";
        OldJToken val;
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
                oldJObject.add(key, childJObject);
            } else if (c == '}') {
                if (childJObject!=null){
                    val = childJObject;
                } else {
                    val = new OldJString(stringBuffer.toString());
                }
                OldOldJObject childObject = oldJObject.add(key, val);
                childObject.setEndChar(i);
                return childObject;
            }  else if (c == '[') {
                //this is an array.
                i++;
                childJObject = parseArray(json, i);
                i = childJObject.getEndChar();
                oldJObject.add(key, childJObject);
            } else if (c==','){
                //If we just processed a child object, then we should do anything.
                if (childJObject!=null){
                    childJObject=null;
                    continue;
                } else {
                    val = new OldJString(stringBuffer.toString());
                }
                oldJObject.add(key, val);
                stringBuffer.delete(0, stringBuffer.capacity());
            }
            else {
                stringBuffer.append(c);
            }
        }

        return oldJObject;
    }

    //An array is something which contains other objects...
    private OldJArray parseArray(String json, int startChar) {
        OldJArray jArray = new OldJArray();
        for (int i = startChar; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{') {
                OldOldJObject obj = parseObject(json, i+1);
                jArray.add(obj);
                i = obj.getEndChar();
            } else if (c==']') {
                jArray.setEndChar(i);
            }
        }
        return jArray;
    }
}
