package LexerParser;

public class JString extends JText{

    public JString(String string) {
        //TODO verify that its a valid JString as keys call this constructor directly,
        this.string = string.replace("\"", "");
    }

    @Override
    public String toString() {
        return "\"" + string + "\"";
    }

    @Override
    public JString getAsString() {
        return this;
    }
}
