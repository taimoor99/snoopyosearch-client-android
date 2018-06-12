package com.snoopyo.search.saas;

import java.io.IOException;

/**
 * Any error that was encountered during the processing of a request.
 * Could be server-side, network failure, or client-side.
 */
public class SnoopyoException extends Exception {
    /** HTTP status code. Only valid when the error originates from the server. */
    private int statusCode;

    public SnoopyoException(String message) {
        super(message);
    }

    public SnoopyoException(String message, Throwable throwable)
    {
        super(message, throwable);
    }

    public SnoopyoException(String message, int statusCode)
    {
        super(message);
        this.statusCode = statusCode;
    }

    private static final long serialVersionUID = 1L;

    /**
     * Get the error's HTTP status code (if any).
     * Only valid when the exception is an application-level error. Values are documented in the
     * <a href="https://www.snoopyo.com/doc/rest">REST API</a>.
     *
     * @return The HTTP status code, or 0 if not available.
     */
    public int getStatusCode()
    {
        return statusCode;
    }

    /**
     * Test whether this error is transient.
     *
     * @return true if transient, false if fatal.
     */
    public boolean isTransient() {
        Throwable cause = getCause();
        if (cause == null) {
            return isServerError(statusCode);
        } else if (cause instanceof SnoopyoException) {
            return ((SnoopyoException)cause).isTransient();
        } else if (cause instanceof IOException) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isServerError(int statusCode) {
        return statusCode / 100 == 5;
    }
}
