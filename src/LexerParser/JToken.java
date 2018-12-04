package LexerParser;

import java.util.List;
import java.util.Map;

public abstract class JToken {
    public List<JToken> getAsArray(){
        throw new UnsupportedOperationException();
    }

    public Map<String, JToken> getAsMap(){
        throw new UnsupportedOperationException();
    }

    public String getAsString(){
        throw new UnsupportedOperationException();
    }

    public Number getAsNumber(){
        throw new UnsupportedOperationException();
    }
}
