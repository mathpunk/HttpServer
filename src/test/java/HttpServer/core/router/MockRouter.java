package HttpServer.core.router;

import java.util.ArrayList;

public class MockRouter extends Router {
    public boolean wasAskedForUris = false;
    public boolean wasAskedForMethods = false;

    public ArrayList<String> getDefinedUris() {
        this.wasAskedForUris = true;
        return super.getDefinedUris();
    }

    public ArrayList<String> getDefinedMethods(String uri) {
        this.wasAskedForMethods= true;
        return super.getDefinedMethods(uri);
    }
}
