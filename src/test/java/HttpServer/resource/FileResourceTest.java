package HttpServer.definer;

import HttpServer.resource.FileResource;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FileResourceTest {

    private FileResource resource;

    @Before
    public void setup() {
        File directory = new File("./cob_spec/public");
        File file = new File(directory.getAbsolutePath(), "text-file.txt");
        resource = new FileResource(file);
    }

    @Test
    public void itSetsADefaultUri() throws IOException {
        assertEquals("/text-file.txt", resource.getUri());
    }
    
    @Test
    public void itGetsFileContent() throws IOException {
        String content = resource.get();
        String expectedContent = "file1 contents";
        assertEquals(expectedContent, content);
    }
}
