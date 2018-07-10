package htty.com.github.cjqcn.htty.core.util;

import io.netty.handler.codec.http.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class HttpUtils {

    public static void setContentLength(HttpMessage message, long length) {
        message.headers().setLong(HttpHeaderNames.CONTENT_LENGTH, length);
    }

    /**
     * Set the {@link HttpHeaderNames#TRANSFER_ENCODING} to either include {@link HttpHeaderValues#CHUNKED} if
     * {@code chunked} is {@code true}, or remove {@link HttpHeaderValues#CHUNKED} if {@code chunked} is {@code false}.
     *
     * @param m       The message which contains the headers to modify.
     * @param chunked if {@code true} then include {@link HttpHeaderValues#CHUNKED} in the headers. otherwise remove
     *                {@link HttpHeaderValues#CHUNKED} from the headers.
     */
    public static void setTransferEncodingChunked(HttpMessage m, boolean chunked) {
        if (chunked) {
            m.headers().set(HttpHeaderNames.TRANSFER_ENCODING, HttpHeaderValues.CHUNKED);
            m.headers().remove(HttpHeaderNames.CONTENT_LENGTH);
        } else {
            List<CharSequence> encodings = m.headers().getAll(HttpHeaderNames.TRANSFER_ENCODING);
            if (encodings.isEmpty()) {
                return;
            }
            List<CharSequence> values = new ArrayList<CharSequence>(encodings);
            Iterator<CharSequence> valuesIt = values.iterator();
            while (valuesIt.hasNext()) {
                CharSequence value = valuesIt.next();
                if (HttpHeaderValues.CHUNKED.contentEquals(value)) {
                    valuesIt.remove();
                }
            }
            if (values.isEmpty()) {
                m.headers().remove(HttpHeaderNames.TRANSFER_ENCODING);
            } else {
                m.headers().set(HttpHeaderNames.TRANSFER_ENCODING, values);
            }
        }
    }

    /**
     * Returns the length of the content or the specified default value if the message does not have the {@code
     * "Content-Length" header}. Please note that this value is not retrieved from {@link HttpContent#content()} but
     * from the {@code "Content-Length"} header, and thus they are independent from each other.
     *
     * @param message      the message
     * @param defaultValue the default value
     * @return the content length or the specified default value
     * @throws NumberFormatException if the {@code "Content-Length"} header does not parse as a long
     */
    public static long getContentLength(HttpMessage message, long defaultValue) {
        String value = Optional.of(message.headers().get(HttpHeaderNames.CONTENT_LENGTH).toString())
                .orElse(null);
        if (value != null) {
            return Long.parseLong(value);
        }
        // We know the content length if it's a Web Socket message even if
        // Content-Length header is missing.
        long webSocketContentLength = getWebSocketContentLength(message);
        if (webSocketContentLength >= 0) {
            return webSocketContentLength;
        }
        // Otherwise we don't.
        return defaultValue;
    }

    /**
     * Returns the content length of the specified web socket message. If the
     * specified message is not a web socket message, {@code -1} is returned.
     */
    private static int getWebSocketContentLength(HttpMessage message) {
        // WebSocket messages have constant content-lengths.
        HttpHeaders h = message.headers();
        if (message instanceof HttpRequest) {
            HttpRequest req = (HttpRequest) message;
            if (HttpMethod.GET.equals(req.method()) &&
                    h.contains(HttpHeaderNames.SEC_WEBSOCKET_KEY1) &&
                    h.contains(HttpHeaderNames.SEC_WEBSOCKET_KEY2)) {
                return 8;
            }
        } else if (message instanceof HttpResponse) {
            HttpResponse res = (HttpResponse) message;
            if (res.status().code() == 101 &&
                    h.contains(HttpHeaderNames.SEC_WEBSOCKET_ORIGIN) &&
                    h.contains(HttpHeaderNames.SEC_WEBSOCKET_LOCATION)) {
                return 16;
            }
        }
        // Not a web socket message
        return -1;
    }

}
