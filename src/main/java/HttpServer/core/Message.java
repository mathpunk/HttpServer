package HttpServer.core;

import java.util.HashMap;

public class Message {
    protected HashMap<String, String> headers;
    protected String version = "HTTP/1.1";
    protected String body;

    public Message() {
        this.headers = new HashMap<>();
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return this.version;
    }

    public void setHeader(String headerKey, Object headerValue) {
        headers.put(headerKey, headerValue.toString());
    }

    public String getHeader(String headerKey) {
        return headers.get(headerKey);
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return this.body;
    }

    public String getHeaderAsString(String fieldName) {
        return fieldName + ": " + headers.get(fieldName);
    }
}
