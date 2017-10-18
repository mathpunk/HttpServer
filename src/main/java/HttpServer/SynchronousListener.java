package HttpServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SynchronousListener {

    private int port;
    private String directory;
    private ArrayList<String> request;

    public SynchronousListener(int port, String directory) {
        this.port = port;
        this.directory = directory;
        this.request = new ArrayList<>();
    }

    public void start() throws IOException {
        System.out.println("Listening on " + port);
        System.out.println("Serving resources at " + directory);
        ServerSocket listener = new ServerSocket(port);

        while(true) {
            System.out.println("Accepting connections.....");
            Socket io = listener.accept();
            System.out.println("Connected\n");

            ReadableSocket reading = new ReadableSocket(io);
            RequestParser parser = new RequestParser(reading);
            parser.read();

            WritableSocket writing = new WritableSocket(io);
            ResponseWriter responder = new ResponseWriter(parser, writing);
            responder.write();

            System.out.println("\nClosing connection\n");
            io.close();
        }
    }
}

