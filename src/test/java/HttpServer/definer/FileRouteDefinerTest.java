package HttpServer.definer;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;

public class FileRouteDefinerTest {

    @Test
    public void itFindsItsDirectory() {
        FileRouteDefiner definer = new FileRouteDefiner("./cob_spec/public");
        File directory = definer.getDirectory();
        assert(directory.isDirectory());
    }

    @Test
    public void itCanListFileNames() {
        FileRouteDefiner definer = new FileRouteDefiner("./cob_spec/public");
        ArrayList<String> files = definer.listFileNames();
        assertThat(files, hasItem("file1"));
    }

    @Test
    public void itCanAccessFileContent() {
        
    }
}
