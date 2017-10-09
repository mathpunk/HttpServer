package HttpServer;

import java.util.ArrayList;

public class MockConnection {
    public String[] bufferIn;
    public ArrayList bufferOut;
    public int index;

    public MockConnection() {
        index = 0;
        bufferOut = new ArrayList(0);
    }

    public void simulateGoodRequest() {
        bufferIn = new String[]{"GET /index.html HTTP/1.1\r\n",
                "Host: www.example.net\r\n",
                "Accept-Language: en\r\n",
                "\r\n"};
    }

    public void simulateUnendingRequest() {
        bufferIn = new String[]{"GET /index.html HTTP/1.1\r\n",
                                "Host: www.example.net\r\n"};
    }

    public String readLine() {
        String line = bufferIn[index];
        index = index +1;
        return line;
    }
    public void println(String line) {
        bufferOut.add(line);
    }

    public void close() {
        System.out.println("Closing fake connection");
    }
}
