package LexerParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JArray extends JToken {

    private List<JToken> tokens = new ArrayList<>();

    public JArray addToList(JToken jtoken) {
        tokens.add(jtoken);
        return this;
    }

    public JArray() {
        this.tokens = new ArrayList<>();
    }

    public JArray(List<JToken> tokens) {
        this.tokens = tokens;
    }

    @Override
    public List<JToken> getAsArray() {
        return tokens;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append('[');
        for (int i=0; i<tokens.size();i++) {

            if (i !=0) {
                stringBuffer.append(',');
            }
            stringBuffer.append(tokens.get(i));
        }
        stringBuffer.append(']');
        return stringBuffer.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JArray array = (JArray) o;
        return Objects.equals(tokens, array.tokens);
    }

    @Override
    public int hashCode() {

        return Objects.hash(tokens);
    }
}
