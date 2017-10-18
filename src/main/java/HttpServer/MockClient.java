package HttpServer;

import java.io.IOException;
import java.util.ArrayList;

public class MockClient implements Writable {

    public ArrayList<String> output;
    public boolean gotWrites;

    public MockClient() {
        this.gotWrites = false;
        this.output = new ArrayList<>();
    }

    @Override
    public void writeLine(String line) throws IOException {
        gotWrites = true;
        output.add(line);
    }

    public void flush() {
    }

    public boolean received(String expectation) {
        return output.stream().anyMatch(output -> expectation.matches(output));
    }

}
