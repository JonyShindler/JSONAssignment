package LexerParser;

import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class TestLexer {
	@Test
	public void testEmpty() throws IOException {
		LexerParser lex = new LexerParser(new StringReader(""));
		JsonSymbol symbol = lex.next();
		assertNull(symbol);
	}
	@Test
	public void testWord() throws IOException {
		LexerParser lex = new LexerParser(new StringReader("hello"));
		assertNextSymbol(lex, Type.STRING, "hello");
		assertNull(lex.next());
	}

	@Test
	public void testSpace() throws IOException {
		LexerParser lex = new LexerParser(new StringReader(" "));
		assertNextSymbol(lex, Type.SPACE);
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
	public void testSingleSlash() throws IOException {
		LexerParser lex = new LexerParser(new StringReader(","));
		assertNextSymbol(lex, Type.COMMA);
	}

	@Test
	public void testCombination() throws IOException {
		LexerParser lex = new LexerParser(new StringReader("{ugh}"));
		assertNextSymbol (lex, Type.OPEN_OBJECT);
		assertNextSymbol (lex, Type.STRING, "ugh");
		assertNextSymbol (lex, Type.CLOSE_OBJECT);
		assertNull(lex.next());
	}

	@Test
	public void testMixedOther() throws IOException {
		LexerParser lex = new LexerParser(new StringReader("#@%!"));
		assertNextSymbol(lex, Type.OTHER, "#@%!");
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
