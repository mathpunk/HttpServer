package HttpServer.core.response;

import HttpServer.core.Message;

import java.util.ArrayList;
import java.util.Set;

public class Response extends Message {

    private int status;

    public Response() {
        super();
    }

    public Response(int code) {
        super();
        this.status = code;
    }

    public void setStatus(int code) {
        this.status = code;
    }

    public int getStatus() {
        return this.status;
    }

    public String getStatusLine() {
        StringBuilder statusLine = new StringBuilder();
        statusLine.append(version + " ");
        statusLine.append(String.valueOf(status));
        String message = new StatusCodes().message(status);
        if (message != null && !message.isEmpty()) {
            statusLine.append(" " + message);
        }
        return statusLine.toString();
    }

    public ArrayList<String> getHead() {
        ArrayList<String> headData = new ArrayList<>();
        headData.add(getStatusLine());
        Set<String> headerKeys = headers.keySet();
        for (String headerKey : headerKeys) {
            headData.add(getHeaderAsString(headerKey));
        }
        return headData;
    }
}

