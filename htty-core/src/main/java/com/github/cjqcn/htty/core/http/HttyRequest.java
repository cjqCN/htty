package com.github.cjqcn.htty.core.http;


import com.github.cjqcn.htty.core.http.cookie.HttyCookie;

import java.util.Map;

public interface HttyRequest {

    HttyMethod method();

    String uri();

    Map<String, String> headers();

    String header(String name);

    HttyCookie[] cookies();

    Map<String, String> params();

    String param(String name);

    String context();
}
