package LexerParser;

public class JNull extends JText {
    public JNull(String string) {
        this.string = string;
    }

    @Override
    public JNull getAsNull() {
        return this;
    }
}
