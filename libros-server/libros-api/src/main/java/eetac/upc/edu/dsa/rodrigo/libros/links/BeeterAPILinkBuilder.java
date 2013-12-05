package eetac.upc.edu.dsa.rodrigo.libros.links;

import java.net.URI;

import javax.ws.rs.core.UriInfo;

import eetac.upc.edu.dsa.rodrigo.libros.api.BeeterRootAPIResource;
import eetac.upc.edu.dsa.rodrigo.libros.api.MediaType;
import eetac.upc.edu.dsa.rodrigo.libros.api.StingResource;
import eetac.upc.edu.dsa.rodrigo.libros.model.Sting;




public class BeeterAPILinkBuilder {
	
	public final static Link buildURIRootAPI(UriInfo uriInfo) {
		//devuelve el http:server:8000/proyecto/
		URI uriRoot = uriInfo.getBaseUriBuilder()
				.path(BeeterRootAPIResource.class).build();
		//lo de encima referencia a la clase
		
		Link link = new Link();
		link.setUri(uriRoot.toString());
		//apuntador a mi mismo
		link.setRel("self bookmark");
		//titulo descriptivo
		link.setTitle("Beeter API");
		//devolver uan coleccionde enlaces
		link.setType(MediaType.BEETER_API_LINK_COLLECTION);
 
		return link;
	}
 
	//metodo de sobre carga hace lsomismo que el de abajo solo que los poen anull los que faltan.
	public static final Link buildURIStings(UriInfo uriInfo, String rel) {
		return buildURIStings(uriInfo, null, null, null, rel);
	}
 
	public static final Link buildURIStings(UriInfo uriInfo, String offset,
			String length, String username, String rel) {
		
		URI uriStings;
		//calcula cual delso 3 casos tiene que hacer
		if (offset == null && length == null)
			//me esta añdiendo el @path("/stings")
			uriStings = uriInfo.getBaseUriBuilder().path(StingResource.class)
					.build();
		else {
			
			//esta añde la base + los paremtrosde plantilla
			if (username == null)
				uriStings = uriInfo.getBaseUriBuilder()
						.path(StingResource.class).queryParam("offset", offset)
						.queryParam("length", length).build();
			else
				//esta añade base + plantilla  con nombre incluido
				uriStings = uriInfo.getBaseUriBuilder()
						.path(StingResource.class).queryParam("offset", offset)
						.queryParam("length", length)
						.queryParam("username", username).build();
		}
 
		Link self = new Link();
		self.setUri(uriStings.toString());
		self.setRel(rel);
		self.setTitle("Stings collection");
		self.setType(MediaType.BEETER_API_STING_COLLECTION);
 
		return self;
	}
 
	// es el mismocodigo solo que toma los valores concretos sino que lso guarda con {offset} en formato de plantilla
	public static final Link buildTemplatedURIStings(UriInfo uriInfo, String rel) {
 
		return buildTemplatedURIStings(uriInfo, rel, false);
	}
 
	public static final Link buildTemplatedURIStings(UriInfo uriInfo,
			String rel, boolean username) {
		URI uriStings;
		if (username)
			uriStings = uriInfo.getBaseUriBuilder().path(StingResource.class)
					.queryParam("offset", "{offset}")
					.queryParam("length", "{length}")
					.queryParam("username", "{username}").build();
		else
			uriStings = uriInfo.getBaseUriBuilder().path(StingResource.class)
					.queryParam("offset", "{offset}")
					.queryParam("length", "{length}").build();
 
		Link link = new Link();
		link.setUri(URITemplateBuilder.buildTemplatedURI(uriStings));
		link.setRel(rel);
		if (username)
			link.setTitle("Stings collection resource filtered by {username}");
		else
			link.setTitle("Stings collection resource");
		link.setType(MediaType.BEETER_API_STING_COLLECTION);
 
		return link;
	}
 
	//envia al metodos  direccion/stings
	public final static Link buildURISting(UriInfo uriInfo, Sting sting) {
		URI stingURI = uriInfo.getBaseUriBuilder().path(StingResource.class).build();
		Link link = new Link();
		link.setUri(stingURI.toString());
		link.setRel("self");
		link.setTitle("Sting " + sting.getStingid());
		link.setType(MediaType.BEETER_API_STING);
 
		return link;
	}
 
	// envia al metodo direcion/stings/stingid
	public final static Link buildURIStingId(UriInfo uriInfo, String stingid,
			String rel) {
		URI stingURI = uriInfo.getBaseUriBuilder().path(StingResource.class)
				.path(StingResource.class, "getSting").build(stingid);
		Link link = new Link();
		link.setUri(stingURI.toString());
		link.setRel("self");
		link.setTitle("Sting " + stingid);
		link.setType(MediaType.BEETER_API_STING);
 
		return link;
	}
 
}
