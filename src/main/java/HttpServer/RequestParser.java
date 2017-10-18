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
        System.out.println("Constructed new RequestParser with Readable " + source.toString() + " called 'source'");
        String line = source.readLine();
        System.out.println("About to loop, starting with line: " + line.toString());
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
