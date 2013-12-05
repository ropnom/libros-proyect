package eetac.upc.edu.dsa.rodrigo.libros.api.links;

import java.net.URI;

import javax.ws.rs.core.UriInfo;

import eetac.upc.edu.dsa.rodrigo.libros.api.LibroResource;
import eetac.upc.edu.dsa.rodrigo.libros.api.MediaType;
import eetac.upc.edu.dsa.rodrigo.libros.api.ResenaResource;
import eetac.upc.edu.dsa.rodrigo.libros.api.model.Resena;


public class ResenasAPILinkBuilder {
	 
	//envia al metodos  direccion/stings
	public final static Link buildURIResena(UriInfo uriInfo, Resena resena) {
		URI resenaURI = uriInfo.getBaseUriBuilder().path(ResenaResource.class).build();
		Link link = new Link();
		link.setUri(resenaURI.toString());
		link.setRel("self");
		link.setTitle("Resena " + resena.getLibroid());
		link.setType(MediaType.LIBROS_API_RESENA);
 
		return link;
	}
 
	// envia al metodo direcion/stings/stingid
	public final static Link buildURIResenaId(UriInfo uriInfo, int resenaid
			) {
		URI resenaURI = uriInfo.getBaseUriBuilder().path(ResenaResource.class)
				.path(LibroResource.class, "getResena").build(resenaid);
		Link link = new Link();
		link.setUri(resenaURI.toString());
		link.setRel("self");
		link.setTitle("Resena " + resenaid);
		link.setType(MediaType.LIBROS_API_RESENA);
 
		return link;
	}
 
}
