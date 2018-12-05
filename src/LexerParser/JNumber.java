package LexerParser;

import java.util.Objects;

public class JNumber extends JToken {

    private final Integer number;

    public JNumber(String number) {
        this.number = Integer.valueOf(number);
    }


    @Override
    public JNumber getAsNumber() {
        return this;
    }

    public Integer getInteger(){
        return number;
    }

    @Override
    public String convertToString() {
        return number.toString();
    }

    @Override
    public String toString() {
        return number.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JNumber jNumber = (JNumber) o;
        return Objects.equals(number, jNumber.number);
    }

    @Override
    public int hashCode() {

        return Objects.hash(number);
    }
}
