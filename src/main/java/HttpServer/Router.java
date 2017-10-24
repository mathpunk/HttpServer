package HttpServer;

public class Router {


    public void defineRoute(String verb, String uri, Response ok) {
    }

    public Response respond(String verb, String uri) {
        return new Response().putStatus(200);
    }
}
