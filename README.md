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
        public HttyMethod[] HttpMethod() {
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

	@HttyRequestMapping(HttpMethod = HttyMethod.GET, path = "/hello")
	public void sayhello(HttyRequest httyRequest, HttyResponse httyResponse) {
		httyResponse.sendString(OK, "hello world!");
	}

	@HttyRequestMapping(HttpMethod = HttyMethod.GET, path = "/welcome")
	public void welcome(HttyRequest httyRequest, HttyResponse httyResponse) {
		httyResponse.sendString(OK, "welcome to use Htty!");
	}

}
```
启动程序：
```java
public class AnnotationServer {

	public static void main(String[] args) throws Exception {
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


## 说明
本项目持续更新中，有兴趣的朋友也可以加入进来


