package com.cjq.htty.abs;

/**
 * @author jqChan
 */
public interface HttpWrapper {

    HttpRequester httpRequester() throws Exception;

    HttpResponder httpResponder() throws Exception;
}



