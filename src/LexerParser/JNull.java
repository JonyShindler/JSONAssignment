package LexerParser;

public class JNull extends JString {
    public JNull(String string) {
        super(string);
    }

    @Override
    public JNull getAsNull() {
        return this;
    }
}
