package LexerParser;

public abstract class JToken {
    public JArray getAsArray(){
        throw new UnsupportedOperationException();
    }

    public JObject getAsObject(){
        throw new UnsupportedOperationException();
    }

    public JText getAsText(){
        throw new UnsupportedOperationException();
    }

    public String convertToString(){
        throw new UnsupportedOperationException();
    }
}
