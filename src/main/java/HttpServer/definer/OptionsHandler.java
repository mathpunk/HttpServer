package HttpServer.definer;

import HttpServer.request.Request;
import HttpServer.response.Response;
import HttpServer.router.Router;

import java.util.ArrayList;

public class OptionsHandler implements Handler {

    private Router router;

    public OptionsHandler(Router router) {
        this.router = router;
    }

    @Override
    public Response respond(Request request) {
        Response response = new Response();
        response.setStatus(200);
        ArrayList<String> allowedMethods;

        if (router != null) {
            allowedMethods = router.getDefinedMethods(request.getUri());
        } else {
            allowedMethods = new ArrayList<>();
        }
        response.setHeader("Allow", String.join(", ", allowedMethods));
        return response;
    }
}
