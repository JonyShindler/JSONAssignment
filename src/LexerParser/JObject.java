package LexerParser;

import java.util.*;

public class JObject extends JToken {

    private List<JString> keys = new ArrayList<>();
    private List<JToken> values = new ArrayList<>();
    private Map<JString, JToken> map = new HashMap<>();

    public void addValue(JToken jToken) {
        values.add(jToken);
    }

    public void addKey(JString key) {
        keys.add(key);
    }

    public JObject(Map<JString, JToken> map) {
        this.map = map;
        this.keys = new ArrayList<>(map.keySet());
        this.values = new ArrayList<>(map.values());
    }

    public JObject() {
    }

    public JToken get(String key){
        return map.get(new JString(key));
    }

    //TODO add iterable method on this

    public Map<JString, JToken> getMap(){
        return map;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append('{');
        if (keys.size() ==0) {
            return "{}";
        } else {

            //TODO this needs to oonly add the value if it exists.
        for (int i=0; i<keys.size();i++) {

            if (i !=0) {
                stringBuffer.append(',');
            }
            stringBuffer.append(keys.get(i));
            if (values.size() > i) {
                stringBuffer.append(":" + values.get(i));
            }
        }
        stringBuffer.append('}');
        return stringBuffer.toString();
        }
    }

    public JObject buildObject() {
        //TODO check we have the correct number of keys and values?? maybe its implicit from earlier.

        for (int i=0; i<keys.size();i++) {

            map.put(keys.get(i), values.get(i));
        }
        return this;
    }

    @Override
    public JObject getAsObject(){
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JObject jObject = (JObject) o;
        return Objects.equals(map, jObject.map);
    }

    @Override
    public int hashCode() {

        return Objects.hash(map);
    }
}
