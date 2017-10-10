package HttpServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.stream.Stream;

public class Service {
    public static void main(String[] args) throws Exception {
        int port = 5000;
        System.out.println("Listening on " + port);
        ServerSocket listener = new ServerSocket(port);

        while(true) {
            System.out.println("Accepting connections");
            Socket io = listener.accept();

            System.out.println("Connected");
            BufferedReader reading = new BufferedReader(new InputStreamReader(io.getInputStream()));
            PrintWriter writing = new PrintWriter(io.getOutputStream(), true);

            System.out.println("Interacting");
            String line = reading.readLine();
            do {
                System.out.println("I heard: " + line);
                line = reading.readLine();
            } while (!line.isEmpty());

            System.out.println("Responding");

            writing.println("HTTP/1.1 200 OK");
            writing.println("Content-Length: 0");
            writing.println("");

            System.out.println("Closing connection");
        }
    }
}
