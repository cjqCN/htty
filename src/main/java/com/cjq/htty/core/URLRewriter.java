package com.cjq.htty.core;

public interface URLRewriter {
    boolean rewrite(HttpRequester requester, HttpResponder responder) throws Exception;
}
