package HttpServer;
import java.util.HashMap;
import java.util.stream.Stream;

public class Response extends HashMap<String, String> {

    private String version;
    private final String crlf = "";
    private int status;
    private String body;
    private HashMap<String, String> headers;

    public Response() {
        this.version = "HTTP/1.1";
        this.headers = new HashMap<>();
    }

    public Response setStatus(int code) {
        this.status = code;
        return this;
    }

    public void putVersion(String versionString) {
        this.version = versionString;
    }

    public String getVersion() {
        return version;
    }

    public void putHeader(String headerKey, String headerValue) {
        this.put(headerKey, headerValue);
    }

    public String getHeader(String headerKey) {
        return this.get(headerKey);
    }

    public int getStatus() {
        return this.status;
    }

    public Response putBody(String body) {
        this.body = body;
        return this;
    }

    public String getBody() {
        return this.body;
    }

    public Stream<String> streamHeaders() {
        String statusLine = getStatusLine();
        Stream<String> headers = this.keySet().stream().map(key -> stringifyHeader(key));
        return Stream.concat(Stream.of(statusLine), headers);
    }

    public Stream<String> streamHead() {
        return Stream.concat(streamHeaders(), Stream.of(crlf));
    }

    public Stream<String> streamBody() {
        return Stream.concat(Stream.of(body), Stream.of(crlf));
    }

    public Stream<String> streamResponse() {
        Stream<String> head = streamHead();
        if (body == null) {
            return head;
        } else {
            return Stream.concat(head, streamBody());
        }
    }

    public String getStatusLine() {
        String statusCode = String.valueOf(status);
        String statusLine = version + " " + statusCode;
        String message = new StatusCodes().message(status);
        if (message.isEmpty()) {
            return statusLine;
        } else {
            return statusLine + " " + message;
        }
    }

    private String stringifyHeader(String fieldName) {
        return fieldName + ": " + this.get(fieldName);
    }

}

