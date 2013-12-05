package eetac.upc.edu.dsa.rodrigo.libros.links;

import java.net.URI;

import javax.ws.rs.core.UriInfo;

import eetac.upc.edu.dsa.rodrigo.libros.api.LibroResource;
import eetac.upc.edu.dsa.rodrigo.libros.api.LibrosRootAPIResource;
import eetac.upc.edu.dsa.rodrigo.libros.api.MediaType;
import eetac.upc.edu.dsa.rodrigo.libros.model.Libro;




public class LibrosAPILinkBuilder {
	
	public final static Link buildURIRootAPI(UriInfo uriInfo) {
		//devuelve el http:server:8000/proyecto/
		URI uriRoot = uriInfo.getBaseUriBuilder()
				.path(LibrosRootAPIResource.class).build();
		//lo de encima referencia a la clase
		
		Link link = new Link();
		link.setUri(uriRoot.toString());
		//apuntador a mi mismo
		link.setRel("self bookmark");
		//titulo descriptivo
		link.setTitle("Libros API");
		//devolver uan coleccionde enlaces
		link.setType(MediaType.LIBROS_API_LINK_COLLECTION);
 
		return link;
	}
 
	//metodo de sobre carga hace lsomismo que el de abajo solo que los poen anull los que faltan.
	public static final Link buildURIStings(UriInfo uriInfo, String rel) {
		return buildURIStings(uriInfo, null, null, null, rel);
	}
 
	public static final Link buildURIStings(UriInfo uriInfo, String offset,
			String length, String username, String rel) {
		
		URI uriLibros;
		//calcula cual delso 3 casos tiene que hacer
		if (offset == null && length == null)
			//me esta añdiendo el @path("/stings")
			uriLibros = uriInfo.getBaseUriBuilder().path(LibroResource.class)
					.build();
		else {
			
			//esta añde la base + los paremtrosde plantilla
			if (username == null)
				uriLibros = uriInfo.getBaseUriBuilder()
						.path(LibroResource.class).queryParam("offset", offset)
						.queryParam("length", length).build();
			else
				//esta añade base + plantilla  con nombre incluido
				uriLibros = uriInfo.getBaseUriBuilder()
						.path(LibroResource.class).queryParam("offset", offset)
						.queryParam("length", length)
						.queryParam("username", username).build();
		}
 
		Link self = new Link();
		self.setUri(uriLibros.toString());
		self.setRel(rel);
		self.setTitle("Libros collection");
		self.setType(MediaType.LIBROS_API_LIBRO_COLLECTION);
 
		return self;
	}
 
	// es el mismocodigo solo que toma los valores concretos sino que lso guarda con {offset} en formato de plantilla
	public static final Link buildTemplatedURIStings(UriInfo uriInfo, String rel) {
 
		return buildTemplatedURIStings(uriInfo, rel, false);
	}
 
	public static final Link buildTemplatedURIStings(UriInfo uriInfo,
			String rel, boolean username) {
		URI uriLibros;
		if (username)
			uriLibros = uriInfo.getBaseUriBuilder().path(LibroResource.class)
					.queryParam("offset", "{offset}")
					.queryParam("length", "{length}")
					.queryParam("username", "{username}").build();
		else
			uriLibros = uriInfo.getBaseUriBuilder().path(LibroResource.class)
					.queryParam("offset", "{offset}")
					.queryParam("length", "{length}").build();
 
		Link link = new Link();
		link.setUri(URITemplateBuilder.buildTemplatedURI(uriLibros));
		link.setRel(rel);
		if (username)
			link.setTitle("Libros collection resource filtered by {username}");
		else
			link.setTitle("Stings collection resource");
		link.setType(MediaType.LIBROS_API_LIBRO_COLLECTION);
 
		return link;
	}
 
	//envia al metodos  direccion/stings
	public final static Link buildURISting(UriInfo uriInfo, Libro libro) {
		URI stingURI = uriInfo.getBaseUriBuilder().path(LibroResource.class).build();
		Link link = new Link();
		link.setUri(stingURI.toString());
		link.setRel("self");
		link.setTitle("Libro " + libro.getLibroid());
		link.setType(MediaType.LIBROS_API_LIBRO);
 
		return link;
	}
 
	// envia al metodo direcion/stings/stingid
	public final static Link buildURIStingId(UriInfo uriInfo, String libroid,
			String rel) {
		URI stingURI = uriInfo.getBaseUriBuilder().path(LibroResource.class)
				.path(LibroResource.class, "getLibro").build(libroid);
		Link link = new Link();
		link.setUri(stingURI.toString());
		link.setRel("self");
		link.setTitle("Libro " + libroid);
		link.setType(MediaType.LIBROS_API_LIBRO);
 
		return link;
	}
 
}
