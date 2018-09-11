package htty.com.github.cjqcn.htty.core.common;

import htty.com.github.cjqcn.htty.core.http.HttyRequest;
import htty.com.github.cjqcn.htty.core.http.HttyResponse;

/**
 * @author jqChan
 */
public interface HttyContext {

    /**
     * Returns {@link HttyRequest} in this.
     *
     * @return The {@link HttyRequest}
     */
    HttyRequest httyRequest();

    /**
     * Returns {@link HttyResponse} in this.
     *
     * @return The {@link HttyResponse}
     */
    HttyResponse httyResponse();
}



