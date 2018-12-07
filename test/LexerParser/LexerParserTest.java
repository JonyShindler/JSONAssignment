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
		assertNextSymbol(lex, Type.STRING, "\"hello\"");
		assertNull(lex.next());
	}

	@Test
	public void testTrueAndFalseInArray() throws IOException {
		LexerParser lex = new LexerParser(new StringReader("[true,false]"));
		assertNextSymbol(lex, Type.OPEN_ARRAY);
		assertNextSymbol(lex, Type.STRING, "true");
		assertNextSymbol(lex, Type.COMMA);
		assertNextSymbol(lex, Type.STRING, "false");
		assertNextSymbol(lex, Type.CLOSE_ARRAY);
		assertNull(lex.next());
	}

	@Test
	public void testAllStringsInArray() throws IOException {
		LexerParser lex = new LexerParser(new StringReader("[true, false, null, 107, \"Bob\"]"));
		assertNextSymbol(lex, Type.OPEN_ARRAY);
		assertNextSymbol(lex, Type.STRING, "true");
		assertNextSymbol(lex, Type.COMMA);
		assertNextSymbol(lex, Type.SPACE);
		assertNextSymbol(lex, Type.STRING, "false");
		assertNextSymbol(lex, Type.COMMA);
		assertNextSymbol(lex, Type.SPACE);
		assertNextSymbol(lex, Type.STRING, "null");
		assertNextSymbol(lex, Type.COMMA);
		assertNextSymbol(lex, Type.SPACE);
		assertNextSymbol(lex, Type.STRING, "107");
		assertNextSymbol(lex, Type.COMMA);
		assertNextSymbol(lex, Type.SPACE);
		assertNextSymbol(lex, Type.STRING, "\"Bob\"");
		assertNextSymbol(lex, Type.CLOSE_ARRAY);
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
		LexerParser lex = new LexerParser(new StringReader("{\"ugh\"}"));
		assertNextSymbol (lex, Type.OPEN_OBJECT);
		assertNextSymbol (lex, Type.STRING, "\"ugh\"");
		assertNextSymbol (lex, Type.CLOSE_OBJECT);
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

	private void assertNextSymbol(LexerParser lex, Type type, String value) throws IOException {
		JsonSymbol symbol = lex.next();
		assertEquals(type, symbol.type);
		if (null != value) assertEquals(value, symbol.value);
	}

	private void assertNextSymbol(LexerParser lex, Type type) throws IOException {
		assertNextSymbol(lex, type, null);
	}


}
