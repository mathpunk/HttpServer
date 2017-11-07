package HttpServer.core.utility;

import HttpServer.core.utility.logger.Logger;

import java.util.Objects;

public class CommandLineParser {
    private final Logger logger;
    private String[] args;
    private int port;
    private String directory;

    public CommandLineParser(String[] args, int port, String directory, Logger logger) {
        this.args = args;
        this.port = port;
        this.directory = directory;
        this.logger = logger;
    }

    public int getPort() {
        return port;
    }

    public String getDirectory() {
        return directory;
    }

    public CommandLineParser invoke() {
        logger.log("Processing command line arguments");
        for (int i=0; i < args.length; i++) {
            String token = args[i];
            if (Objects.equals(token, "-p")) {
                port = Integer.parseInt(args[i+1]);
            } else if (token.equals("-d")) {
                directory = args[i+1];
            }
        }
        return this;
    }
}
