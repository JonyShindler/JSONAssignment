package LexerParser;

import java.util.Objects;

public class JString extends JToken {

    final String string;

    //TODO this constructor should decide which object to make? or make a builder that can decide that.
    public JString(String string) {
        this.string = string;
    }
    public JNumber getAsNumber(){
        throw new UnsupportedOperationException();
    }

    public JBoolean getAsBoolean(){
        throw new UnsupportedOperationException();
    }
    //TODO make classes that extend this. perhaps rename this class JText.

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

    public enum StringType {
    STRING,
    NUMBER,
    TRUE,
    FALSE,
    NULL
    }
}

