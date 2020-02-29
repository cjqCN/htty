package com.github.cjqcn.htty.core.http;

import com.github.cjqcn.htty.core.http.cookie.CookieBuilder;
import com.github.cjqcn.htty.core.http.cookie.HttyCookie;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import io.netty.util.CharsetUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

public class BasicHttyRequest implements HttyRequest {

    private final FullHttpRequest fullHttpRequest;

    private volatile HttyCookie[] cookies;

    private volatile Map<String, String> headers;

    private volatile Map<String, String> params;

    public BasicHttyRequest(FullHttpRequest fullHttpRequest) {
        this.fullHttpRequest = fullHttpRequest;
        cookies = null;
        headers = null;
        params = null;
    }

    @Override
    public HttyMethod method() {
        if (fullHttpRequest.method() == HttpMethod.GET) {
            return HttyMethod.GET;
        }
        if (fullHttpRequest.method() == HttpMethod.POST) {
            return HttyMethod.POST;
        }
        if (fullHttpRequest.method() == HttpMethod.HEAD) {
            return HttyMethod.HEAD;
        }
        if (fullHttpRequest.method() == HttpMethod.DELETE) {
            return HttyMethod.DELETE;
        }
        if (fullHttpRequest.method() == HttpMethod.PUT) {
            return HttyMethod.PUT;
        }
        if (fullHttpRequest.method() == HttpMethod.OPTIONS) {
            return HttyMethod.OPTIONS;
        }
        throw new UnsupportedOperationException("不支持该方法:" + fullHttpRequest.method().name());
    }

    @Override
    public String uri() {
        return fullHttpRequest.uri();
    }


    @Override
    public Map<String, String> headers() {
        if (headers == null) {
            Set<String> names = fullHttpRequest.headers().names();
            headers = new HashMap<>(names.size());
            names.forEach(x -> {
                headers.put(x, fullHttpRequest.headers().get(x));
            });
        }
        return headers;
    }

    @Override
    public String header(String name) {
        return fullHttpRequest.headers().get(name);
    }

    @Override
    public HttyCookie[] cookies() {
        if (!isDecodeCookie()) {
            decodeCookie();
        }
        return cookies;
    }

    @Override
    public Map<String, String> params() {
        if (!isDecodeParam()) {
            decodeParam();
        }
        return params;
    }

    @Override
    public String param(String name) {
        return params().get(name);
    }

    @Override
    public String content() {
        return fullHttpRequest.content().toString(CharsetUtil.UTF_8);
    }


    private void decodeCookie() {
        ServerCookieDecoder cookieDecoder = ServerCookieDecoder.LAX;
        Set<Cookie> cookieSet = cookieDecoder.decode(header("Set-Cookie"));
        cookies = CookieBuilder.create(cookieSet);
    }

    private void decodeParam() {
        QueryStringDecoder queryStringDecoder;
        try {
            queryStringDecoder = new QueryStringDecoder(URLDecoder.decode(uri(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            queryStringDecoder = new QueryStringDecoder(uri());
        }
        Map<String, List<String>> tmp = queryStringDecoder.parameters();
        if (tmp == null || tmp.isEmpty()) {
            params = Collections.emptyMap();
        } else {
            params = new HashMap<>(tmp.size());
            tmp.forEach((k, v) -> params.put(k, v.get(0)));
        }
    }

    private boolean isDecodeCookie() {
        return cookies != null;
    }


    private boolean isDecodeParam() {
        return params != null;
    }


}
