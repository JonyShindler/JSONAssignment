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
				else if ('{' == c) return new JsonSymbol(Type.OPEN_OBJECT, "{");
				else if ('}' == c) return new JsonSymbol(Type.CLOSE_OBJECT, "}");
				else if (',' == c) return new JsonSymbol(Type.COMMA, ",");
				else if (':' == c) return new JsonSymbol(Type.COLON, ":");
				else if ('[' == c) return new JsonSymbol(Type.OPEN_ARRAY, "[");
				else if (']' == c) return new JsonSymbol(Type.CLOSE_ARRAY, "]");
				else if (Character.isWhitespace(c)) {
					while (Character.isWhitespace(c)) { // eat extra whitespace
						c = reader.read();
					}
					if (charactersRemain(c)) reader.unread(c);
					return new JsonSymbol(Type.SPACE, " ");
					//TODO i dont care about spaces for now.
				}

				// Numbers
				else if (Character.isDigit(c)) {
					StringBuffer value = new StringBuffer();
					while (Character.isDigit(c)) { // collect extra digits
						value.append((char)c);
						c = reader.read();
					}
					if (charactersRemain(c)) reader.unread(c);
					return new JsonSymbol(Type.STRING, value.toString());
				}


				//TODO this needs to be able to read null, true, false etc. so maybe have another one that checks for these?
				// Strings
				else if ('"' == c) {
					c = reader.read();
					if (Character.isLetterOrDigit(c) || c == '/') {
						StringBuffer value = new StringBuffer();
						while (Character.isLetterOrDigit(c) || c == '/' || c == ' ') { // collect extra digits
							value.append((char)c);
							c = reader.read();
							if ('"' == c) {
								break;
							}
						}
						return new JsonSymbol(Type.STRING, value.toString());
					}
				}

				//Null/True/False
				else if (Character.isLetter(c)) {
						StringBuffer value = new StringBuffer();
						while (Character.isLetter(c)) { // collect extra digits
							value.append((char)c);
							c = reader.read();
						}
					if (charactersRemain(c)) reader.unread(c);
					return new JsonSymbol(Type.STRING, value.toString());
				}


				else
				{
					//TODO we are gonna have to process spaces as well!
					StringBuffer value = new StringBuffer();
					while ((charactersRemain(c) && isNotBracket(c) && !Character.isWhitespace(c) && !Character.isLetterOrDigit(c)))
					{ // collect extra digits
						value.append((char)c);
						c = reader.read();
					}
					if (charactersRemain(c)) reader.unread(c);
					return new JsonSymbol(Type.OTHER, value.toString());
				}
				return null;
			}

	private boolean isNotBracket(int c) {
		return '{' != c
				&& '}' != c
				&& '[' != c
				&& ']' != c;
	}

	private boolean charactersRemain(int c) {
		return -1 != c;
	}

}


