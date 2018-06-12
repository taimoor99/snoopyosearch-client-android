package com.snoopyo.search.saas;

/**
 * An API request.
 *
 * Instances of this class encapsulates a sequence of normally one (nominal case), potentially many (in case of retry)
 * network calls into a high-level operation. This operation can be cancelled by the user.
 */
public interface Request
{
    // ==============================================================================================================
    // DISCUSSION: Why don't we use `AsyncTask` directly?
    // --------------------------------------------------
    // `AsyncTask` does not fit our purpose for many reasons:
    //
    // - `AsyncTask` has serial execution, whereas we need parallel execution. We don't want network requests to be
    //   processed serially, especially when searching "as you type"!
    //
    // - Having a custom interface leads to better encapsulation. We can refactor the underlying implementation without
    //   any breaking change.
    //
    // - The public API of `AsyncTask` is very verbose, and does not fit our purpose:
    //     - `cancel(.)` has an extraneous parameter `mayInterruptIfRunning`;
    //     - no `isFinished()` method.
    //
    // - `AsyncTask` does not allow calling the completion handler on another thread than the UI thread.
    //
    // - In theory, `AsyncTask` must be created on the UI thread, making it potentially dangerous to call API methods
    //   from another thread.
    // ==============================================================================================================

    /**
     * Cancel this request.
     * The listener will not be called after a request has been cancelled.
     * <p>
     * WARNING: Cancelling a request may or may not cancel the underlying network call, depending how late the
     * cancellation happens. In other words, a cancelled request may have already been executed by the server. In any
     * case, cancelling never carries "undo" semantics.
     * </p>
     */
     void cancel();

    /**
     * Test if this request is still running.
     *
     * @return true if completed or cancelled, false if still running.
     */
     boolean isFinished();

    /**
     * Test if this request has been cancelled.
     *
     * @return true if cancelled, false otherwise.
     */
     boolean isCancelled();
}
