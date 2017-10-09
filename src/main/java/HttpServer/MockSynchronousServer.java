package HttpServer;

public class MockSynchronousServer implements ISynchronousServer {

    private int port;
    private int index;
    public MockConnection connection;

    public MockSynchronousServer(int port) {
        this.port = port;
        index = 0;
    }

    public void simulateGoodRequest() {
        connection = new MockConnection();
        connection.simulateGoodRequest();
    }

    public void simulateUnendingRequest() {
        connection = new MockConnection();
        connection.simulateUnendingRequest();
    }

    @Override
    public void nextConnection() {
        if (connection != null) {
            connection.close();
        }
        port = 5000;
        System.out.println("Fake-listening on port " + port);
        connection = new MockConnection();
    }

    @Override
    public String readLine() {
        String line = connection.readLine();
        return line;
    }

    @Override
    public void writeLine(String line) {
        connection.println(line);
    }
}
