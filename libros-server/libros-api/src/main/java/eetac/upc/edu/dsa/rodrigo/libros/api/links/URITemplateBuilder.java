package eetac.upc.edu.dsa.rodrigo.libros.api.links;

import java.net.URI;

public class URITemplateBuilder {
	
	//definir cadenas como constantes esmucho mas rapido de ejecutar
	private final static String COLON = ":";
	private final static String DOUBLE_FWD_SLASH = "//";
	private final static String QUESTION_MARK = "?";
	private final static String HASH = "#";

	public final static String buildTemplatedURI(URI uri) {
		//foto de esquema de uri
		
		//para concatener cadenas usar stringbuilder
		StringBuilder sb = new StringBuilder();
		//primero el eskema
		sb.append(uri.getScheme());
		//dos puntos
		sb.append(COLON);
		if (uri.getAuthority() != null) {
			//poneros la autoridad
			sb.append(DOUBLE_FWD_SLASH);
			sb.append(uri.getAuthority());
		}
		if (uri.getPath() != null)
			//añadimso el path
			sb.append(uri.getPath());
		if (uri.getQuery() != null) {
			
			//ponemos el query
			sb.append(QUESTION_MARK);
			sb.append(uri.getQuery());
		}
		if (uri.getFragment() != null) {
			//si tenemso fragmento lo ponemos
			sb.append(HASH);
			sb.append(uri.getFragment());
		}

		return sb.toString();
	}
}
