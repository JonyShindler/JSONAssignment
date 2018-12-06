package LexerParser;

public class JBoolean extends JString
{
    public JBoolean(String string)
    {
        super(string);
    }

    @Override
    public JBoolean getAsBoolean()
    {
        return this;
    }

    @Override
    public String toString() {
        return string;
    }
}
