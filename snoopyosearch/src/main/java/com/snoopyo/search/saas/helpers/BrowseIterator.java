package com.snoopyo.search.saas.helpers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.snoopyo.search.saas.SnoopyoException;
import com.snoopyo.search.saas.CompletionHandler;
import com.snoopyo.search.saas.Index;
import com.snoopyo.search.saas.Query;
import com.snoopyo.search.saas.Request;
import com.snoopyo.search.saas.RequestOptions;

import org.json.JSONObject;

/**
 * Iterator to browse all index content.
 * <p>
 * This helper takes care of chaining API requests and calling back the handler block with the results, until:
 * - the end of the index has been reached;
 * - an error has been encountered;
 * - or the user cancelled the iteration.
 */
public class BrowseIterator {

    /**
     * Listener for {@link com.snoopyo.search.saas.helpers.BrowseIterator}.
     */
    public interface BrowseIteratorHandler {
        /**
         * Called at each batch of results.
         *
         * @param iterator The iterator where the results originate from.
         * @param result   The results (in case of success).
         * @param error    The error (in case of error).
         */
        void handleBatch(@NonNull BrowseIterator iterator, JSONObject result, SnoopyoException error);
    }

    /** The index being browsed. */
    private Index index;

    /** The query used to filter the results. */
    private Query query;

    /** Listener. */
    private BrowseIteratorHandler handler;

    /** Eventual request-specific options */
    private @Nullable
    RequestOptions requestOptions;

    /** Cursor to use for the next call, if any. */
    private String cursor;

    /** Whether the iteration has already started. */
    private transient boolean started = false;

    /** Whether the iteration has been cancelled by the user. */
    private transient boolean cancelled = false;

    /** The currently ongoing request, if any. */
    private Request request;

    /**
     * Construct a new browse iterator.
     * NOTE: The iteration does not start automatically. You have to call `start()` explicitly.
     *
     * @param index   The index to be browsed.
     * @param query   The query used to filter the results.
     * @param handler Handler called for each batch of results.
     */
    public BrowseIterator(@NonNull Index index, @NonNull Query query, @NonNull BrowseIteratorHandler handler) {
        this(index, query, null, handler);
    }

    /**
     * Construct a new browse iterator.
     * NOTE: The iteration does not start automatically. You have to call `start()` explicitly.
     *
     * @param index          The index to be browsed.
     * @param query          The query used to filter the results.
     * @param requestOptions Request-specific options.
     * @param handler        Handler called for each batch of results.
     */
    public BrowseIterator(@NonNull Index index, @NonNull Query query, @Nullable RequestOptions requestOptions, @NonNull BrowseIteratorHandler handler) {
        this.index = index;
        this.query = query;
        this.handler = handler;
        this.requestOptions = requestOptions;
    }

    /**
     * Start the iteration.
     */
    public void start() {
        if (started) {
            throw new IllegalStateException();
        }
        started = true;
        request = index.browseAsync(query, requestOptions, completionHandler);
    }

    /**
     * Cancel the iteration.
     * This cancels any currently ongoing request, and cancels the iteration.
     * The listener will not be called after the iteration has been cancelled.
     */
    public void cancel() {
        if (cancelled) {
            return;
        }
        request.cancel();
        request = null;
        cancelled = true;
    }

    /**
     * Determine if there is more content to be browsed.
     * WARNING: Can only be called from the handler, once the iteration has started.
     */
    public boolean hasNext() {
        if (!started) {
            throw new IllegalStateException();
        }
        return cursor != null;
    }

    private void next() {
        if (!hasNext()) {
            throw new IllegalStateException();
        }
        request = index.browseFromAsync(cursor, requestOptions, completionHandler);
    }

    private CompletionHandler completionHandler = new CompletionHandler() {
        @Override
        public void requestCompleted(JSONObject content, SnoopyoException error) {
            if (!cancelled) {
                handler.handleBatch(BrowseIterator.this, content, error);
                if (error == null) {
                    cursor = content.optString("cursor", null);
                    if (!cancelled && hasNext()) {
                        next();
                    }
                }
            }
        }
    };
}
