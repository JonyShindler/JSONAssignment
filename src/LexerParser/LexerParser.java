package LexerParser;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;

public class LexerParser {

			private final PushbackReader reader;
			private int endOfPreviousSymbol = -1;

	public LexerParser(Reader reader) {
				this.reader = new PushbackReader(reader);
			}

			// This method will return the next symbol in the linear JSON string.
			public JsonSymbol next() throws IOException {
				int currentStart = endOfPreviousSymbol + 1;
				int c = reader.read();
				if (-1 == c) return null; // no more symbols
				else if ('{' == c) {endOfPreviousSymbol ++; return new JsonSymbol(Type.OPEN_OBJECT, "{", currentStart);}
				else if ('}' == c) {endOfPreviousSymbol ++; return new JsonSymbol(Type.CLOSE_OBJECT, "}", currentStart);}
				else if (',' == c) {endOfPreviousSymbol ++; return new JsonSymbol(Type.COMMA, ",", currentStart);}
				else if (':' == c) {endOfPreviousSymbol ++; return new JsonSymbol(Type.COLON, ":", currentStart);}
				else if ('[' == c) {endOfPreviousSymbol ++; return new JsonSymbol(Type.OPEN_ARRAY, "[", currentStart);}
				else if (']' == c) {endOfPreviousSymbol ++; return new JsonSymbol(Type.CLOSE_ARRAY, "]", currentStart);}
				else if (Character.isWhitespace(c)) {
					while (Character.isWhitespace(c)) { // eat extra whitespace
						c = reader.read();
						endOfPreviousSymbol++;
					}
					if (charactersRemain(c)) reader.unread(c);
					return new JsonSymbol(Type.SPACE, " ", currentStart);
				}

				// Numbers
				else if (Character.isDigit(c)) {
					StringBuffer value = new StringBuffer();
					while (Character.isDigit(c)) { // collect extra digits
						value.append((char)c);
						c = reader.read();
						endOfPreviousSymbol++;
					}
					if (charactersRemain(c)) reader.unread(c);
					return new JsonSymbol(Type.STRING, value.toString(), currentStart);
				}


				// Strings
				else if ('"' == c) {
					StringBuffer value = new StringBuffer();
					value.append((char)c);
					endOfPreviousSymbol++;
					c = reader.read();
					if (Character.isLetterOrDigit(c) || c == '/') {
						while (Character.isLetterOrDigit(c) || c == '/' || c == ' ') { // collect extra digits
							value.append((char)c);
							c = reader.read();
							endOfPreviousSymbol++;
							if ('"' == c) {
								value.append((char)c);
								endOfPreviousSymbol++;
								break;
							}
						}
						return new JsonSymbol(Type.STRING, value.toString(), currentStart);
					}
				}

				//Null/True/False
				else if (Character.isLetter(c)) {
						StringBuffer value = new StringBuffer();
						while (Character.isLetter(c)) { // collect extra digits
							value.append((char)c);
							c = reader.read();
							endOfPreviousSymbol++;
						}
					if (charactersRemain(c)) reader.unread(c);
					return new JsonSymbol(Type.STRING, value.toString(), currentStart);
				}

				else
				{
					StringBuffer value = new StringBuffer();
					while ((charactersRemain(c) && isNotBracket(c) && !Character.isWhitespace(c) && !Character.isLetterOrDigit(c)))
					{ // collect extra digits
						value.append((char)c);
						c = reader.read();
					}
					if (charactersRemain(c)) reader.unread(c);
					return new JsonSymbol(Type.OTHER, value.toString(), endOfPreviousSymbol);
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


