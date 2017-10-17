package HttpServer;

import java.io.IOException;
import java.util.ArrayList;

public class MockClient implements Writable {

    public ArrayList<String> output;

    public MockClient() {
        this.output = new ArrayList<>();
    }

    @Override
    public void writeLine(String line) throws IOException {
        output.add(line);
    }

    public boolean received(String expectation) {
        return output.stream().anyMatch(output -> expectation.matches(output));
    }

}
