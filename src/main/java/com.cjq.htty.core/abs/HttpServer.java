package com.cjq.htty.core.abs;

/**
 *
 *
 * @author jqChan
 */
public interface HttpServer {

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
