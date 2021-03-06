package eetac.upc.edu.dsa.rodrigo.libros.api;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import eetac.upc.edu.dsa.rodrigo.libros.api.model.LibrosError;


public class ServiceUnavailableException extends WebApplicationException {
	public ServiceUnavailableException(String message) {
		super(Response
			.status(Response.Status.SERVICE_UNAVAILABLE)
			.entity(new LibrosError(Response.Status.SERVICE_UNAVAILABLE
				.getStatusCode(), message)).type(MediaType.LIBROS_API_ERROR)
			.build());
	}
}
