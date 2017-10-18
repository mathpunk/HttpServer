package HttpServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class RequestParser {

    private ArrayList<String> lines;
    public HashMap request;
    private Readable source;

    public RequestParser(Readable source) {
        this.lines = new ArrayList<>();
        this.request = new HashMap();
        this.source = source;
    }

    public void read() throws IOException {
        System.out.println("Reading request: ");
        System.out.println("--------------------------");
        String line = source.readLine();
        do {
            lines.add(line);
            System.out.println(line);
            line = source.readLine();
        } while (!line.isEmpty());
    }

    public HashMap parse() {
        String requestLine = lines.get(0);
        String[] tokens = requestLine.split("\\s+");

        String method = tokens[0];
        request.put("Method", method);

        String path = tokens[1];
        request.put("URI", path);

        return request;
    }

    public HashMap request() {
        parse();
        return request;
    }

}
