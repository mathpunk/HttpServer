package HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SynchronousServer implements ISynchronousServer {

    private int port;
    private ServerSocket listener;
    private Socket connection;
    private BufferedReader request;
    private PrintWriter response;

    public SynchronousServer(int port) {
        this.port = port;
    }

    @Override
    public void nextConnection() throws IOException {
        if(connection != null) {
            connection.close();
        }
        ServerSocket listener = new ServerSocket(port);
        connection = listener.accept();
        request = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        response = new PrintWriter(connection.getOutputStream(), true);
    }

    @Override
    public String readLine() throws IOException {
        return request.readLine();
    }

    @Override
    public void writeLine(String line) {
        response.println(line);
        // response.flush()?
    }
}
