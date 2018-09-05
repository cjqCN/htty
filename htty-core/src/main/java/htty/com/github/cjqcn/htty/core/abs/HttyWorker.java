package htty.com.github.cjqcn.htty.core.abs;

public interface HttyWorker {
    void handle(HttyRequest httyRequest, HttyResponse httyResponse) throws Exception;
}
