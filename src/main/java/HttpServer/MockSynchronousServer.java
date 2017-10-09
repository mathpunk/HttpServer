package HttpServer;

public class MockSynchronousServer implements ISynchronousServer {

    private int port;
    private int index;
    public MockConnection connection;

    public MockSynchronousServer(int port) {
        this.port = port;
        connection = new MockConnection();
        index = 0;
    }

    @Override
    public void nextConnection() {
        port = 5000;
        System.out.println("If I were real, I'd be listening on port " + port);
        connection = new MockConnection();
    }

    @Override
    public String readLine() {
        String line = connection.bufferIn[index];
        index = index + 1;
        return line;
    }

    @Override
    public void writeLine(String line) {
        connection.bufferOut.add(line);
    }
}
