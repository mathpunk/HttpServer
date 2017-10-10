package HttpServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.stream.Stream;

public class Service {
    public static void main(String[] args) throws Exception {
        int port = 1337;
        String directory = null;

        System.out.println("Processing command line arguments");
        for (int i=0; i < args.length; i++) {
            String token = args[i];
            if (Objects.equals(token, "-p")) {
                port = Integer.parseInt(args[i+1]);
            } else if (token.equals("-d")) {
                directory = args[i+1];
            }
        }

        System.out.println("Listening on " + port);
        System.out.println("Serving resources at " + directory);
        ServerSocket listener = new ServerSocket(port);

        while(true) {
            System.out.println("Accepting connections");
            Socket io = listener.accept();

            System.out.println("\nConnected");
            BufferedReader reading = new BufferedReader(new InputStreamReader(io.getInputStream()));
            PrintWriter writing = new PrintWriter(io.getOutputStream(), true);

            System.out.println("Request:");
            String line = reading.readLine();
            do {
                System.out.println("I heard: " + line);
                line = reading.readLine();
            } while (!line.isEmpty());

            System.out.println("\nResponding with:");

            String requestLine = "HTTP/1.1 200 OK";
            System.out.println(requestLine);
            writing.println(requestLine);

            String contentType = "Content-Type: text/html";
            System.out.println(contentType);
            writing.println(contentType);

            String contentLength = "Content-Length: 0";
            System.out.println(contentLength);
            writing.println(contentLength);

            System.out.println("<CRLF>");
            writing.println("");

            System.out.println("\nClosing connection\n");
        }
    }
}
