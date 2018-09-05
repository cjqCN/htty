package htty.com.github.cjqcn.htty.core.abs;

/**
 * @author jqChan
 */
public interface HttyContext {

	/**
	 * Returns {@link HttyRequest} in this.
	 *
	 * @return The {@link HttyRequest}
	 */
	HttyRequest httyRequest() throws Exception;

	/**
	 * Returns {@link HttyResponse} in this.
	 *
	 * @return The {@link HttyResponse}
	 */
	HttyResponse httyResponse() throws Exception;
}



