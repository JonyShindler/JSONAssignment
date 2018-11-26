package JSONParser;

public class JSONObject {

    private String key;
    private String node;
    private int startChar;
    private int endChar;
    private JSONObject childObject;
    private JSONObject parentObject;

    public JSONObject() {
    }

    public JSONObject getChildObject() {
        return childObject;
    }

    public void setChildObject(JSONObject childObject) {
        this.childObject = childObject;
        childObject.setParentObject(this);
    }

    public JSONObject getParentObject() {
        return parentObject;
    }

    public void setParentObject(JSONObject parentObject) {
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
        return "JSONObject{" +
                "key='" + key + '\'' +
                ", node='" + node + '\'' +
                ", startChar=" + startChar +
                ", endChar=" + endChar +
                ", childObject=" + childObject +
                ", parentObject=" + parentObject +
                '}';
    }
}
