package com.cjq.htty.abs;

public interface URLRewriter {

    boolean rewrite(HttpRequester requester, HttpResponder responder) throws Exception;
}
