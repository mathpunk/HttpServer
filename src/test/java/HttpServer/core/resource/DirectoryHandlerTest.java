package HttpServer.core.resource;

import HttpServer.core.request.Request;
import HttpServer.core.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertEquals;

public class DirectoryHandlerTest {

    private DirectoryHandler directoryHandler;

    @Before
    public void setup() {
        this.directoryHandler = new DirectoryHandler("./cob_spec/public");
    }

    @Test
    public void itListsFilenamesServed() {
        ArrayList<String> names = directoryHandler.fileNames();
        assertThat(names, hasItems( "file1", "file2", "image.gif",
                "image.jpeg", "image.png", "partial_content.txt",
                "patch-content.txt", "text-file.txt" ));
    }

    @Test
    public void it404sIfFileDoesNotExist() {
        Request request = new Request();
        request.setUri("/i-dont-have-this-file");
        request.setMethod("GET");
        Response response = directoryHandler.respond(request);
        assertEquals(404, response.getStatus());
    }

    @Test
    public void itIsOkIfFileExists() {
        Request request = new Request();
        request.setUri("/file1");
        request.setMethod("GET");
        Response response = directoryHandler.respond(request);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void itRespondsToGetWithBodySetToContent() {
        Request request = new Request();
        request.setUri("/file1");
        request.setMethod("GET");
        Response response = directoryHandler.respond(request);
        assertEquals("file1 contents", response.getBody());
    }
}
