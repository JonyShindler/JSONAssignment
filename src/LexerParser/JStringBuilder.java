package LexerParser;

import java.io.IOException;

public class JStringBuilder
{
    public static JText createJString(String string) throws IOException {

        if (string.matches("-?\\d+(\\.\\d+)?")){
            return new JNumber(string);

        } else if (string.equals("true")){
            return new JBoolean("true");

        } else if (string.equals("false")) {
            return new JBoolean("false");

        } else if (string.equals("null")) {
            return new JNull("null");

        } else if (beginsAndEndsWithQuoteMarks(string)){
            return new JString(string);
        } else {
            throw new IOException("Not a valid JSON string");
        }
    }

    private static boolean beginsAndEndsWithQuoteMarks(String string) {
        return string.charAt(0) == '"' && string.charAt(string.length() - 1) == '"';
    }
}
