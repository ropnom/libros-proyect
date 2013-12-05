package eetac.upc.edu.dsa.rodrigo.libros.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import eetac.upc.edu.dsa.rodrigo.libros.links.LibrosAPILinkBuilder;
import eetac.upc.edu.dsa.rodrigo.libros.model.LibrosRootAPI;


@Path("/")
public class LibrosRootAPIResource {

	//instancia de loguardado en context ala variables
	@Context
	private UriInfo uriInfo;
 
	// TODO: Return links
	
	@GET
	@Produces(MediaType.LIBROS_API_LINK_COLLECTION)
	public LibrosRootAPI GetLinks()
	{
		LibrosRootAPI listalinks = new LibrosRootAPI();
		listalinks.addLink(LibrosAPILinkBuilder.buildURIRootAPI(uriInfo));
		listalinks.addLink(LibrosAPILinkBuilder.buildTemplatedURIStings(uriInfo, "libros",false));
		listalinks.addLink(LibrosAPILinkBuilder.buildTemplatedURIStings(uriInfo, "libros", true));	
		//aki hay un inconcruencia con Raul
		listalinks.addLink(LibrosAPILinkBuilder.buildURIStings(uriInfo, "libro") );
		
				
		return(listalinks);
	}
	
}
