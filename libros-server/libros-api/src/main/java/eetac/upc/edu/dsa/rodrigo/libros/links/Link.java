package eetac.upc.edu.dsa.rodrigo.libros.links;

public class Link {
	//url absoluta en formato string, conla uri seria suficiente
	private String uri;
	//indica la relacion del recurso 
	private String rel;
	//tipo de media que consume esa uri
	private String type;
	//el title es uan descripcion legible de la uri
	private String title;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
