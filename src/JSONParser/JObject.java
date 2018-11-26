package JSONParser;

import java.util.LinkedHashMap;
import java.util.Map;

public class JObject extends JToken {

    private Map<String, JToken> keysToValues = new LinkedHashMap<>();

    public boolean isHasChildren(){
        return !keysToValues.isEmpty();
    }

    public JObject add(String name, String value) {
        add(name, new JString(value));
        return this;
    }

    public JObject add(String name, JToken value) {
        keysToValues.put(name, value);
        return this;
    }

    @Override
    public boolean isObject() {
        return true;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append('{');
        int i=1;
        for (Map.Entry entry: keysToValues.entrySet()) {
            if (keysToValues.entrySet().size() != 1 && i !=1) {
                stringBuffer.append(',');
            }
            stringBuffer.append(entry.getKey() + "=" + entry.getValue());
            i++;
        }
        stringBuffer.append('}');
        return stringBuffer.toString();
    }
}
