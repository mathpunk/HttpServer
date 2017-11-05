package HttpServer.cobspec;

import HttpServer.core.SynchronousListener;
import HttpServer.core.definer.CobSpecRouteDefiner;
import HttpServer.core.definer.IRouteDefiner;
import HttpServer.core.utility.CommandLineParser;
import HttpServer.core.utility.Logger;
import HttpServer.core.utility.VerboseLogger;

public class Server {
    public static void main(String[] args) throws Exception {
        int port = 1337;
        String directory = null;
        Logger logger = new VerboseLogger();

        CommandLineParser commandLineParser = new CommandLineParser(args, port, directory, logger).invoke();
        port = commandLineParser.getPort();
        directory = commandLineParser.getDirectory();

        IRouteDefiner definer = new CobSpecRouteDefiner();

        // Listening. Opens a server socket, accepts a connection, reads/writes, closes the connection. Repeat.
        SynchronousListener listener = new SynchronousListener(port, directory, definer, logger);
        listener.start();
    }

}
