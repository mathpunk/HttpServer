package HttpServer.core.utility;

import HttpServer.core.message.Uri;

public class Clause {

    // Wikipedia, 'clause': "In grammar, a clause is the smallest grammatical unit that can express a complete proposition.
    // A typical clause consists of a subject and a predicate, the latter typically a verb phrase...

    private final Uri uri;
    private final String method;

    public Clause(String uriString, String method) {
        this.uri = new Uri(uriString);
        this.method = method;
    }

    public Clause(Uri uri, String method) {
        this.uri = uri;
        this.method = method;
    }

    public String getUriString() {
        return uri.getUriString();
    }

    public String getMethod() {
        return method;
    }

    public Uri getUri() {
        return uri;
    }
}
