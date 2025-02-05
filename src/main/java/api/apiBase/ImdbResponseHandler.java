package api.apiBase;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpResponseException;
import org.apache.http.util.EntityUtils;

import java.nio.charset.StandardCharsets;

import org.apache.http.client.ResponseHandler;


/**
 * @author vineetkumar
 * created 6/12/2020
 * ImdbResponseHandler class for handling response by implementing ResponseHandler interface.
 */
public class ImdbResponseHandler implements ResponseHandler<String> {

    /**
     * Read the entity from the response body and pass it to the entity handler
     * method if the response was successful (a 2xx status code). If no response
     * body exists, this returns null. If the response was unsuccessful (&gt;= 500
     * status code), throws an {@link HttpResponseException}.
     */

    @Override
    public String handleResponse(final HttpResponse response)
            throws HttpResponseException, IOException {
        final HttpEntity entity = response.getEntity();
        return entity == null ? null : handleEntity(entity);
    }

    public String handleEntity(HttpEntity entity) throws IOException {
        return EntityUtils.toString(entity, StandardCharsets.UTF_8);
    }

}