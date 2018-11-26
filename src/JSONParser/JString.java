package JSONParser;

public class JString extends JToken{

    private final String string;


    public JString(String string) {
        this.string = string;
    }

    @Override
    public boolean isAString() {
        return true;
    }

    @Override
    public String toString() {
        return string;
    }
}
