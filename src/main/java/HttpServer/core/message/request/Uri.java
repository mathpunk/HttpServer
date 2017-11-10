package HttpServer.core.message.request;

import java.util.HashMap;

public class Uri {

    private final String uriString;

    public Uri(String uriString) {
        this.uriString = uriString;
    }

    public HashMap<String,String> getParameters() {
        PercentDecoder decoder = new PercentDecoder();
        HashMap<String, String> parameters = new HashMap<String, String>();
        String[] tokens = uriString.split("\\?");
        String parameterString = tokens[1];
        String[] keyValueStrings = parameterString.split("&");
        for ( String keyValueString : keyValueStrings) {
            String[] kvPair = keyValueString.split("=");
            String key = decoder.decodeString(kvPair[0]);
            String value = decoder.decodeString(kvPair[1]);
            parameters.put(key, value);
        }
        return parameters;
    }

    public String getPath() {
        String[] tokens = uriString.split("\\?");
        String path = tokens[0];
        return path;
    }

    public String getUriString() {
        return uriString;
    }

}
