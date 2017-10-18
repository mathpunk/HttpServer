package HttpServer;

import java.util.ArrayList;

public class MockTraffic implements Readable {

    private ArrayList<String> request;
    private int index;

    public MockTraffic() {
    }

    public void emulateSimpleCurl() {
        request = new ArrayList<>(1);
        request.add("GET / HTTP/1.1");
        request.add("Host: localhost:1337");
        request.add("User-Agent: curl/7.56.0");
        request.add("Accept: */*");
        request.add("");
    }

    public void emulateFaviconCurl() {
        request = new ArrayList<>(1);
        request.add("GET /favicon.ico HTTP/1.1");
        request.add("Host: localhost:1337");
        request.add("User-Agent: curl/7.56.0");
        request.add("Accept: */*");
        request.add("");
    }

    public String readLine() {
        return request.remove(0);
    }

}
