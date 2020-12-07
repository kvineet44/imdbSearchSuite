package api.apiBase;

import java.util.Map;
import java.util.HashMap;

/**
 * @author vineetkumar
 * created 06/12/2020
 * Class Response provides a standard interface to an API's HTTP request.
 */

public class Request {
    private Method method;
    private String uri;
    private String endpoint;
    private String body = null;
    private Map<String, String> headers;
    private final Map<String, String> queryParams;

    @Override
    public String toString() {
        return "Request{" +
                "method=" + method +
                ", uri='" + uri + '\'' +
                ", endpoint='" + endpoint + '\'' +
                ", body='" + body + '\'' +
                ", headers=" + headers +
                ", queryParams=" + queryParams +
                '}';
    }

    public Request(String uri, Method method, Map<String, String> headers, Map<String, String> queryParams) {
        this.method = method;
        this.uri = uri;
        if (headers != null)
            this.headers = headers;
        else this.headers = new HashMap<>();
        if (queryParams != null)
            this.queryParams = queryParams;
        else this.queryParams = new HashMap<String, String>();
    }

    /**
     * Place the object into an empty state.
     */
    public void reset() {
        this.clearMethod();
        this.clearBaseUri();
        this.clearEndpoint();
        this.clearBody();
        this.clearHeaders();
        this.clearQueryParams();
    }

    public void addQueryParam(String key, String value) {
        this.queryParams.put(key, value);
    }


    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public String removeQueryParam(String key) {
        return this.queryParams.remove(key);
    }

    public String removeHeader(String key) {
        return this.headers.remove(key);
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public Map<String, String> getQueryParams() {
        return this.queryParams;
    }

    public Method getMethod() {
        return this.method;
    }

    public String getUri() {
        return this.uri;
    }

    public String getEndpoint() {
        return this.endpoint;
    }

    public String getBody() {
        return this.body;
    }

    public void clearMethod() {
        this.method = null;
    }

    public void clearBaseUri() {
        this.uri = "";
    }

    public void clearEndpoint() {
        this.endpoint = "";
    }

    public void clearBody() {
        this.body = "";
    }

    public void clearQueryParams() {
        this.queryParams.clear();
    }

    public void clearHeaders() {
        this.headers.clear();
    }
}