package JSONParser;

public class OlderJSONObject {

    private String key;
    private String node;
    private int startChar;
    private int endChar;
    private OlderJSONObject childObject;
    private OlderJSONObject parentObject;

    public OlderJSONObject() {
    }

    public OlderJSONObject getChildObject() {
        return childObject;
    }

    public void setChildObject(OlderJSONObject childObject) {
        this.childObject = childObject;
        childObject.setParentObject(this);
    }

    public OlderJSONObject getParentObject() {
        return parentObject;
    }

    public void setParentObject(OlderJSONObject parentObject) {
        this.parentObject = parentObject;
        parentObject.setChildObject(this);
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
        return "OlderJSONObject{" +
                "key='" + key + '\'' +
                ", node='" + node + '\'' +
                ", startChar=" + startChar +
                ", endChar=" + endChar +
                ", childObject=" + childObject +
                ", parentObject=" + parentObject +
                '}';
    }
}
