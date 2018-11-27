package JSONParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JArray extends JToken{

    private List<JToken> jObjects = new ArrayList<>();

//TODO Arrays can contain objects, strings, numbers, true,faalse or another array....

    public JArray add(JObject value) {
        jObjects.add(value);
        return this;
    }

    public List<JToken> getArray() {
        return jObjects;
    }

    @Override
    public boolean isArray() {
        return true;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append('[');
        int i = 1;
        for (JToken jObject : jObjects) {
            if (jObjects.size() != 1 && i !=1) {
                stringBuffer.append(',');
            }
            stringBuffer.append(jObject.toString());
            i++;
        }
        stringBuffer.append(']');
        return stringBuffer.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JArray jArray = (JArray) o;
        return Objects.equals(jObjects, jArray.jObjects);
    }

    @Override
    public int hashCode() {

        return Objects.hash(jObjects);
    }
}
