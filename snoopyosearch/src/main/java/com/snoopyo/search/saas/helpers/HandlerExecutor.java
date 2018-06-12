package com.snoopyo.search.saas.helpers;

import android.os.Handler;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * Adapts an Android {@link Handler Handler} into a JRE {@link Executor Executor}.
 * Runnables will be posted asynchronously.
 */
public class HandlerExecutor implements Executor {
    /** Handler wrapped by this executor. */
    private Handler handler;

    /**
     * Construct a new executor wrapping the specified handler.
     *
     * @param handler Handler to wrap.
     */
    public HandlerExecutor(@NonNull Handler handler) {
        this.handler = handler;
    }

    /**
     * Execute a command, by posting it to the underlying handler.
     *
     * @param command Command to execute.
     */
    public void execute(Runnable command) {
        handler.post(command);
    }
}
