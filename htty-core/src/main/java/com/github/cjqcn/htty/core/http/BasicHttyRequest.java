package com.github.cjqcn.htty.core.http;

import com.github.cjqcn.htty.core.http.cookie.CookieBuilder;
import com.github.cjqcn.htty.core.http.cookie.HttyCookie;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import io.netty.util.CharsetUtil;

import javax.ws.rs.NotSupportedException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Optional;
import java.util.Set;

public class BasicHttyRequest implements HttyRequest {

    private final FullHttpRequest fullHttpRequest;

    private volatile HttyCookie[] cookies;

    private volatile QueryStringDecoder queryStringDecoder;

    public BasicHttyRequest(FullHttpRequest fullHttpRequest) {
        this.fullHttpRequest = fullHttpRequest;
        cookies = null;
        queryStringDecoder = null;
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
        throw new NotSupportedException("不支持该方法");
    }

    @Override
    public String uri() {
        return fullHttpRequest.uri();
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
    public String param(String name) {
        if (!isDecodeParam()) {
            decodeParam();
        }
        return Optional.ofNullable(queryStringDecoder).map(QueryStringDecoder::parameters).map(x -> x.get(name)).map(x -> x.get(0)).orElse(null);
    }

    @Override
    public String context() {
        return fullHttpRequest.content().toString(CharsetUtil.UTF_8);
    }


    private void decodeCookie() {
        ServerCookieDecoder cookieDecoder = ServerCookieDecoder.LAX;
        Set<Cookie> cookieSet = cookieDecoder.decode(header("Set-Cookie"));
        cookies = CookieBuilder.create(cookieSet);
    }

    private void decodeParam() {
        try {
            queryStringDecoder = new QueryStringDecoder(URLDecoder.decode(uri(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            queryStringDecoder = new QueryStringDecoder(uri());
        }
    }

    private boolean isDecodeCookie() {
        return cookies != null;
    }


    private boolean isDecodeParam() {
        return queryStringDecoder != null;
    }


}
