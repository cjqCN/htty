package com.cjq.htty.abs;

/**
 * @author jqChan
 */
public interface HttpWrapper {

    HttpRequester getHttpRequester() throws Exception;

    HttpResponder getHttpResponder() throws Exception;
}



