package com.github.cjqcn.htty.sample.annotation.work;

import com.github.cjqcn.htty.annotation.support.EnableHttyWorking;
import com.github.cjqcn.htty.annotation.support.HttyRequestMapping;
import com.github.cjqcn.htty.core.http.HttyMethod;
import com.github.cjqcn.htty.core.http.HttyRequest;
import com.github.cjqcn.htty.core.http.HttyResponse;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;

/**
 * @author: chenjinquan
 * @create: 2018-09-26 16:30
 **/
@EnableHttyWorking(path = "/index")
public class IndexController {

	@HttyRequestMapping(HttpMethod = HttyMethod.GET, path = "/hello")
	public void sayhello(HttyRequest httyRequest, HttyResponse httyResponse) {
		httyResponse.sendString(OK, "hello world!");
	}

	@HttyRequestMapping(HttpMethod = HttyMethod.GET, path = "/welcome")
	public void welcome(HttyRequest httyRequest, HttyResponse httyResponse) {
		httyResponse.sendString(OK, "welcome to use Htty!");
	}

}
