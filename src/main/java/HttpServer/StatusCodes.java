package HttpServer;

import java.util.HashMap;

public class StatusCodes {
    private HashMap<String, String> codes;

    public StatusCodes() {
        codes = new HashMap<>();
        codes.put("200", "OK");
        codes.put("404", "Not Found");
        codes.put("418", "");
    }

    public String message(int code) {
        String encoded = String.valueOf(code);
        return codes.get(encoded);
    }
}
