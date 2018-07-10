package htty.com.github.cjqcn.htty.core.abs;

/**
 * @author jqChan
 */
public interface HttpContext {

    /**
     * Returns {@link HttpRequester} in this.
     *
     * @return The {@link HttpRequester}
     */
    HttpRequester httpRequester() throws Exception;

    /**
     * Returns {@link HttpResponder} in this.
     *
     * @return The {@link HttpResponder}
     */
    HttpResponder httpResponder() throws Exception;
}



