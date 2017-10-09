package HttpServer;

public class MockConnection {
    private String[] buffer;

    public MockConnection() {
        buffer = new String[]{"GET /index.html HTTP/1.1\r\n",
                "Host: www.example.net\r\n",
                "Accept-Language: en\r\n"};
    }

    public String[] buffer() {
        return buffer;
    }
}
