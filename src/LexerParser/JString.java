package LexerParser;

import java.util.Objects;

public class JString extends JToken {

    private final String string;

    public JString(String string) {
        this.string = string;
    }

    @Override
    public JString getAsString() {
        return this;
    }

    public String getString() {
        return string;
    }

    @Override
    public String convertToString() {
        return string;
    }

    @Override
    public String toString() {
        return "\"" + string + "\"";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JString jString = (JString) o;
        return Objects.equals(string, jString.string);
    }

    @Override
    public int hashCode() {

        return Objects.hash(string);
    }
}
