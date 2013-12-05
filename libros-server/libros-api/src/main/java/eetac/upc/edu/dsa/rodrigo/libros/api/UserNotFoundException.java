package eetac.upc.edu.dsa.rodrigo.libros.api;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import eetac.upc.edu.dsa.rodrigo.libros.model.LibrosError;


public class UserNotFoundException extends WebApplicationException {
        /**
         * 
         */
        private static final long serialVersionUID = -4110163799971920585L;
        private final static String MESSAGE = "User requested not found";
        public UserNotFoundException() {
                super(Response
                                .status(Response.Status.NOT_FOUND)
                                .entity(new LibrosError(Response.Status.NOT_FOUND
                                                .getStatusCode(), MESSAGE))
                                .type(MediaType.LIBROS_API_ERROR).build());
        }
}
