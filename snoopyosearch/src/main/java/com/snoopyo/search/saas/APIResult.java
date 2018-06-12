package com.snoopyo.search.saas;

import android.support.annotation.NonNull;

import org.json.JSONObject;

/**
 * Encapsulates the two possible outcomes of an API request: either a JSON object (success), or an error (failure).
 * One and only one is guaranteed to be non-null.
 */
class APIResult {
    /** The content returned (in case of success). */
    public final JSONObject content;

    /** The error encountered (in case of failure). */
    public final SnoopyoException error;

    /**
     * Construct a new success result.
     *
     * @param content The content returned.
     */
    public APIResult(@NonNull JSONObject content) {
        this.content = content;
        this.error = null;
    }

    /**
     * Construct a new failure result.
     *
     * @param error The error that was encountered.
     */
    public APIResult(@NonNull SnoopyoException error) {
        this.content = null;
        this.error = error;
    }

    /** Test whether this is a success (true) or failure (false) result. */
    public boolean isSuccess() {
        return error == null;
    }
}
