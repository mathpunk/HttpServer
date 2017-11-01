package HttpServer.controller;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

public class FileControllerTest {

    @Test
    public void itCanHaveADirectory() {
        // Use https://docs.oracle.com/javase/8/docs/api/java/nio/file/Path.html instead of Strings??
        FileController controller = new FileController("./cob_spec/public");
        String directory = controller.getDirectory();
        assertThat(directory, containsString("cob_spec"));
        assertThat(directory, containsString("public"));
    }

//    @Ignore
//    public void itCanListFileNames() {
//        FileController controller = new FileController("./cob_spec/public");
//        String[] files = controller.listFileNames();
////        Assertion about the collection goes here
////        but I'm having trouble importing an appropriate hamcrest matcher
//    }
//
}
