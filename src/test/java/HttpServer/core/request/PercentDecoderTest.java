package HttpServer.core.request;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PercentDecoderTest {
    private PercentDecoder decoder;

    @Before
    public void setup() {
        decoder = new PercentDecoder();
    }

    @Test
    public void itRecognizesAnEncodedCharacter() {
        assert(decoder.needsDecoding("%3A"));
        assert(decoder.needsDecoding("+"));
        assertFalse(decoder.needsDecoding("Hello"));
    }

    @Test
    public void itRecognizesEncodedStrings() {
        assert(decoder.needsDecoding("Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F" ));
    }

    @Test
    public void itDecodesEachKnownCharacterAlone() {
        assertEquals(":", decoder.decodeCharacter("%3A"));
        assertEquals("/", decoder.decodeCharacter("%2F"));
        assertEquals("?", decoder.decodeCharacter("%3F"));
        assertEquals("#", decoder.decodeCharacter("%23"));
        assertEquals("[", decoder.decodeCharacter("%5B"));
        assertEquals("]", decoder.decodeCharacter("%5D"));
        assertEquals("@", decoder.decodeCharacter("%40"));
        assertEquals("!", decoder.decodeCharacter("%21"));
        assertEquals("<", decoder.decodeCharacter("%3C"));
        assertEquals(">", decoder.decodeCharacter("%3E"));
        assertEquals("\"", decoder.decodeCharacter("%22"));
        assertEquals("\\$", decoder.decodeCharacter("%24"));
        assertEquals("&", decoder.decodeCharacter("%26"));
        assertEquals("'", decoder.decodeCharacter("%27"));
        assertEquals("(", decoder.decodeCharacter("%28"));
        assertEquals(")", decoder.decodeCharacter("%29"));
        assertEquals("*", decoder.decodeCharacter("%2A"));
        assertEquals("+", decoder.decodeCharacter("%2B"));
        assertEquals(",", decoder.decodeCharacter("%2C"));
        assertEquals(";", decoder.decodeCharacter("%3B"));
        assertEquals("=", decoder.decodeCharacter("%3D"));
        assertEquals("%", decoder.decodeCharacter("%25"));
        assertEquals(" ", decoder.decodeCharacter("%20"));
        assertEquals(" ", decoder.decodeCharacter("+"));
    }

    @Test
    public void itDecodesStrings() {
        String encodedString = "Do you know C%2B%2B%3F";
        String decoded = decoder.decodeString(encodedString);
        assertEquals("Do you know C++?", decoded);
    }

    @Test
    public void itDecodesComplicatedStrings() {
        String encodedString = "Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F";
        String decoded = decoder.decodeString(encodedString);
        String expected = "Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?";
        assertEquals(expected, decoded);
    }

    @Test
    public void itReturnsUnencodedStringsUnchanged() {
        String unencodedString = "string";
        String decoded = decoder.decodeString(unencodedString);
        assertEquals(unencodedString, decoded);
    }
}
