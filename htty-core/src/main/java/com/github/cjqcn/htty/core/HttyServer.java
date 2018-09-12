package com.github.cjqcn.htty.core;

/**
 * @author jqChan
 */
public interface HttyServer {

    /**
     * Starts the HTTP server.
     *
     * @throws Exception if the server failed to started.
     */
    void start() throws Exception;

    /**
     * Suspends the HTTP server.
     *
     * @throws Exception if the server failed to suspended.
     */
    void suspend() throws Exception;

    /**
     * Recovers the HTTP server
     *
     * @throws Exception if the server failed to recovered in the pause.
     */
    void recover() throws Exception;

    /**
     * Stops the HTTP server.
     *
     * @throws Exception if there is exception raised during shutdown.
     */
    void stop() throws Exception;

}
