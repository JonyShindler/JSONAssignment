package LexerParser;

import java.io.IOException;
import java.io.Reader;

class Parser2 {

	JToken parse(Reader in) throws IOException {
		PushbackLexer lex = new PushbackLexer(new LexerParser(in));
		JsonSymbol s = lex.next();

		if (null == s) return new JString("");

		JToken jToken = null;
		Type symbolType = s.type;
		if (symbolType == Type.OPEN_ARRAY) {
			jToken = parseArray(lex);
		} else if (symbolType == Type.OPEN_OBJECT) {
			jToken =  parseObject(lex);
		}

		return jToken;
	}

	private JToken parseArray(PushbackLexer lex) throws IOException {
		JArray array = new JArray();
		boolean lastWasComma = true; //The first value in the array can be fine.

		while (true) {
			JsonSymbol s = lex.next();
			if (s.type == Type.STRING) {
				if (lastWasComma) {
					array.addToList(new JString(s.value));
					lastWasComma = false;
				} else {
					throw new IOException("Expected comma after value");
				}

			} else if (s.type == Type.OPEN_OBJECT) {
				if (!lastWasComma) {
					throw new IOException("Expected comma after value");
				}
				array.addToList(parseObject(lex));

			} else if (s.type == Type.OPEN_ARRAY) {
				if (!lastWasComma) {
					throw new IOException("Expected comma after value");
				}
				array.addToList(parseArray(lex));

			} else if (s.type == Type.COMMA) {
				//do nothing, but make sure to say that the last thing was a comma.
				lastWasComma = true;

			} else if (s.type == Type.CLOSE_ARRAY) {
				return array;

			} else {
				throw new IOException("Expected something...");
			}
		}
	}

	private JToken parseObject(PushbackLexer lex) throws IOException {
		JObject object = new JObject();
		Type lastType = Type.COMMA; //The first thing is a key.

		// TODO use the text method below to handle whitespace etc.
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
				//TODO check its a string etc.

			} else if (lastType == Type.COMMA) {
				//just change a boolean flag for now maybe?;
				lastType = Type.COMMA;
				//add the key.
				object.addKey(s.value);

			} else if (lastType == Type.COLON) {
				//TODO go figure out the type of the JToken and add it to the values.
				//for now lets pretend theyre always strings.
				lastType = Type.COLON;
				object.addValue(new JString(s.value));
			}

			//TODO throw exception here cos we should have existed the object here and
			// TODO we have to have been one of those things above
		}
	}


	// This method reads the next symbols and returns a text based document.
	//TODO use this method to create the JStrings for us?
	private String text(PushbackLexer lex) throws IOException {
		StringBuilder ret = new StringBuilder();
		sym: for (JsonSymbol symbol = lex.next(); symbol != null; symbol = lex.next()) {
			switch(symbol.type) {
			case STRING: case SPACE: case OTHER: case COMMA:
					ret.append(symbol.value);
				break;
			default:
				 break sym; // to stop loop
			//TODO need to add the rest from Type
			//TODO See page 22 and implement
			}
		}
		return ret.toString();

	}

}
