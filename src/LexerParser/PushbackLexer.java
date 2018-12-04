package LexerParser;

import java.io.IOException;
import java.util.Stack;

public class PushbackLexer {

    private LexerParser lexer;
    private Stack<JsonSymbol> symbols;

    public PushbackLexer(LexerParser lexer) {
        this.lexer = lexer;
        this.symbols = new Stack<>();
    }

    public JsonSymbol next() throws IOException {
        if (!symbols.isEmpty()) return symbols.pop();
        return lexer.next();
    }

    public void unread(JsonSymbol symbol) {
        symbols.push(symbol);
    }

}
