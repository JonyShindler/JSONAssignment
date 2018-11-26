package JSONParser;

import java.util.HashMap;
import java.util.Map;

public class JObject extends JToken {

    private Map<String, JToken> keysToValues = new HashMap<>();

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
        return keysToValues.toString();
    }
}
