package HttpServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class Service {
    public static void main(String[] args) throws Exception {
        int port = 1337;
        String directory = null;

        CommandLineParser commandLineParser = new CommandLineParser(args, port, directory).invoke();
        port = commandLineParser.getPort();
        directory = commandLineParser.getDirectory();

        listenSynchronously(port, directory);
    }

    private static void listenSynchronously(int port, String directory) throws IOException {
        System.out.println("Listening on " + port);
        System.out.println("Serving resources at " + directory);
        ServerSocket listener = new ServerSocket(port);

        while(true) {
            System.out.println("Accepting connections");
            Socket io = listener.accept();

            System.out.println("\nConnected");
            BufferedReader reading = new BufferedReader(new InputStreamReader(io.getInputStream()));
            PrintWriter writing = new PrintWriter(io.getOutputStream(), true);

            System.out.println("Reading request:");
            readHeader(reading);
            respond(writing);

            System.out.println("\nClosing connection\n");
        }
    }

    private static void respond(PrintWriter writing) {
        System.out.println("\nResponding with:");
        writeStatus(writing);
        writeContentType(writing);
        writeContentLength(writing);
        writeEnd(writing);
    }

    private static void writeEnd(PrintWriter writing) {
        System.out.println("<CRLF>");
        writing.println("");
    }

    private static void writeContentLength(PrintWriter writing) {
        String contentLength = "Content-Length: 0";
        writeBothSides(writing, contentLength);
    }

    private static void writeContentType(PrintWriter writing) {
        String contentType = "Content-Type: text/html";
        writeBothSides(writing, contentType);
    }

    private static void writeStatus(PrintWriter writing) {
        String requestLine = "HTTP/1.1 200 OK";
        writeBothSides(writing, requestLine);
    }

    private static void writeBothSides(PrintWriter writing, String message) {
        System.out.println(message);
        writing.println(message);
    }

    private static void readHeader(BufferedReader reading) throws IOException {
        String line = reading.readLine();
        do {
            System.out.println("Read: " + line);
            line = reading.readLine();
        } while (!line.isEmpty());
    }

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
