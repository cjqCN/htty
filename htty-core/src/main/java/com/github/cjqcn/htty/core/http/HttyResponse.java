package com.github.cjqcn.htty.core.http;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;


public interface HttyResponse {

    HttpHeaders getHeaders();

    void addHeader(HttpHeaders header);

    void sendJson(HttpResponseStatus status, String jsonString);

    void sendString(HttpResponseStatus status, String data);

    void sendStatus(HttpResponseStatus status);

    void sendByteArray(HttpResponseStatus status, byte[] bytes);

    void sendBytes(HttpResponseStatus status, ByteBuffer buffer);

    ChunkResponder sendChunkStart(HttpResponseStatus status);

    void sendContent(HttpResponseStatus status, ByteBuf content);

    void sendFile(File file) throws IOException;

    void sendContent(HttpResponseStatus status, BodyProducer bodyProducer);
}
