package LexerParser;

import java.util.*;

public class JObject extends JToken {

    private List<String> keys = new ArrayList<>();
    private List<JToken> values = new ArrayList<>();
    private Map<String, JToken> map = new HashMap<>();

    public void addValue(JToken jToken) {
        values.add(jToken);
    }

    public void addKey(String key) {
        keys.add(key);
    }

    public JObject(Map<String, JToken> map) {
        //TODO the map doesnt tostring in bloody order! not the end of the world thoguh
        this.map = map;
        this.keys = new ArrayList<>(map.keySet());
        this.values = new ArrayList<>(map.values());
    }

    public JObject() {
    }

    public JToken get(String key){
        return map.get(key);
    }

    public Map<String, JToken> getMap(){
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
            stringBuffer.append("\"" + keys.get(i) + "\"");
            if (values.size() > i) {
                stringBuffer.append(":" + values.get(i));
            }
        }
        stringBuffer.append('}');
        return stringBuffer.toString();
        }
    }


//    @Override
//    public String toString() {
//        StringBuffer stringBuffer = new StringBuffer();
//        stringBuffer.append('{');
//        int i=1;
//        //TODO the map isnt in fucking order!
//        for (Map.Entry entry: map.entrySet()) {
//            if (map.entrySet().size() != 1 && i !=1) {
//                stringBuffer.append(',');
//            }
//            stringBuffer.append(entry.getKey() + ":" + entry.getValue());
//            i++;
//        }
//        stringBuffer.append('}');
//        return stringBuffer.toString();
//    }




    public JObject buildObject() {
        //TODO zip the map together and return the object.
        //TODO maybe check we have the correct number of keys and values?? maybe its implicit from earlier.

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
