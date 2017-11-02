package HttpServer.definer;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

public class FileRouteDefinerTest {

    @Test
    public void itCanHaveADirectory() {
        // Use https://docs.oracle.com/javase/8/docs/api/java/nio/file/Path.html instead of Strings??
        FileRouteDefiner controller = new FileRouteDefiner("./cob_spec/public");
        String directory = controller.getDirectory();
        assertThat(directory, containsString("cob_spec"));
        assertThat(directory, containsString("public"));
    }

    @Test
    public void itCanListAFileName() {
        FileRouteDefiner controller = new FileRouteDefiner("./cob_spec/public");
        String[] files = controller.listFileNames();
        assertThat(Arrays.asList(files), hasItem("file1"));
    }

    @Test
    public void itCanListAnotherFileName() {
        FileRouteDefiner controller = new FileRouteDefiner("./cob_spec/public");
        String[] files = controller.listFileNames();
        assertThat(Arrays.asList(files), hasItem("text-file.txt"));
    }

}
