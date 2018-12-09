package LexerParser;

import java.io.IOException;
import java.io.StringReader;

public class Parser2 {

	public static final String EXPECTED_COMMA = "Expected comma ";
	public static final String ILLEGAL_COMMA = "Illegal comma ";
	public static final String UNEXPECTED_CHARACTER = "Unexpected character: ";

	public JToken parse(String string) throws IOException {
		PushbackLexer lex = new PushbackLexer(new LexerParser(new StringReader(string)));
		JsonSymbol s = lex.next();

		if (null == s) {return JStringBuilder.createJString("");}

		JToken jToken;
		Type symbolType = s.type;
		if (symbolType == Type.OPEN_ARRAY) {
			jToken = parseArray(lex);
		} else if (symbolType == Type.OPEN_OBJECT) {
			jToken =  parseObject(lex);
		} else {
			throw new IOException("JSON does not begin with {");
		}

		return jToken;
	}

    private String generateExceptionMessage(String exceptionMessage, int startChar) {
        return generateExceptionMessage(exceptionMessage, null, startChar);
    }

	private String generateExceptionMessage(String exceptionMessage, String unexpectedCharacter, int startChar) {
	    StringBuilder builder = new StringBuilder();
	    builder.append(exceptionMessage);

	    if (unexpectedCharacter !=null) {
	        builder.append(unexpectedCharacter);
	        builder.append(" ");
        }

        builder.append("at position ");
        builder.append(startChar);
        return builder.toString();
    }

	private JToken parseArray(PushbackLexer lex) throws IOException {
		JArray array = new JArray();
		Type lastType = Type.COMMA; //The first value in the array can be fine.

		while (true) {
			JsonSymbol s = lex.next();
			if (s.type == Type.STRING ) {
				if (lastType!=Type.COMMA) { throw new IOException(generateExceptionMessage(EXPECTED_COMMA, s.startChar));}
				array.addToList(JStringBuilder.createJString(s.value));
				lastType = Type.STRING;

			} else if (s.type == Type.OPEN_OBJECT) {
				if (lastType!=Type.COMMA) { throw new IOException(generateExceptionMessage(EXPECTED_COMMA, s.startChar));}
				array.addToList(parseObject(lex));
				lastType = Type.OPEN_OBJECT;

			} else if (s.type == Type.OPEN_ARRAY) {
				if (lastType!=Type.COMMA) { throw new IOException(generateExceptionMessage(EXPECTED_COMMA, s.startChar));}
				array.addToList(parseArray(lex));
				lastType = Type.OPEN_ARRAY;

			} else if (s.type == Type.COMMA) {
			    // Comma after comma
				if (lastType==Type.COMMA) { throw new IOException(generateExceptionMessage(ILLEGAL_COMMA, s.startChar));}
				lastType = Type.COMMA;

			} else if (s.type == Type.CLOSE_ARRAY) {
			    // Comma before closing bracket
				if (lastType == Type.COMMA) {
					throw new IOException(generateExceptionMessage(ILLEGAL_COMMA, s.startChar-1));
				}
				return array;

			} else if (s.type == Type.SPACE) {
				//do nothing

			} else {
			    // Anything which isnt a string, close array, open object, open array or comma is not valid in an array.
				throw new IOException(generateExceptionMessage(UNEXPECTED_CHARACTER, s.value, s.startChar));
			}
		}
	}

	private JToken parseObject(PushbackLexer lex) throws IOException {
		JObject object = new JObject();
		Type lastType = Type.COMMA; //The first thing is a key.

		while (true) {
			JsonSymbol s = lex.next();
			if (s.type == Type.CLOSE_OBJECT) {
				if (lastType != Type.COLON) {
					throw new IOException("uneven number of keys and values..");
				}
				return object.buildObject();

			} else if (s.type == Type.OPEN_OBJECT) {
				if (lastType != Type.COLON){
					throw new IOException("Object can only be a key.");
				}
				object.addValue(parseObject(lex));

				// this bit decides what to do if you just had a comma or a string.
			} else if (s.type == Type.OPEN_ARRAY) {
				if (lastType != Type.COLON){
					throw new IOException("Array can only be a key.");
				}
				object.addValue(parseArray(lex));

			// this bit decides what to do if you just had a comma or a string.
			} else if (s.type == Type.COMMA) {
				lastType = Type.COMMA;
				//Do nothing else
				continue;

			} else if (s.type == Type.COLON) {
				lastType = Type.COLON;
				//Do nothing else
				continue;

				//Otherwise we must be a string.
			} else if (lastType == Type.COMMA) {
				lastType = Type.COMMA;
				//add the key.
				if (s.type != Type.STRING) {
					throw new IOException("Each key must be a string after a comma in an object");
				}
				object.addKey(new JString(s.value));

			} else if (lastType == Type.COLON) {
				lastType = Type.COLON;
				if (s.type == Type.STRING) {
					object.addValue(JStringBuilder.createJString(s.value));
				}
			} else if (s.type == Type.SPACE) {
				//do nothing
			}

			//TODO throw exception here cos we should have existed the object here and
			// TODO we have to have been one of those things above
		}
	}

}
