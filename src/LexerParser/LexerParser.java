package LexerParser;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;

public class LexerParser {

			private final PushbackReader reader;

			public LexerParser(Reader reader) {
				this.reader = new PushbackReader(reader);
			}

			// This method will return the next symbol in the linear JSON string.
			public JsonSymbol next() throws IOException {
				int c = reader.read();
				if (-1 == c) return null; // no more symbols
				if ('{' == c) return new JsonSymbol(Type.OPEN_OBJECT, "{");
				if ('}' == c) return new JsonSymbol(Type.CLOSE_OBJECT, "}");
				if (',' == c) return new JsonSymbol(Type.COMMA, ",");
				if (':' == c) return new JsonSymbol(Type.COLON, ":");
				if ('[' == c) return new JsonSymbol(Type.OPEN_ARRAY, "[");
				if (']' == c) return new JsonSymbol(Type.CLOSE_ARRAY, "]");
				if (Character.isWhitespace(c)) {
					while (Character.isWhitespace(c)) { // eat extra whitespace
						c = reader.read();
					}
					if (noCharactersRemain(c)) reader.unread(c);
					return new JsonSymbol(Type.SPACE, " ");
				}
				//TODO here we need to include something that will include "" in a string.

				if (Character.isLetterOrDigit(c)) {
					StringBuffer value = new StringBuffer();
					while (Character.isLetterOrDigit(c)) { // collect extra digits
						value.append((char)c);
						c = reader.read();
					}
					if (noCharactersRemain(c)) reader.unread(c);
					return new JsonSymbol(Type.STRING, value.toString());
				}

				StringBuffer value = new StringBuffer();
				while ((noCharactersRemain(c) && isNotBracket(c) && !Character.isWhitespace(c) && !Character.isLetterOrDigit(c)) ) { // collect extra digits
					value.append((char)c);
					c = reader.read();
				}
				if (noCharactersRemain(c)) reader.unread(c);
				return new JsonSymbol(Type.OTHER, value.toString());
			}

	private boolean isNotBracket(int c) {
		return '{' != c
				&& '}' != c
				&& '[' != c
				&& ']' != c;
	}

	private boolean noCharactersRemain(int c) {
		return -1 != c;
	}

}


