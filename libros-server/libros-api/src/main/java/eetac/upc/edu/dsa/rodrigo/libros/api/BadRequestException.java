package eetac.upc.edu.dsa.rodrigo.libros.api;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import eetac.upc.edu.dsa.rodrigo.libros.api.model.LibrosError;


public class BadRequestException extends WebApplicationException {

	public BadRequestException(String message) {
		super(Response
			.status(Response.Status.BAD_REQUEST)
			.entity(new LibrosError(Response.Status.BAD_REQUEST
				.getStatusCode(), message)).type(MediaType.LIBROS_API_ERROR)
			.build());
	}
}
