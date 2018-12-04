package LexerParser;

public class JsonSymbol {

	public final Type type;
	public final String value;
	public JsonSymbol(Type type, String value) {
		this.type = type;
		this.value = value;
			}

	@Override
	public String toString() {
		return "JsonSymbol{" +
				"type=" + type +
				", value='" + value + '\'' +
				'}';
	}
}


