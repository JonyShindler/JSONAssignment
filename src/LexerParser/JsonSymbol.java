package LexerParser;

public class JsonSymbol {

	public final Type type;
	public final String value;
	public final int startChar;
	public JsonSymbol(Type type, String value, int startChar) {
		this.type = type;
		this.value = value;
		this.startChar = startChar;
	}

			//TODO add the character start position in here.

	@Override
	public String toString() {
		return "JsonSymbol{" +
				"type=" + type +
				", value='" + value + '\'' +
				'}';
	}
}


