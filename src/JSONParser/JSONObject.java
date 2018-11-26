package JSONParser;

public class JSONObject {

    private String key;
    private String node;
    private int startChar;
    private int endChar;

    public JSONObject(String key, String node, int startChar, int endChar) {
        this.key = key;
        this.node = node;
        this.startChar = startChar;
        this.endChar = endChar;
    }

    public JSONObject() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public int getStartChar() {
        return startChar;
    }

    public void setStartChar(int startChar) {
        this.startChar = startChar;
    }

    public int getEndChar() {
        return endChar;
    }

    public void setEndChar(int endChar) {
        this.endChar = endChar;
    }

    @Override
    public String toString() {
        return "JSONObject{" +
                "key='" + key + '\'' +
                ", node='" + node + '\'' +
                ", startChar=" + startChar +
                ", endChar=" + endChar +
                '}';
    }
}
