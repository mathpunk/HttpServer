package HttpServer.core.resource;

import HttpServer.core.router.Router;

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

//    public Response act(Request request) {
//
//    }

//    public void actCobSpecificallyOnParameters(String method) {
//        Function f = (Request request) -> {
//            HashMap<String, String> parameters = request.getParameters();
//            String key1 = "variable_1";
//            String value1 = parameters.get("variable_1");
//            String key2 = "variable_2";
//            String value2 = parameters.get("variable_2");
//            String body1 = key1 + " = " + value1 + "\n";
//            String body2 = key2 + " = " + value2 + "\n";
//            Response response = new Response();
//            response.setStatus(200);
//            response.setBody(body1+body2);
//            return response;
//        };
//        Handler handler = new FunctionalHandler(f);
//        actions.put("GET", handler);
//    }

}
