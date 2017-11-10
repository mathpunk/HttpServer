package HttpServer.core.message.request;

import HttpServer.core.utility.socket.Readable;

import java.util.ArrayList;

public class MockTransmittingSocket implements Readable {

    private ArrayList<String> simulatedRequest;

    public MockTransmittingSocket() {
        simulatedRequest = new ArrayList<>();
    }

    public MockTransmittingSocket request(String[] lines) {
        for (String line : lines) {
           simulatedRequest.add(line);
        }
        simulatedRequest.add("");
        return this;
    }

    public String readLine() {
        return simulatedRequest.remove(0);
    }

}
