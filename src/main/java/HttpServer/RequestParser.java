package HttpServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class RequestParser {

    private ArrayList<String> lines;
    private Readable reading;

    public RequestParser(Readable reading) {
        this.lines = new ArrayList<>();
        this.reading = reading;
    }

    public void read() throws IOException {
        System.out.println("Reading request: ");
        System.out.println("--------------------------");
        String line = reading.readLine();
        do {
            lines.add(line);
            System.out.println(line);
            line = reading.readLine();
        } while (!line.isEmpty());
    }

    public ArrayList<String> compiledRequest() {
        return lines;
    }

    public HashMap request() {
        String requestLine = lines.get(0);
        HashMap request = new HashMap();
        String[] tokens = requestLine.split("\\s+");

        String method = tokens[0];
        request.put("Method", method);

        String path = tokens[1];
        request.put("URL", path);
        return request;
    }

}
