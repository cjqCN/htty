package com.github.cjqcn.htty.core.http.cookie;

import io.netty.handler.codec.http.cookie.Cookie;

import java.util.Objects;


/**
 * @author: chenjinquan
 * @create: 2018-10-07 19:20
 **/
public class DefaultHttyCookieWrapper implements HttyCookie {

    private final Cookie cookie;

    DefaultHttyCookieWrapper(Cookie cookie) {
        this.cookie = cookie;
    }

    private Cookie getCookie() {
        return cookie;
    }

    @Override
    public String name() {
        return getCookie().name();
    }

    @Override
    public String value() {
        return getCookie().value();
    }

    @Override
    public void setValue(String value) {
        getCookie().setValue(value);
    }

    @Override
    public boolean wrap() {
        return getCookie().wrap();
    }

    @Override
    public void setWrap(boolean wrap) {
        getCookie().setWrap(wrap);
    }

    @Override
    public String domain() {
        return getCookie().domain();
    }

    @Override
    public void setDomain(String domain) {
        getCookie().setDomain(domain);
    }

    @Override
    public String path() {
        return getCookie().path();
    }

    @Override
    public void setPath(String path) {
        getCookie().setPath(path);
    }

    @Override
    public long maxAge() {
        return getCookie().maxAge();
    }

    @Override
    public void setMaxAge(long maxAge) {
        getCookie().setMaxAge(maxAge);
    }

    @Override
    public boolean isSecure() {
        return getCookie().isSecure();
    }

    @Override
    public void setSecure(boolean secure) {
        getCookie().setSecure(secure);
    }

    @Override
    public boolean isHttpOnly() {
        return getCookie().isHttpOnly();
    }

    @Override
    public void setHttpOnly(boolean httpOnly) {
        getCookie().setHttpOnly(httpOnly);
    }

    @Override
    public int compareTo(Cookie o) {
        return getCookie().compareTo(o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DefaultHttyCookieWrapper that = (DefaultHttyCookieWrapper) o;
        return Objects.equals(cookie, that.cookie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cookie);
    }

    @Override
    public String toString() {
        return getCookie().toString();
    }
}
