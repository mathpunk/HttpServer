package HttpServer;

public class MockSynchronousServer implements ISynchronousServer {

    private int port;

    @Override
    public void nextConnection() {
        port = 5000;
        System.out.println("If I were real, I'd be listening on port " + port);
    }

    @Override
    public String readLine() {
        return null;
    }

    @Override
    public void writeLine(String line) {

    }
}
