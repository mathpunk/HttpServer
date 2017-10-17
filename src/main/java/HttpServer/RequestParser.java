package HttpServer;

import java.io.IOException;
import java.util.ArrayList;

public class RequestParser {

    private ArrayList<String> requestLines; // Associative data structure?
    private Readable reading;

    public RequestParser(Readable reading) {
        this.requestLines = new ArrayList<>();
        this.reading = reading;
    }

    public void read() throws IOException {
        System.out.println("Reading request: ");
        System.out.println("--------------------------");
        String line = reading.readLine();
        do {
            line = reading.readLine();
            requestLines.add(line);
            System.out.println(line);
        } while (!line.isEmpty());
    }

}
