package htty.com.github.cjqcn.htty.core;

public class HttpServerBuilderTest {

    public static void main(String[] args) throws Exception {
        HttpServerBuilder.builder("HttyServer").build().start();
    }
}