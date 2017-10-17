package HttpServer;

import java.util.Objects;

public class Server {
    public static void main(String[] args) throws Exception {
        int port = 1337;
        String directory = null;

        CommandLineParser commandLineParser = new CommandLineParser(args, port, directory).invoke();
        port = commandLineParser.getPort();
        directory = commandLineParser.getDirectory();

        // Listening. Opens a server socket, accepts a connection, reads/writes, closes the connection. Repeat.
        SynchronousListener listener = new SynchronousListener(port, directory);
        listener.start();
    }

    // Parsing command line arguments when the server is started.
    private static class CommandLineParser {
        private String[] args;
        private int port;
        private String directory;

        public CommandLineParser(String[] args, int port, String directory) {
            this.args = args;
            this.port = port;
            this.directory = directory;
        }

        public int getPort() {
            return port;
        }

        public String getDirectory() {
            return directory;
        }

        public CommandLineParser invoke() {
            System.out.println("Processing command line arguments");
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
}