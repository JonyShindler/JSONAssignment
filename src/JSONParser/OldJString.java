package JSONParser;

import java.util.Objects;

public class OldJString extends OldJToken {

    private final String string;


    public OldJString(String string) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OldJString jString = (OldJString) o;
        return Objects.equals(string, jString.string);
    }

    @Override
    public int hashCode() {

        return Objects.hash(string);
    }
}
