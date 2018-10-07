/*
 * Copyright 2015 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.github.cjqcn.htty.core.http.cookie;


import io.netty.handler.codec.http.cookie.Cookie;

/**
 * An interface defining an
 * <a href="http://en.wikipedia.org/wiki/HTTP_cookie">HTTP cookie</a>.
 */
public interface HttyCookie extends Cookie {


    /**
     * Returns the name of this {@link HttyCookie}.
     *
     * @return The name of this {@link HttyCookie}
     */
    @Override
    String name();

    /**
     * Returns the value of this {@link HttyCookie}.
     *
     * @return The value of this {@link HttyCookie}
     */
    @Override
    String value();

    /**
     * Sets the value of this {@link HttyCookie}.
     *
     * @param value The value to set
     */
    @Override
    void setValue(String value);

    /**
     * Returns true if the raw value of this {@link HttyCookie},
     * was wrapped with double quotes in original Set-HttyCookie header.
     *
     * @return If the value of this {@link HttyCookie} is to be wrapped
     */
    @Override
    boolean wrap();

    /**
     * Sets true if the value of this {@link HttyCookie}
     * is to be wrapped with double quotes.
     *
     * @param wrap true if wrap
     */
    @Override
    void setWrap(boolean wrap);

    /**
     * Returns the domain of this {@link HttyCookie}.
     *
     * @return The domain of this {@link HttyCookie}
     */
    @Override
    String domain();

    /**
     * Sets the domain of this {@link HttyCookie}.
     *
     * @param domain The domain to use
     */
    @Override
    void setDomain(String domain);

    /**
     * Returns the path of this {@link HttyCookie}.
     *
     * @return The {@link HttyCookie}'s path
     */
    @Override
    String path();

    /**
     * Sets the path of this {@link HttyCookie}.
     *
     * @param path The path to use for this {@link HttyCookie}
     */
    @Override
    void setPath(String path);

    /**
     * Returns the maximum age of this {@link HttyCookie} in seconds or {@link HttyCookie#UNDEFINED_MAX_AGE} if unspecified
     *
     * @return The maximum age of this {@link HttyCookie}
     */
    @Override
    long maxAge();

    /**
     * Sets the maximum age of this {@link HttyCookie} in seconds.
     * If an age of {@code 0} is specified, this {@link HttyCookie} will be
     * automatically removed by browser because it will expire immediately.
     * If {@link HttyCookie#UNDEFINED_MAX_AGE} is specified, this {@link HttyCookie} will be removed when the
     * browser is closed.
     *
     * @param maxAge The maximum age of this {@link HttyCookie} in seconds
     */
    @Override
    void setMaxAge(long maxAge);

    /**
     * Checks to see if this {@link HttyCookie} is secure
     *
     * @return True if this {@link HttyCookie} is secure, otherwise false
     */
    @Override
    boolean isSecure();

    /**
     * Sets the security getStatus of this {@link HttyCookie}
     *
     * @param secure True if this {@link HttyCookie} is to be secure, otherwise false
     */
    @Override
    void setSecure(boolean secure);

    /**
     * Checks to see if this {@link HttyCookie} can only be accessed via HTTP.
     * If this returns true, the {@link HttyCookie} cannot be accessed through
     * client side script - But only if the browser supports it.
     * For more information, please look <a href="http://www.owasp.org/index.php/HTTPOnly">here</a>
     *
     * @return True if this {@link HttyCookie} is HTTP-only or false if it isn't
     */
    @Override
    boolean isHttpOnly();

    /**
     * Determines if this {@link HttyCookie} is HTTP only.
     * If set to true, this {@link HttyCookie} cannot be accessed by a client
     * side script. However, this works only if the browser supports it.
     * For for information, please look
     * <a href="http://www.owasp.org/index.php/HTTPOnly">here</a>.
     *
     * @param httpOnly True if the {@link HttyCookie} is HTTP only, otherwise false.
     */
    @Override
    void setHttpOnly(boolean httpOnly);
}
