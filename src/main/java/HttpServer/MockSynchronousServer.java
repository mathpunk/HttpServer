package HttpServer;

public class MockSynchronousServer implements ISynchronousServer {

    private int port;
    private int index;
    public MockConnection request;

    public MockSynchronousServer(int port) {
        this.port = port;
        request = new MockConnection();
        index = 0;
    }

    @Override
    public void nextConnection() {
        port = 5000;
        System.out.println("If I were real, I'd be listening on port " + port);
        request = new MockConnection();
    }

    @Override
    public String readLine() {
        String line = request.buffer()[index];
        index = index + 1;
        return line;
    }

    @Override
    public void writeLine(String line) {
    }
}
