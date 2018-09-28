package com.github.cjqcn.htty.core.common;


import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.http.HttyResponse;

public interface ExceptionHandler {
	void handle(Exception ex, HttyRequest request, HttyResponse response);
}
