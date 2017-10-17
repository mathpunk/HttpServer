package HttpServer;

import java.util.ArrayList;

public class MockTraffic implements Readable {

    private ArrayList<String> request;

    public MockTraffic() {
    }

    public void emulateCurl() {
        request = new ArrayList<>();
        request.add("GET / HTTP/1.1");
        request.add("Host: localhost:1337");
        request.add("User-Agent: curl/7.56.0");
        request.add("Accept: */*");
    }

    public String readLine() {
        return request.remove(0);
    }

}
