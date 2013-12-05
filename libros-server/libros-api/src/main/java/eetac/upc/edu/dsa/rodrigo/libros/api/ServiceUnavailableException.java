package eetac.upc.edu.dsa.rodrigo.libros.api;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import eetac.upc.edu.dsa.rodrigo.libros.model.BeeterError;


public class ServiceUnavailableException extends WebApplicationException {
	public ServiceUnavailableException(String message) {
		super(Response
			.status(Response.Status.SERVICE_UNAVAILABLE)
			.entity(new BeeterError(Response.Status.SERVICE_UNAVAILABLE
				.getStatusCode(), message)).type(MediaType.BEETER_API_ERROR)
			.build());
	}
}
