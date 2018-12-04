package JSONParser;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OldOldJObject extends OldJToken {

    private Map<String, OldJToken> keysToValues = new LinkedHashMap<>();

    public OldOldJObject(String key1, OldJToken token1, String key2, OldJToken token2) {
        keysToValues.put(key1,token1);
        keysToValues.put(key2,token2);
    }

    public OldOldJObject(String key1, OldJToken token1) {
        keysToValues.put(key1,token1);
    }

    public OldOldJObject() {
    }

    public boolean isHasChildren(){
        return !keysToValues.isEmpty();
    }

    public OldJToken get(String key){
        return keysToValues.get(key);
    }

    @Override
    public List<OldJToken> getAsArray(String key) {
       OldJArray array = (OldJArray) keysToValues.get(key);
       return array.getArray();
    }

    public OldOldJObject add(String name, String value) {
        add(name, new OldJString(value));
        return this;
    }

    public OldOldJObject add(String name, OldJToken value) {
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
        OldOldJObject oldJObject = (OldOldJObject) o;
        return Objects.equals(keysToValues, oldJObject.keysToValues);
    }

    @Override
    public int hashCode() {

        return Objects.hash(keysToValues);
    }
}
