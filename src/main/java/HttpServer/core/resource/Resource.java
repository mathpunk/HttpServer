package HttpServer.core.resource;

import HttpServer.core.request.Uri;
import HttpServer.core.response.Response;
import HttpServer.core.router.Router;
import HttpServer.core.utility.Clause;

import java.util.ArrayList;
import java.util.HashMap;

public interface Resource {

    ArrayList<Clause> clausesHandled();

    // TODO: The below is a 'maybe'; users should use clausesHandled for now.
    // void register(Router router, Uri endpoint);

    Response action(Clause clause);

}

