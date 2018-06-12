package com.snoopyo.search.saas;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Per-request options.
 * This class allows specifying options at the request level, overriding default options at the {@link Client} level.
 *
 * NOTE: These are reserved for advanced use cases. In most situations, they shouldn't be needed.
 */
public class RequestOptions {
    // Low-level storage
    // -----------------

    /** HTTP headers, as untyped values. */
    @NonNull
    Map<String, String> headers = new HashMap<>();

    /**
     * URL parameters, as untyped values.
     * These will go into the query string part of the URL (after the question mark).
     */
    @NonNull
    Map<String, String> urlParameters= new HashMap<>();

    /**
     * Set a HTTP header (untyped version).
     * Whenever possible, you should use a typed accessor.
     *
     * @param name Name of the header.
     * @param value Value of the header, or `null` to remove the header.
     */
    public RequestOptions setHeader(@NonNull String name, @Nullable String value) {
        if (value == null) {
            headers.remove(name);
        } else {
            headers.put(name, value);
        }
        return this;
    }

    /**
     * Get the value of a HTTP header.
     *
     * @param name Name of the header.
     * @return Value of the header, or `null` if it does not exist.
     */
    public String getHeader(@NonNull String name) {
        return headers.get(name);
    }

    /**
     * Set a URL parameter (untyped version).
     * Whenever possible, you should use a typed accessor.
     *
     * @param name Name of the parameter.
     * @param value Value of the parameter, or `null` to remove it.
     */
    public RequestOptions setUrlParameter(@NonNull String name, @Nullable String value) {
        if (value == null) {
            urlParameters.remove(name);
        } else {
            urlParameters.put(name, value);
        }
        return this;
    }

    /**
     * Get the value of a URL parameter.
     *
     * @param name Name of the parameter.
     * @return Value of the parameter, or `null` if it does not exist.
     */
    public String getUrlParameter(@NonNull String name) {
        return urlParameters.get(name);
    }

    // Debug
    // -----

    @Override
    public @NonNull String toString() {
        return String.format("%s{headers: %s}", this.getClass().getSimpleName(), this.headers);
    }

    // Comparison
    // ----------

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RequestOptions)) return false;
        RequestOptions another = (RequestOptions)obj;
        return this.headers.equals(another.headers);
    }

    // Construction
    // ------------

    /**
     * Construct empty request options.
     */
    public RequestOptions() {
    }
}
