package LexerParser;

import java.util.Objects;

public class JNumber extends JString {

    public JNumber(String string)
    {
        super(string);
    }

    @Override
    public JNumber getAsNumber() {
        return this;
    }

    public Integer getInteger(){
        return Integer.parseInt(string);
    }

    @Override
    public String toString() {
        return string;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JNumber jNumber = (JNumber) o;
        return Objects.equals(string, jNumber.string);
    }

    @Override
    public int hashCode() {

        return Objects.hash(string);
    }
}
