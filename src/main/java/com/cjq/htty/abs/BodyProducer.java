package com.cjq.htty.abs;

import io.netty.buffer.ByteBuf;

/**
 * Class for producing response body in streaming fashion.
 */
public abstract class BodyProducer {

    /**
     * Returns the size of the content in bytes that this producer is going to produce.
     * <p>
     * If a negative number is returned, the size is unknown and the {@code Content-Length} header
     * won't be set and {@code Transfer-Encoding: chunked} will be used.
     * </p>
     * By default, {@code -1L} is returned.
     */
    public long getContentLength() {
        return -1L;
    }

    /**
     * Returns a {@link ByteBuf} representing the next chunk of bytes to send. If the returned
     * {@link ByteBuf} is an empty buffer, it signals the end of the streaming.
     *
     * @throws Exception if there is any error
     */
    public abstract ByteBuf nextChunk() throws Exception;

    /**
     * This method will get called after the last chunk of the body get sent successfully.
     *
     * @throws Exception if there is any error
     */
    public abstract void finished() throws Exception;

    /**
     * This method will get called if there is any error raised when sending body chunks. This method will
     * also get called if there is exception raised from the {@link #nextChunk()} or {@link #finished()} method.
     *
     * @param cause the reason of the failure or {@code null} if the reason of failure is unknown.
     */
    public abstract void handleError(Throwable cause);
}
