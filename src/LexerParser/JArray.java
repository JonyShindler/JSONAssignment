package LexerParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JArray extends JToken {

    //TODO add iterable method on this

    private List<JToken> tokens;

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

    public List<JToken> getTokens(){
        return tokens;
    }

    @Override
    public JArray getAsArray() {
        return this;
    }

    public JToken get(int i){
        return tokens.get(i);
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
