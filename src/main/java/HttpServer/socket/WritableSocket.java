package HttpServer.socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class WritableSocket implements Writable {
    private PrintWriter connection;

    public WritableSocket(Socket io) throws IOException {
        this.connection = new PrintWriter(io.getOutputStream(), true);
    }

    public void writeLine(String output) {
        connection.println(output);
    }

    public void flush() {
        connection.flush();
    }

    public void close() {
        connection.close();
    }
}
