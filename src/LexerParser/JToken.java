package LexerParser;

import jdk.nashorn.internal.scripts.JO;

import java.util.List;
import java.util.Map;

public abstract class JToken {
    public JArray getAsArray(){
        throw new UnsupportedOperationException();
    }

    public JObject getAsObject(){
        throw new UnsupportedOperationException();
    }

    public JString getAsString(){
        throw new UnsupportedOperationException();
    }

    public JNumber getAsNumber(){
        throw new UnsupportedOperationException();
    }

    public String convertToString(){
        throw new UnsupportedOperationException();
    }
}
