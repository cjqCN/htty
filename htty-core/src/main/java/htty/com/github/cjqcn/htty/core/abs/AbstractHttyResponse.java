/*
 * Copyright © 2014-2017 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package htty.com.github.cjqcn.htty.core.abs;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Base implementation of {@link HttyResponse} to simplify child implementations.
 */
public abstract class AbstractHttyResponse implements HttyResponse {

	protected static final String OCTET_STREAM_TYPE = "application/octet-stream";

	@Override
	public void sendJson(HttpResponseStatus status, String jsonString) {
		sendString(status, jsonString, new DefaultHttpHeaders().add(HttpHeaderNames.CONTENT_TYPE.toString(),
				"application/json"));
	}

	@Override
	public void sendString(HttpResponseStatus status, String data) {
		sendString(status, data, EmptyHttpHeaders.INSTANCE);
	}

	@Override
	public void sendString(HttpResponseStatus status, String data, HttpHeaders headers) {
		if (data == null) {
			sendStatus(status, headers);
			return;
		}
		ByteBuf buffer = Unpooled.wrappedBuffer(StandardCharsets.UTF_8.encode(data));
		sendContent(status, buffer, addContentTypeIfMissing(new DefaultHttpHeaders().add(headers),
				"text/plain; charset=utf-8"));
	}

	@Override
	public void sendStatus(HttpResponseStatus status) {
		sendContent(status, Unpooled.EMPTY_BUFFER, EmptyHttpHeaders.INSTANCE);
	}

	@Override
	public void sendStatus(HttpResponseStatus status, HttpHeaders headers) {
		sendContent(status, Unpooled.EMPTY_BUFFER, headers);
	}

	@Override
	public void sendByteArray(HttpResponseStatus status, byte[] bytes, HttpHeaders headers) {
		ByteBuf buffer = Unpooled.wrappedBuffer(bytes);
		sendContent(status, buffer, headers);
	}

	@Override
	public void sendBytes(HttpResponseStatus status, ByteBuffer buffer, HttpHeaders headers) {
		sendContent(status, Unpooled.wrappedBuffer(buffer), headers);
	}

	@Override
	public void sendFile(File file) throws IOException {
		sendFile(file, EmptyHttpHeaders.INSTANCE);
	}

	@Override
	public ChunkResponder sendChunkStart(HttpResponseStatus status) {
		return sendChunkStart(status, EmptyHttpHeaders.INSTANCE);
	}

	protected final HttpHeaders addContentTypeIfMissing(HttpHeaders headers, String contentType) {
		if (!headers.contains(HttpHeaderNames.CONTENT_TYPE)) {
			headers.set(HttpHeaderNames.CONTENT_TYPE, contentType);
		}

		return headers;
	}
}
