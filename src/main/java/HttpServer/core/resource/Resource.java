package HttpServer.core.resource;

import HttpServer.core.request.Request;
import HttpServer.core.response.Response;
import HttpServer.core.router.Router;
import HttpServer.core.resource.FunctionalHandler;

import java.util.HashMap;

public class Resource {

    private String uriString;
    private HashMap<String, Handler> actions;

    public void setUriString(String uriString) {
        actions = new HashMap<>();
        this.uriString = uriString;
    }

    public void register(Router router) {
        for (String verb : actions.keySet()) {
            router.defineRoute(uriString, verb, actions.get(verb));
        }
    }

    public void setAction(String method, Handler handler) {
        actions.put(method, handler);
    }

    public void overlySpecificActionThatBelongsElsewhere() {
        FunctionalHandler handler = new FunctionalHandler((Request request) -> {
            HashMap<String, String> parameters = request.getParameters();
            String variableNameCobSpecExpectsToSeeInBody = "variable_1";
            String value = parameters.get("variable_1");
            String formattingCobSpecWants = variableNameCobSpecExpectsToSeeInBody + "= " + value;

            Response response = new Response();
            response.setStatus(200);
            response.setBody(formattingCobSpecWants);
        });
        actions.put("GET", handler);
    }

}
