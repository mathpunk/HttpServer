package HttpServer;

public class Controller {

    private final Router router;

    public Controller() {
        this.router = new Router(new Routes());
        serveRoot();
        serveForm();
        serveTea();
        serveFiles();
    }

    public void serveRoot() {
        Response ok = new Response().setStatus(200);
        defineRoute("GET", "/", ok);
        defineRoute("HEAD", "/", ok);
    }

    public void serveForm() {
        Response ok = new Response().setStatus(200);
        defineRoute("GET", "/form", ok);
        defineRoute("PUT", "/form", ok);
    }

    public void serveFiles() {
        Response ok = new Response().setStatus(200);
        defineRoute("GET", "/file1", ok);
        defineRoute("GET", "/text-file.txt", ok);

//        put	/file1
//        ensure	response code equals	405

//        bogus Request	/file1
//        ensure	response code equals	405

//        post	/text-file.txt
//        ensure	response code equals	405

//        bogus Request	/file1
//        ensure	response code equals	405

    }

    public void serveTea() {
        Response teaResponse = new Response().setStatus(200);
        Response coffeeResponse = new Response().setStatus(418).putBody("I'm a teapot");

        defineRoute("GET", "/tea", teaResponse);
        defineRoute("GET", "/coffee", coffeeResponse);
    }

    public void defineRoute(String verb, String uri, Response response) {
        RequestHandler handler = new RequestHandler((request) -> response);
        router.defineRoute(uri, verb, handler);
    }

    public Response respond(Request request) {
        String verb = request.getMethod();
        String uri = request.getUri();
        return router.route(uri, verb);
    }
}

