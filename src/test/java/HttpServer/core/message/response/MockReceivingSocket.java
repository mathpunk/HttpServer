package HttpServer.core.message.response;

import HttpServer.core.utility.socket.Writable;

import java.io.IOException;
import java.util.ArrayList;

public class MockReceivingSocket implements Writable {

    public ArrayList<String> linesReceived;
    public ArrayList<Byte> bytesReceived;
    public boolean writeLineReceived;
    public boolean writeByteReceived;

    public MockReceivingSocket() {
        this.writeLineReceived = false;
        this.writeByteReceived = false;
        this.linesReceived = new ArrayList<>();
    }

    @Override
    public void writeLine(String line) throws IOException {
        writeLineReceived = true;
        linesReceived.add(line);
    }

    @Override
    public void writeBytes(byte[] bytes) throws IOException {
        writeByteReceived = true;
    }

    public void flush() { }

    public void close() { }
}
