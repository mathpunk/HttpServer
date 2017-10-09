package HttpServer;

public class MockSynchronousServer implements ISynchronousServer {

    private int port;
    public String[] request;
    public String[] response;

    @Override
    public void nextConnection() {
        port = 5000;
        System.out.println("If I were real, I'd be listening on port " + port);
        String[] request = {"GET /index.html HTTP/1.1\r\n",
                "Host: www.example.net\r\n",
                "Accept-Language: en\r\n"};
        String[] response = {};
    }

    @Override
    public String readLine() {
        return null;
    }

    @Override
    public void writeLine(String line) {
    }
}
