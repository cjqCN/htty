package com.cjq.htty.abs;

import java.io.IOException;
import java.io.InputStream;

/**
 * Interface used to get the status code and content from calling another handler internally.
 */
public interface InternalHttpResponse {

  int getStatusCode();

  /**
   * Opens an {@link InputStream} that contains the response content. The caller is responsible of closing the
   * returned stream.
   *
   * @return an {@link InputStream} for reading response content
   * @throws IOException if failed to open the stream
   */
  InputStream openInputStream() throws IOException;
}
