package edu.usc.marshall.centralis22.service.requesthandler;

import edu.usc.marshall.centralis22.model.SimUser;
import edu.usc.marshall.centralis22.util.RequestResponseEntity;

/**
 * Interface for concrete {@code RequestHandler} implementations.
 */
public interface RequestHandler {

    /**
     * Handles the request.
     *
     * @param user User who initiates the request.
     * @param content See API.
     * @param rre See API.
     */
    void handle(SimUser user, Object content, RequestResponseEntity rre);
}
