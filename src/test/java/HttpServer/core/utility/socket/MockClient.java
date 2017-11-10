package HttpServer.core.utility.socket;

import java.io.IOException;
import java.util.ArrayList;

public class MockClient implements Writable {

    public ArrayList<String> linesReceived;
    public ArrayList<Byte> bytesReceived;
    public boolean writeLineReceived;
    public boolean writeByteReceived;

    public MockClient() {
        this.writeLineReceived = false;
        this.writeByteReceived = false;
        this.linesReceived = new ArrayList<>();
        this.bytesReceived = new ArrayList<Byte>();
    }

    @Override
    public void writeLine(String line) throws IOException {
        writeLineReceived = true;
        linesReceived.add(line);
    }

    @Override
    public void writeByte(byte bite) throws IOException {
        writeByteReceived = true;
        bytesReceived.add(bite);
    }

    public void flush() { }

    public void close() { }
}
