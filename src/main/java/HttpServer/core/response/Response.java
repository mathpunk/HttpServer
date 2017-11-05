package HttpServer.core.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Response extends HashMap<String, String> {

    private final String crlf = "";
    private int status;
    private String version = "HTTP/1.1";
    private String body;
    private HashMap<String, String> headers;

    public Response() {
        this.headers = new HashMap<>();
    }

    public Response setStatus(int code) {
        this.status = code;
        return this;
    }

    public void setVersion(String versionString) {
        this.version = versionString;
    }

    public String getVersion() {
        return version;
    }

    public void setHeader(String headerKey, Object headerValue) {
        headers.put(headerKey, headerValue.toString());
    }

    public String getHeader(String headerKey) {
        return headers.get(headerKey);
    }

    public int getStatus() {
        return this.status;
    }

    public Response setBody(String body) {
        this.body = body;
        return this;
    }

    public String getBody() {
        return this.body;
    }

    public String getStatusLine() {
        String statusCode = String.valueOf(status);
        String statusLine = version + " " + statusCode;
        String message = new StatusCodes().message(status);
        if (message == null || message.isEmpty()) {
            return statusLine;
        } else {
            return statusLine + " " + message;
        }
    }

    private String stringifyHeader(String fieldName) {
        return fieldName + ": " + headers.get(fieldName);
    }

    public ArrayList<String> getHead() {
        ArrayList<String> headerData = new ArrayList<>();
        headerData.add(0, getStatusLine());
        int i = 1;

        Set<String> headerKeys = headers.keySet();
        for (String headerKey : headerKeys) {
            headerData.add(i, stringifyHeader(headerKey));
            i++;
        }
        return headerData;
    }
}

