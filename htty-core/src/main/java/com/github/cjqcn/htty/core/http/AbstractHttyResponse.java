package com.github.cjqcn.htty.core.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Base implementation of {@link HttyResponse} to simplify child implementations.
 */
public abstract class AbstractHttyResponse implements HttyResponse {

    protected static final String OCTET_STREAM_TYPE = "application/octet-stream";

    @Override
    public void sendJson(HttpResponseStatus status, String jsonString) {
        addHeader(new DefaultHttpHeaders().add(HttpHeaderNames.CONTENT_TYPE.toString(),
                "application/json"));
        sendString(status, jsonString);
    }

    @Override
    public void sendString(HttpResponseStatus status, String data) {
        if (data == null) {
            sendStatus(status);
            return;
        }
        addContentTypeIfMissing(getHeaders(), "text/plain; charset=utf-8");
        ByteBuf buffer = Unpooled.wrappedBuffer(StandardCharsets.UTF_8.encode(data));
        sendContent(status, buffer);
    }

    @Override
    public void sendStatus(HttpResponseStatus status) {
        sendContent(status, Unpooled.EMPTY_BUFFER);
    }

    @Override
    public void sendByteArray(HttpResponseStatus status, byte[] bytes) {
        sendContent(status, Unpooled.wrappedBuffer(bytes));
    }

    @Override
    public void sendBytes(HttpResponseStatus status, ByteBuffer buffer) {
        sendContent(status, Unpooled.wrappedBuffer(buffer));
    }

    protected final HttpHeaders addContentTypeIfMissing(HttpHeaders headers, String contentType) {
        if (!headers.contains(HttpHeaderNames.CONTENT_TYPE)) {
            headers.set(HttpHeaderNames.CONTENT_TYPE, contentType);
        }
        return headers;
    }
}
