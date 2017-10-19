package HttpServer;

public interface LoggerInterface {
    public void log(String string);
}

class ServerLog implements LoggerInterface {
    public void log(String string) {
        System.out.println(string);
    }
}

class TestLog implements LoggerInterface {
    public void log(String string) {
        // no op
    }
}

