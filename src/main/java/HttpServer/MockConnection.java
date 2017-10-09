package HttpServer;

import java.util.ArrayList;

public class MockConnection {
    public String[] bufferIn;
    public ArrayList bufferOut;

    public MockConnection() {
        bufferIn = new String[]{"GET /index.html HTTP/1.1\r\n",
                "Host: www.example.net\r\n",
                "Accept-Language: en\r\n"};
        bufferOut = new ArrayList(0);
    }
}
