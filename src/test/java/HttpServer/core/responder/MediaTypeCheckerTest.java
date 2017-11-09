package HttpServer.core.responder;

import HttpServer.core.utility.MediaTypeChecker;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class MediaTypeCheckerTest {
    private MediaTypeChecker checker;

    // WARNING: This implementation of a media type checker depends on file extensions being accurate. Files without
    // file extensions will be typed "application/octet-stream", indicating the body contains arbitrary binary data.
    //
    // Reference: https://www.iana.org/assignments/media-types/application/octet-stream

    @Before
    public void setup() {
        this.checker = new MediaTypeChecker();
    }

    @Test
    public void itMapsFileExtensionsToContentTypes() {
        assertEquals("image/jpeg", checker.typeExtension("jpeg"));
        assertEquals("image/png", checker.typeExtension("png"));
        assertEquals("image/gif", checker.typeExtension("gif"));
        assertEquals("text/plain", checker.typeExtension("txt"));
        assertEquals("application/octet-stream", checker.typeExtension("exe"));
    }

    @Test
    public void itMapsFilenamesToTheirStatedContentType() {
        assertEquals("image/jpeg", checker.typeFilename("cat-pix.jpeg"));
    }

    @Test
    public void itTypesUnstatedContentTypesAsOctet() {
        assertEquals("application/octet-stream", checker.typeFilename("verysafefile"));
    }

    @Test
    public void itTypesFiles() {
        File file = new File("cob_spec/public/file1");
        assertEquals("application/octet-stream", checker.typeFile(file));
    }
}
