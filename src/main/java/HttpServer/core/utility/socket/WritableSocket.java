package HttpServer.core.utility.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class WritableSocket implements Writable {
    private OutputStream byteWriter;
    private PrintWriter lineWriter;

    public WritableSocket(Socket io) throws IOException {
        this.byteWriter = io.getOutputStream();
        this.lineWriter = new PrintWriter(byteWriter, true);
    }

    public void writeLine(String output) {
        lineWriter.println(output);
    }

    public void writeBytes(byte[] bytes) throws IOException {
        byteWriter.write(bytes);
    }

    public void flush() throws IOException {
        lineWriter.flush();
        byteWriter.flush();
    }

    public void close() throws IOException {
        lineWriter.close();
        byteWriter.close();
    }
}
