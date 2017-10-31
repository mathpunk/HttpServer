package HttpServer.socket;

import HttpServer.socket.Writable;

import java.io.IOException;
import java.util.ArrayList;

public class MockClient implements Writable {

    public ArrayList<String> output;
    public boolean writeLineReceived;

    public MockClient() {
        this.writeLineReceived = false;
        this.output = new ArrayList<>();
    }

    @Override
    public void writeLine(String line) throws IOException {
        writeLineReceived = true;
        output.add(line);
    }

    public void flush() { }

    public void close() { }
}
