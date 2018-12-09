package LexerParser;

import java.io.IOException;

public class JString extends JText{

    public JString(String string) {
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
