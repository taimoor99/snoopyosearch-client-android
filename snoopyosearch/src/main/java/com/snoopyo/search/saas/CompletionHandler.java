package com.snoopyo.search.saas;

import org.json.JSONObject;

/**
 * Handles completion of an API request.
 */
public interface CompletionHandler {

    /**
     * Called when the request has completed, either successfully or failing.
     * <p>
     * NOTE: One and only one of either <code>content</code> or <code>error</code> is guaranteed to be not null.
     * </p>
     *
     * @param content Content that was returned by the API (in case of success).
     * @param error Error that was encountered (in case of failure).
     */
    void requestCompleted(JSONObject content, SnoopyoException error);
}
