package LexerParser;

import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class LexerParserTest {
	@Test
	public void testEmpty() throws IOException {
		LexerParser lex = new LexerParser(new StringReader(""));
		JsonSymbol symbol = lex.next();
		assertNull(symbol);
	}
	@Test
	public void testWord() throws IOException {
		LexerParser lex = new LexerParser(new StringReader("\"hello\""));
		assertNextSymbol(lex, Type.STRING, "\"hello\"", 0);
		assertNull(lex.next());
	}

	@Test
	public void testTrueAndFalseInArray() throws IOException {
		LexerParser lex = new LexerParser(new StringReader("[true,false]"));
		assertNextSymbol(lex, Type.OPEN_ARRAY, 0);
		assertNextSymbol(lex, Type.STRING, "true", 1);
		assertNextSymbol(lex, Type.COMMA, 5);
		assertNextSymbol(lex, Type.STRING, "false", 6);
		assertNextSymbol(lex, Type.CLOSE_ARRAY, 11);
		assertNull(lex.next());
	}

	@Test
	public void testAllStringsInArray() throws IOException {
		LexerParser lex = new LexerParser(new StringReader("[true, false, null, 107, \"Bob\"]"));
		assertNextSymbol(lex, Type.OPEN_ARRAY, 0);
		assertNextSymbol(lex, Type.STRING, "true", 1);
		assertNextSymbol(lex, Type.COMMA, 5);
		assertNextSymbol(lex, Type.SPACE,6);
		assertNextSymbol(lex, Type.STRING, "false" ,7);
		assertNextSymbol(lex, Type.COMMA, 12);
		assertNextSymbol(lex, Type.SPACE, 13);
		assertNextSymbol(lex, Type.STRING, "null", 14);
		assertNextSymbol(lex, Type.COMMA, 18);
		assertNextSymbol(lex, Type.SPACE, 19);
		assertNextSymbol(lex, Type.STRING, "107", 20);
		assertNextSymbol(lex, Type.COMMA, 23);
		assertNextSymbol(lex, Type.SPACE, 24);
		assertNextSymbol(lex, Type.STRING, "\"Bob\"", 25);
		assertNextSymbol(lex, Type.CLOSE_ARRAY, 30);
		assertNull(lex.next());
	}

	@Test
	public void testSingleOpen() throws IOException {
		LexerParser lex = new LexerParser(new StringReader("{"));
		JsonSymbol symbol = lex.next();
		assertEquals(Type.OPEN_OBJECT, symbol.type);
	}

	@Test
	public void testSingleClose() throws IOException {
		LexerParser lex = new LexerParser(new StringReader("}"));
		assertNextSymbol(lex, Type.CLOSE_OBJECT);
	}

	@Test
	public void testSingleComma() throws IOException {
		LexerParser lex = new LexerParser(new StringReader(","));
		assertNextSymbol(lex, Type.COMMA);
	}

	@Test
	public void testCombination() throws IOException {
		LexerParser lex = new LexerParser(new StringReader("{\"ugh\", \"blob\"}"));
		assertNextSymbol (lex, Type.OPEN_OBJECT, 0);
		assertNextSymbol (lex, Type.STRING, "\"ugh\"", 1);
		assertNextSymbol (lex, Type.COMMA, 6);
		assertNextSymbol (lex, Type.SPACE, 7);
		assertNextSymbol (lex, Type.STRING, "\"blob\"", 8);
		assertNextSymbol (lex, Type.CLOSE_OBJECT, 14);
		assertNull(lex.next());
	}

	@Test
	public void testMixedOther() throws IOException {
		LexerParser lex = new LexerParser(new StringReader("#@%!"));
		assertNextSymbol(lex, Type.OTHER, "#@%!");
		assertNull(lex.next());
	}

	@Test
	public void testQuotes() throws IOException {
		LexerParser lex = new LexerParser(new StringReader("\"a\""));
		assertNextSymbol(lex, Type.STRING, "\"a\"");
		assertNull(lex.next());
	}

	@Test
	public void testQuotesWithForwardSlach() throws IOException {
		LexerParser lex = new LexerParser(new StringReader("\"/a\""));
		assertNextSymbol(lex, Type.STRING, "\"/a\"");
		assertNull(lex.next());
	}

	@Test
	public void testNumbers() throws IOException {
		LexerParser lex = new LexerParser(new StringReader("1000"));
		assertNextSymbol(lex, Type.STRING, "1000");
		assertNull(lex.next());
	}

	@Test
	public void testStringWithSpace() throws IOException {
		LexerParser lex = new LexerParser(new StringReader("\"hi bob\""));
		assertNextSymbol(lex, Type.STRING, "\"hi bob\"");
		assertNull(lex.next());
	}

	//TODO cannot handle spaces!
	@Test
	public void testArrayOfNumbersWithSpace() throws IOException {
		LexerParser lex = new LexerParser(new StringReader("[1000, 2345]"));
		assertNextSymbol(lex, Type.OPEN_ARRAY);
		assertNextSymbol(lex, Type.STRING, "1000");
		assertNextSymbol(lex, Type.COMMA);
		assertNextSymbol(lex, Type.SPACE);
		assertNextSymbol(lex, Type.STRING, "2345");
		assertNextSymbol(lex, Type.CLOSE_ARRAY);
		assertNull(lex.next());
	}

	@Test
	public void testArrayOfNumbersWithOutSpace() throws IOException {
		LexerParser lex = new LexerParser(new StringReader("[1000,2345]"));
		assertNextSymbol(lex, Type.OPEN_ARRAY);
		assertNextSymbol(lex, Type.STRING, "1000");
		assertNextSymbol(lex, Type.COMMA);
		assertNextSymbol(lex, Type.STRING, "2345");
		assertNextSymbol(lex, Type.CLOSE_ARRAY);
		assertNull(lex.next());
	}

	private void assertNextSymbol(LexerParser lex, Type type, String value, int startChar) throws IOException {
		JsonSymbol symbol = lex.next();
		assertEquals("Type", type, symbol.type);
		if (null != value) assertEquals("Value", value, symbol.value);
		assertEquals("Start char: " + type, startChar, symbol.startChar);
	}

	private void assertNextSymbol(LexerParser lex, Type type, String value) throws IOException {
		JsonSymbol symbol = lex.next();
		assertEquals(type, symbol.type);
		if (null != value) assertEquals(value, symbol.value);
	}

	private void assertNextSymbol(LexerParser lex, Type type, int startChar) throws IOException {
		assertNextSymbol(lex, type, null, startChar);
	}

	private void assertNextSymbol(LexerParser lex, Type type) throws IOException {
		assertNextSymbol(lex, type, null);
	}


}
