package HttpServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SynchronousListener {

    private final LoggerInterface logger;
    private int port;
    private String directory;
    private ArrayList<String> request;

    public SynchronousListener(int port, String directory, LoggerInterface logger) {
        this.port = port;
        this.directory = directory;
        this.request = new ArrayList<>();
        this.logger = logger;
    }

    public void start() throws IOException {
        logger.log("Listening on " + port);
        logger.log("Serving resources at " + directory);
        ServerSocket listener = new ServerSocket(port);

        while(true) {
            logger.log("Accepting connections.....");
            Socket io = listener.accept();
            logger.log("Connected\n");

            ReadableSocket reading = new ReadableSocket(io);
            RequestParser parser = new RequestParser(reading, logger);
            parser.read();

            WritableSocket writing = new WritableSocket(io);
            ResponseWriter responder = new ResponseWriter(parser, writing, logger);
            responder.write();

            logger.log("\nClosing connection\n");
            io.close();
        }
    }
}

