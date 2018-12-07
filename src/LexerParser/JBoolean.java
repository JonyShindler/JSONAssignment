package LexerParser;

public class JBoolean extends JText
{
    public JBoolean(String string)
    {
        this.string = string;
    }

    @Override
    public JBoolean getAsBoolean()
    {
        return this;
    }

}
