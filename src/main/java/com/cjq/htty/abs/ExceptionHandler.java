package com.cjq.htty.abs;


public abstract class ExceptionHandler {
    public abstract void handle(Throwable t, HttpRequester requester, HttpResponder responder) throws Throwable;
}
