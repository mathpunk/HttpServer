package HttpServer.core.message.request;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PercentDecoder {

    private HashMap<String, String> codes;

    public PercentDecoder() {
        codes = new HashMap<>();
        codes.put("%3A", ":");
        codes.put("%2F", "/");
        codes.put("%3C", "<");
        codes.put("%3E", ">");
        codes.put("%22", "\"");
        codes.put("%3F", "?");
        codes.put("%23", "#");
        codes.put("%5B", "[");
        codes.put("%5D", "]");
        codes.put("%40", "@");
        codes.put("%21", "!");
        codes.put("%24", "\\$");
        codes.put("%26", "&");
        codes.put("%27", "'");
        codes.put("%28", "(");
        codes.put("%29", ")");
        codes.put("%2A", "*");
        codes.put("%2B", "+");
        codes.put("%2C", ",");
        codes.put("%3B", ";");
        codes.put("%3D", "=");
        codes.put("%25", "%");
        codes.put("%20", " ");
        codes.put("+", " ");
    }

    public boolean needsDecoding(String string) {
        Pattern pattern = encodedCharacterPattern();
        Matcher matcher = pattern.matcher(string);
        return matcher.find() || string.equals("+");
    }

    private Pattern encodedCharacterPattern() {
        return Pattern.compile("(%[0-9A-F]{2})");
    }

    public String decodeCharacter(String string) {
        // That is, decode a string that will be one character once decoded
        String decoded = codes.get(string);
        if (decoded == null) {
            return string;
        } else {
            return decoded;
        }
    }

    public String decodeString(String givenString) {
        Pattern pattern = encodedCharacterPattern();
        Matcher matcher = pattern.matcher(givenString);
        String replaced = null;
        while (matcher.find()) {
            String encodedCharacter = matcher.group();
            String decodedCharacter = codes.get(encodedCharacter);
            if (decodedCharacter == null) {
                // Except-worthy?
                System.out.println("Encoding missing: Don't know what " + encodedCharacter + " encodes");
            }
            if (replaced == null) {
                replaced = givenString;
            } else {
                replaced = replaced.replaceAll(encodedCharacter, decodedCharacter);
            }
        }
        if (replaced == null) {
            return givenString;
        } else {
            return replaced;
        }
    }
}
