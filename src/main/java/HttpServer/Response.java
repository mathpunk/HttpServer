package HttpServer;

import java.util.HashMap;

public class Response extends HashMap<String,String> {
    public Response() { }

    public Response ok() {
        this.put("HTTP Version", "HTTP/1.1");
        this.put("Status Code", "200");
        this.put("Status Message", "OK");
        return this;
    }

    public Response notFound() {
        this.put("HTTP Version", "HTTP/1.1");
        this.put("Status Code", "404");
        this.put("Status Message", "Not Found");
        return this;
    }

    public Response teapot() {
        this.put("HTTP Version", "HTTP/1.1");
        this.put("Status Code", "418");
        return this;
    }

    public String statusLine() {
        String version = this.get("HTTP Version");
        String code = this.get("Status Code");
        String message = this.get("Status Message");
        return version + " " + code + " " + message;
    }

}
