package HttpServer.core.resource;

import HttpServer.core.request.Request;
import HttpServer.core.response.Response;
import HttpServer.core.router.Router;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertEquals;

public class DirectoryHandlerTest {

    private DirectoryHandler directoryHandler;
    private Request fileRequest;

    @Before
    public void setup() {
        this.directoryHandler = new DirectoryHandler("cob_spec/public");
        Request fileRequest = new Request();
        fileRequest.setUri("/file1");
        fileRequest.setMethod("GET");
        this.fileRequest = fileRequest;
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
        Response response = directoryHandler.respond(fileRequest);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void itRespondsToGetWithBodySetToContent() {
        Response response = directoryHandler.respond(fileRequest);
        assertEquals("file1 contents", response.getBody());
    }

    @Test
    public void itRespondsWithContentTypeSet() {
        Response response = directoryHandler.respond(fileRequest);
        assertEquals("application/octet-stream", response.getHeader("Content-Type"));
    }

}
