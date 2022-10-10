package edu.usc.marshall.centralis22.service.handler;

import edu.usc.marshall.centralis22.util.RequestResponseEntity;

/**
 * Interface for concrete request handler implementations.
 */
public interface RequestHandler {

    /**
     * Handles the request given.
     * @param content See API.
     * @param rre See API.
     */
    void handle(Object content, RequestResponseEntity rre);
}
