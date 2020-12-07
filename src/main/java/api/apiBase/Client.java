package api.apiBase;


import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import ui.base.CONSTANT;
import ui.base.ImdbSearchBase;
import org.apache.http.Header;
import org.apache.http.HttpMessage;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * @author vineetkumar
 * created 06/12/2020
 */
public class Client implements Closeable {

    /**
     * Boolean https used for checking if it is https or http.
     */

    private CloseableHttpClient httpClient;
    private Boolean http;
    private boolean createdHttpClient;

    /**
     * Constructor for using the default CloseableHttpClient.
     */
    public Client() {
        this.httpClient = HttpClients.createDefault();
        this.http = false;
        this.createdHttpClient = true;
    }

    /**
     * Add query parameters to a URL.
     *
     * @param baseUri     (e.g. "api.sendgrid.com")
     * @param endpoint    (e.g. "/endpoint/path")
     * @param queryParams map of key, values representing the query parameters
     * @throws URISyntaxException in of a URI syntax error
     */
    public URI buildUri(String baseUri, String endpoint, Map<String, String> queryParams) throws URISyntaxException, UnsupportedEncodingException {
        URIBuilder builder = new URIBuilder();
        URI uri = null;

        if (this.http == true) {
            builder.setScheme(CONSTANT.HTTP);
        } else {
            builder.setScheme(CONSTANT.HTTPS);
        }

        builder.setHost(baseUri);
        builder.setPath(endpoint);

        if (queryParams != null) {
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                ;
                builder.setParameter(java.net.URLDecoder.decode(entry.getKey(), StandardCharsets.UTF_8.name()), java.net.URLDecoder.decode(entry.getValue(), StandardCharsets.UTF_8.name()));
            }
        }

        try {
            uri = builder.build();
        } catch (URISyntaxException ex) {
            throw ex;
        }

        return uri;
    }

    /**
     * Prepare a Response object from an API call via Apache's HTTP client.
     *
     * @param response from a call to a CloseableHttpClient
     * @return the response object
     * @throws IOException in case of a network error
     */

    public Response getResponse(CloseableHttpResponse response) throws IOException {
        ResponseHandler<String> handler = new ImdbResponseHandler();
        String responseBody = handler.handleResponse(response);

        int statusCode = response.getStatusLine().getStatusCode();

        Header[] headers = response.getAllHeaders();
        Map<String, String> responseHeaders = new HashMap<String, String>();
        for (Header h : headers) {
            responseHeaders.put(h.getName(), h.getValue());
        }
        return new Response(statusCode, responseBody, responseHeaders);
    }

    /**
     * Make a POST request and provide the status code, response body and
     * response headers.
     *
     * @param request the request object
     * @return the response object
     * @throws URISyntaxException in case of a URI syntax error
     * @throws IOException        in case of a network error
     */
    public Response post(Request request) throws URISyntaxException, IOException {
        URI uri = null;
        HttpPost httpPost;

        try {
            uri = buildUri(request.getUri(), request.getEndpoint(), request.getQueryParams());
            httpPost = new HttpPost(uri);
            System.out.println(uri);
        } catch (URISyntaxException ex) {
            throw ex;
        }
        if (request.getHeaders() != null) {
            for (Map.Entry<String, String> entry : request.getHeaders().entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        if (request.getBody() != null) {
            httpPost.setEntity(new StringEntity(request.getBody()));
        }
        writeContentTypeIfNeeded(request, httpPost);
        return executeApiCall(httpPost);
    }

    private void writeContentTypeIfNeeded(Request request, HttpMessage httpMessage) {
        if (!"".equals(request.getBody())) {
            httpMessage.setHeader("Content-Type", "application/json");
        }
    }

    /**
     * Makes a call to the client API.
     *
     * @param httpPost the request method object
     * @return the response object
     * @throws IOException in case of a network error
     */
    private Response executeApiCall(HttpRequestBase httpPost) throws IOException {
        try {
            CloseableHttpResponse serverResponse = httpClient.execute(httpPost);
            try {
                return getResponse(serverResponse);
            } finally {
                serverResponse.close();
            }
        } catch (ClientProtocolException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Closes the http client.
     *
     * @throws IOException in case of a network error
     */
    @Override
    public void close() throws IOException {
        this.httpClient.close();
    }

    /**
     * Closes and finalizes the http client.
     *
     * @throws Throwable in case of an error
     */

    @Override
    public void finalize() throws Throwable {
        try {
            close();
        } catch (IOException e) {
            throw new Throwable(e.getMessage());
        } finally {
            super.finalize();
        }
    }
}


