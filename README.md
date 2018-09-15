# htty

## 简介
基于 netty4 的轻量级、易于扩展的 WEB 框架。 
持续更新中...

如果你感兴趣，请点 [Star](https://github.com/cjqCN/htty/stargazers)。  
如果你想加入该项目，请发邮件至 [15521255460@163.com](mailto:15521255460@163.com)。

## 特性
- [x] 依赖少，实现基本功能只需一个core依赖。
- [x] 使用方便、友好、扩展性强。
- [x] 自定义拦截器。
- [x] `HTTPS` 支持。
- [x] `Cookie` 支持。
- [x] `Chunk` 支持。
- [ ] 基于注解的使用方式。

## HelloWorld

创建一个 maven 项目，引入依赖:

```xml
<dependency>
    <groupId>com.github.cjqcn</groupId>
    <artifactId>htty-core</artifactId>
    <version>1.0.0</version>
</dependency>
```

编写HelloWorld程序:

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

启动程序：
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


## 说明
-----


