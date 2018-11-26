package JSONParser;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class JObject extends JToken {

    private Map<String, JToken> keysToValues = new LinkedHashMap<>();

    public boolean isHasChildren(){
        return !keysToValues.isEmpty();
    }

    public JToken get(String key){
        return keysToValues.get(key);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JObject jObject = (JObject) o;
        return Objects.equals(keysToValues, jObject.keysToValues);
    }

    @Override
    public int hashCode() {

        return Objects.hash(keysToValues);
    }
}
