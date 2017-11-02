package HttpServer.definer;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertEquals;

public class FileRouteDefinerTest {

    private FileRouteDefiner definer;

    @Before
    public void setup() {
        this.definer = new FileRouteDefiner("./cob_spec/public");
    }

    @Test
    public void itFindsItsDirectory() {
        File directory = definer.getDirectory();
        assert(directory.isDirectory());
    }

    @Test
    public void itCanListFileNames() {
        ArrayList<String> files = definer.listFileNames();
        assertThat(files, hasItems("file1", "image.gif", "image.png", "patch-content.txt",
                "file2", "image.jpeg", "partial_content.txt", "text-file.txt"));
    }

    @Test
    public void itCanAccessFileContent() throws IOException {
        String filename = "text-file.txt";
        String content = definer.getFileContent(filename);
        String expectedContent = "file1 contents";
        assertEquals(expectedContent, content);
    }
}
