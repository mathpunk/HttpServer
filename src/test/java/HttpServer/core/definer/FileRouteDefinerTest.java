package HttpServer.core.definer;

import HttpServer.core.request.Request;
import HttpServer.core.response.Response;
import HttpServer.core.router.Router;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertEquals;

public class FileRouteDefinerTest {

    private FileRouteDefiner definer;
    private Router router;

    @Before
    public void setup() {
        this.definer = new FileRouteDefiner("./cob_spec/public", new Router());
        this.router = definer.getRouter();
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

    @Ignore
    public void itDefinesRoutesThatRetrieveFileContent() {
        String expectedContent = "default content";
        Request request = new Request();
        String uri = "/patch-content.txt";
        request.setMethod("GET");
        request.setUri(uri);
        Response response = router.route(request);
        assertEquals(expectedContent, response.getBody());
    }

}
