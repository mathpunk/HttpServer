package HttpServer;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ResourceTest {
    @Test
    public void itHasAURI() {
        Resource resource = new Resource();
        resource.setUri("/");
        assertEquals(resource.getUri(), "/");
    }

    @Test
    public void itCanAllowMethods() {
        Resource resource = new Resource();
        resource.setUri("/");
        resource.allowMethod("GET");
        ArrayList<String> methods = resource.allowedMethods();
        assert(methods.contains("GET"));

    }
}
