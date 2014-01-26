package eetac.upc.edu.dsa.rodrigo.libros.api;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import eetac.upc.edu.dsa.rodrigo.libros.api.model.LibrosError;


public class UserNameIsUsedException extends WebApplicationException {
        /**
         * 
         */
        private static final long serialVersionUID = -4110163799971920575L;
        private final static String MESSAGE = "User name is used";
        public UserNameIsUsedException() {
                super(Response
                                .status(Response.Status.NOT_FOUND)
                                .entity(new LibrosError(Response.Status.NOT_FOUND
                                                .getStatusCode(), MESSAGE))
                                .type(MediaType.LIBROS_API_ERROR).build());
        }
}
