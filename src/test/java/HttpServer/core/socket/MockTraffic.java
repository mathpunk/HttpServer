package HttpServer.core.socket;

import java.util.ArrayList;

public class MockTraffic implements Readable {

    private ArrayList<String> simulatedRequest;

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
