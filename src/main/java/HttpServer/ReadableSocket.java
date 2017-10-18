package HttpServer;

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
        System.out.println("\nReadableSocket: received readline() message");
        System.out.println("connection: I am a " + connection.toString());
        System.out.println("Sending readLine() message to connection");
        return connection.readLine();
    }
}
