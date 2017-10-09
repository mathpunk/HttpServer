package HttpServer;

import java.util.ArrayList;

public class RequestResponse {

    private String[] request;

    public RequestResponse() {
    }

    public ArrayList[] getResponse(MockConnection connection) {
        ArrayList[] response = new ArrayList[0];
        return response;
    }

    public boolean requestOver() {
        return false;
    }
}
