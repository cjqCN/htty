package htty.com.github.cjqcn.htty.core.worker;

import htty.com.github.cjqcn.htty.core.common.HttyContext;

public interface HttyWorkerWrapper {

    HttyWorker httyWorker();

    HttyContext httyContext();

    static HttyWorkerWrapper create(final HttyWorker httyWorker, final HttyContext httyContext) {
        return new HttyWorkerWrapper() {
            @Override
            public HttyWorker httyWorker() {
                return httyWorker;
            }

            @Override
            public HttyContext httyContext() {
                return httyContext;
            }
        };
    }


}
