package HttpServer.core.utility.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadableSocket implements Readable {
    private BufferedReader connection;

    public ReadableSocket(Socket io) throws IOException {
        this.connection = new BufferedReader(new InputStreamReader(io.getInputStream()));
    }

    public String readLine() throws IOException {
        return connection.readLine();
    }
}
