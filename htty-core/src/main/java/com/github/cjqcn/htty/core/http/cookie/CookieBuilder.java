package com.github.cjqcn.htty.core.http.cookie;

import io.netty.handler.codec.http.cookie.Cookie;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: chenjinquan
 * @create: 2018-10-07 21:06
 **/
public final class CookieBuilder {

    private static final HttyCookie[] emptyArr = new HttyCookie[0];

    public static HttyCookie create(Cookie cookie) {
        return new DefaultHttyCookieWrapper(cookie);
    }

    public static HttyCookie[] create(Collection<Cookie> cookies) {
        if (cookies == null) {
            return emptyArr;
        }
        return cookies.stream().map(x -> create(x)).collect(Collectors.toList()).toArray(new HttyCookie[cookies.size()]);
    }
}
