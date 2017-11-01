package HttpServer;

import HttpServer.utility.CommandLineParser;
import HttpServer.utility.Logger;
import HttpServer.utility.VerboseLogger;

public class Server {
    public static void main(String[] args) throws Exception {
        int port = 1337;
        String directory = null;
        Logger logger = new VerboseLogger();

        CommandLineParser commandLineParser = new CommandLineParser(args, port, directory, logger).invoke();
        port = commandLineParser.getPort();
        directory = commandLineParser.getDirectory();

        // Listening. Opens a server socket, accepts a connection, reads/writes, closes the connection. Repeat.
        SynchronousListener listener = new SynchronousListener(port, directory, logger);
        listener.start();
    }

}
