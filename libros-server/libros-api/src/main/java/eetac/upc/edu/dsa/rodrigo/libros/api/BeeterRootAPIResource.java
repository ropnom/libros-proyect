package eetac.upc.edu.dsa.rodrigo.libros.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import eetac.upc.edu.dsa.rodrigo.libros.links.BeeterAPILinkBuilder;
import eetac.upc.edu.dsa.rodrigo.libros.model.BeeterRootAPI;


@Path("/")
public class BeeterRootAPIResource {

	//instancia de loguardado en context ala variables
	@Context
	private UriInfo uriInfo;
 
	// TODO: Return links
	
	@GET
	@Produces(MediaType.BEETER_API_LINK_COLLECTION)
	public BeeterRootAPI GetLinks()
	{
		BeeterRootAPI listalinks = new BeeterRootAPI();
		listalinks.addLink(BeeterAPILinkBuilder.buildURIRootAPI(uriInfo));
		listalinks.addLink(BeeterAPILinkBuilder.buildTemplatedURIStings(uriInfo, "stings",false));
		listalinks.addLink(BeeterAPILinkBuilder.buildTemplatedURIStings(uriInfo, "stings", true));				
		listalinks.addLink(BeeterAPILinkBuilder.buildURIStings(uriInfo, "sting") );
		
				
		return(listalinks);
	}
	
}
