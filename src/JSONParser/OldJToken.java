package JSONParser;

import java.util.List;

public class OldJToken {

    private int startChar;
    private int endChar;
    private boolean hasChildren;


    public int getStartChar() {
        return startChar;
    }

    public void setStartChar(int startChar) {
        this.startChar = startChar;
    }

    public int getEndChar() {
        return endChar;
    }

    public void setEndChar(int endChar) {
        this.endChar = endChar;
    }

    public boolean isObject(){
        return false;
    }

    public boolean isArray(){
        return false;
    }

    public boolean isNumber(){
        return false;
    }

    public boolean isAString(){
        return false;
    }

    public boolean isBoolean(){
        return false;
    }

    public List<OldJToken> getAsArray(String key){throw new UnsupportedOperationException();}
}
