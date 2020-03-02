# htty

## 简介
基于 netty4 的轻量级、易于扩展的 WEB 框架。 

如果你感兴趣，请点 [Star](https://github.com/cjqCN/htty/stargazers)。  

## 特性
- [x] 依赖少，实现基本功能只需一个core依赖。
- [x] 使用方便、友好、扩展性强。
- [x] 自定义拦截器。
- [x] 自定义异常处理。
- [x] `HTTPS` 支持。
- [x] `Cookie` 支持。
- [x] `Chunk` 支持。
- [x] 基于注解的使用方式。

## HelloWorld

创建一个 maven 项目，引入依赖:

```xml
<dependency>
    <groupId>com.github.cjqcn</groupId>
    <artifactId>htty-core</artifactId>
    <version>1.0.0</version>
</dependency>
```
启动程序:

```java
public class HelloWorldServer {

    public static void main(String[] args) {
        HttyServerBuilder.builder("HelloWorldServer") 
                .setPort(8080)      
                .addHttyHandler(new HelloWorldHandler())  
                .build().start();
    }


    static class HelloWorldHandler implements HttyWorker {
        @Override
        public void handle(HttyRequest httyRequest, HttyResponse httyResponse) {
            httyResponse.sendString(OK, "hello world");
        }

        @Override
        public HttyMethod[] httpMethod() {
            return HttyMethod.ALL_HTTP_METHOD;
        }

        @Override
        public String path() {
            return "/hello";
        }
    }
}
```

run：
```log
[2018-09-15 10:29:57.746] - [INFO] - [com.github.cjqcn.htty.core.BasicHttyServer:142] - [Method = start] - [Starting HTTP Service HelloWorldServer at address localhost/127.0.0.1:8080]
[2018-09-15 10:29:58.348] - [INFO] - [com.github.cjqcn.htty.core.BasicHttyServer:155] - [Method = start] - [Started HTTP Service HelloWorldServer at address /127.0.0.1:8080]
```

测试 :
 ```curl
 curl -i "http://localhost:8080/hello"
 
 HTTP/1.1 200 OK
 content-type: text/plain; charset=utf-8
 content-length: 11
 
 hello world
 ```

## 注解使用
控制层:

```java
@EnableHttyWorking(prefixPath = "/index")
public class IndexController {

	@HttyRequestMapping(httpMethod = HttyMethod.GET, path = "/hello")
	public void sayhello(HttyRequest httyRequest, HttyResponse httyResponse) {
		httyResponse.sendString(OK, "hello world!");
	}

	@HttyRequestMapping(httpMethod = HttyMethod.GET, path = "/welcome")
	public void welcome(HttyRequest httyRequest, HttyResponse httyResponse) {
		httyResponse.sendString(OK, "welcome to use Htty!");
	}

}
```
启动程序：
```java
public class AnnotationServer {

	public static void main(String[] args) {
		Collection<HttyWorker> httyWorkers = new DefaultWorkBuildHelper()
				.scanAndBuild("com.github.cjqcn.htty.sample.annotation.work");
		HttyServerBuilder.builder("AnnotationServer")
				.setPort(8080)
				.addHttyHandler(httyWorkers)
				.build().start();
	}

}
```

## 更多例子
<https://github.com/cjqCN/htty/tree/master/htty-sample>


## 版本说明
- v1.1.0
    - 内部优化
    - 自定义拦截器
    - 优雅关闭
- v1.0.0
    - 基本框架

## 压测（待补充）
```ab
$ ab -n 10000000 -c 64 -k http://127.0.0.1:8080/hello
This is ApacheBench, Version 2.3 <$Revision: 1843412 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking 127.0.0.1 (be patient)
Completed 1000000 requests
Completed 2000000 requests
Completed 3000000 requests
Completed 4000000 requests
Completed 5000000 requests
Completed 6000000 requests
Completed 7000000 requests
Completed 8000000 requests
Completed 9000000 requests
Completed 10000000 requests
Finished 10000000 requests


Server Software:
Server Hostname:        127.0.0.1
Server Port:            8080

Document Path:          /hello
Document Length:        11 bytes

Concurrency Level:      64
Time taken for tests:   73.118 seconds
Complete requests:      10000000
Failed requests:        0
Keep-Alive requests:    10000000
Total transferred:      1150000000 bytes
HTML transferred:       110000000 bytes
Requests per second:    136765.38 [#/sec] (mean)
Time per request:       0.468 [ms] (mean)
Time per request:       0.007 [ms] (mean, across all concurrent requests)
Transfer rate:          15359.39 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    0   0.0      0       1
Processing:     0    0   0.5      0       6
Waiting:        0    0   0.5      0       6
Total:          0    0   0.5      0       7

Percentage of the requests served within a certain time (ms)
  50%      0
  66%      1
  75%      1
  80%      1
  90%      1
  95%      1
  98%      1
  99%      1
 100%      7 (longest request)
```