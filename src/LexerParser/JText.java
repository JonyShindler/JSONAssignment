package LexerParser;

import java.util.Objects;

public abstract class JText extends JToken {

    String string;

    public JNumber getAsNumber(){
        throw new UnsupportedOperationException();
    }

    public JBoolean getAsBoolean(){
        throw new UnsupportedOperationException();
    }

    public JNull getAsNull(){
        throw new UnsupportedOperationException();
    }

    public JString getAsString(){
        throw new UnsupportedOperationException();
    }

    //TODO can remove half these methods.
    public String getString() {
        return string;
    }

    @Override
    public String convertToString() {
        return string;
    }

    @Override
    public String toString() {
        return string;
    }

    @Override
    public JText getAsText() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JText jText = (JText) o;
        return Objects.equals(string, jText.string);
    }

    @Override
    public int hashCode() {

        return Objects.hash(string);
    }
}

