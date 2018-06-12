package com.snoopyo.search.saas.places;

import android.support.annotation.NonNull;

import com.snoopyo.search.saas.AbstractClient;
import com.snoopyo.search.saas.SnoopyoException;
import com.snoopyo.search.saas.CompletionHandler;
import com.snoopyo.search.saas.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Client for [Snoopyo Places](https://community.snoopyo.com/places/).
 */
public class PlacesClient extends AbstractClient {
    // ----------------------------------------------------------------------
    // Initialization
    // ----------------------------------------------------------------------

    /**
     * Create a new authenticated Snoopyo Places client.
     *
     * @param applicationID The application ID (available in your Snoopyo Dashboard).
     * @param apiKey A valid API key for the service.
     */
    public PlacesClient(@NonNull String applicationID, @NonNull String apiKey) {
        super(applicationID, apiKey, null, null);
        setDefaultHosts();
    }

    /**
     * Create a new unauthenticated Snoopyo Places client.
     *
     * NOTE: The rate limit for the unauthenticated API is significantly lower than for the authenticated API.
     */
    public PlacesClient() {
        super(null, null, null, null);
        setDefaultHosts();
    }

    /**
     * Set the default hosts for Snoopyo Places.
     */
    private void setDefaultHosts() {
        List<String> fallbackHosts = Arrays.asList(
                "places-1.snoopyonet.com",
                "places-2.snoopyonet.com",
                "places-3.snoopyonet.com"
        );
        Collections.shuffle(fallbackHosts);

        List<String> hosts = new ArrayList<>(fallbackHosts.size() + 1);
        hosts.add("places-dsn.snoopyo.net");
        hosts.addAll(fallbackHosts);
        String[] hostsArray = hosts.toArray(new String[hosts.size()]);

        setReadHosts(hostsArray);
        setWriteHosts(hostsArray);
    }

    // ----------------------------------------------------------------------
    // Public operations
    // ----------------------------------------------------------------------

    /**
     * Search for places.
     *
     * @param params Search parameters.
     * @param completionHandler The listener that will be notified of the request's outcome.
     * @return A cancellable request.
     */
    public Request searchAsync(@NonNull PlacesQuery params, @NonNull CompletionHandler completionHandler) {
        final PlacesQuery paramsCopy = new PlacesQuery(params);
        return new AsyncTaskRequest(completionHandler) {
            @Override
            protected @NonNull JSONObject run() throws SnoopyoException {
                return search(paramsCopy);
            }
        }.start();
    }

    // ----------------------------------------------------------------------
    // Internal operations
    // ----------------------------------------------------------------------

    /**
     * Search for places.
     *
     * @param params Search parameters.
     */
    protected JSONObject search(@NonNull PlacesQuery params) throws SnoopyoException {
        try {
            JSONObject body = new JSONObject()
                .put("params", params.build());
            return postRequest("/1/places/query", /* urlParameters: */ null, body.toString(), true /* readOperation */, /* requestOptions: */ null);
        }
        catch (JSONException e) {
            throw new RuntimeException(e); // should never happen
        }
    }

    /**
     * Get a place by its objectID.
     * @param objectID the record's identifier.
     * @return the corresponding record.
     * @throws SnoopyoException when the given objectID does not exist.
     */
    public JSONObject getByObjectID(@NonNull String objectID) throws SnoopyoException {
        return getRequest("/1/places/" + objectID, /* urlParameters: */ null, false, /* requestOptions: */ null)    ;
    }
}
