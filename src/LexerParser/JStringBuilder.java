package LexerParser;

public class JStringBuilder
    //This class decides which type of JString to make.
    //This info is useful

{
    public static JString createJString(String string){

        if (string.matches("-?\\d+(\\.\\d+)?")){
            return new JNumber(string);

        } else if (string.equals("true")){
            return new JBoolean("true");

        } else if (string.equals("false")) {
            return new JBoolean("false");

        } else if (string.equals("false")) {
            return new JNull("null");

        } else {
            return new JString(string); //TODO make this check for quotes.
        }
    }


}
