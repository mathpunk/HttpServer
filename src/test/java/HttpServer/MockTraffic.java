package HttpServer;

import HttpServer.socket.Readable;

import java.util.ArrayList;

public class MockTraffic implements Readable {

    private ArrayList<String> simulatedRequest;
    private int index;

    public MockTraffic() {
        simulatedRequest = new ArrayList<>();
    }

    public MockTraffic request(String[] lines) {
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
