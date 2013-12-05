package eetac.upc.edu.dsa.rodrigo.libros.api;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import eetac.upc.edu.dsa.rodrigo.libros.api.links.LibrosAPILinkBuilder;
import eetac.upc.edu.dsa.rodrigo.libros.api.model.Libro;
import eetac.upc.edu.dsa.rodrigo.libros.api.model.LibroCollection;

@Path("/libros")
public class LibroResource {

	@Context
	private UriInfo uriInfo;
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();

	@Context
	private SecurityContext security;

	// Raul tiene el el Libro collection aqui;

	@GET
	@Path("/{libroid}")
	@Produces(MediaType.LIBROS_API_LIBRO)
	public Response getLibro(@PathParam("libroid") String libroid,
			@Context Request req) {

		// Create CacheControl cache por si me lohan pedido hace poco
		CacheControl cc = new CacheControl();
		Libro libro = new Libro();
		Statement stmt = null;

		// arrancamos la conexion
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceUnavailableException(e.getMessage());
		}

		// hacemso la consulta del libro
		try {
			// creamos el statement y la consulta
			stmt = conn.createStatement();
			String sql = "select * from libros where libroid=" + libroid;
			// realizamos la consulta
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				// creamos el libro
				libro.setLibroid(rs.getInt("libroid"));
				libro.setTitulo(rs.getString("titulo"));
				libro.setAutor(rs.getString("autor"));
				libro.setLengua(rs.getString("lengua"));
				libro.setEdicion(rs.getString("edicion"));
				libro.setFecha_edicion(rs.getDate("fecha_edicion"));
				libro.setFecha_impresion(rs.getDate("fecha_impresion"));
				libro.setEditorial(rs.getString("editorial"));
				libro.setLastUpdate(rs.getTimestamp("lastUpdate"));

				// añadimos los links
				libro.addLink(LibrosAPILinkBuilder
						.buildURISting(uriInfo, libro));
			} else {
				throw new LibroNotFoundException();
			}
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InternalServerException(e.getMessage());
		} finally {

			try {
				stmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Calculate the ETag on last modified date of user resource
		EntityTag eTag = new EntityTag(Integer.toString(libro.getLastUpdate()
				.hashCode()));

		// Verify if it matched with etag available in http request
		Response.ResponseBuilder rb = req.evaluatePreconditions(eTag);

		// If ETag matches the rb will be non-null;
		// Use the rb to return the response without any further processing
		if (rb != null) {
			return rb.cacheControl(cc).tag(eTag).build();
		}

		// If rb is null then either it is first time request; or resource is
		// modified
		// Get the updated representation and return with Etag attached to it
		rb = Response.ok(libro).cacheControl(cc).tag(eTag);

		return rb.build();
	}

	@POST
	@Consumes(MediaType.LIBROS_API_LIBRO)
	@Produces(MediaType.LIBROS_API_LIBRO)
	public Libro createLibro(Libro libro) {

		Statement stmt = null;

		/*
		 * if (sting.getSubject().length() > 100) { throw new
		 * BadRequestException(
		 * "Subject length must be less or equal than 100 characters"); } if
		 * (sting.getContent().length() > 500) { throw new BadRequestException(
		 * "Content length must be less or equal than 500 characters"); }
		 */

		// realizamos conexion
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceUnavailableException(e.getMessage());
		}

		// leemos lo que nos manda y lo insertamos enla base de datos
		try {
			// realizamos la consulta
			stmt = conn.createStatement();			
			SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
			String edicion = dt1.format(libro.getFecha_edicion());
			String impresion = dt1.format(libro.getFecha_impresion());
						
			String sql = "insert into libros (titulo, autor, lengua, edicion, fecha_edicion, fecha_impresion, editorial)";
			sql += "values ('" + libro.getTitulo() + "', '" + libro.getAutor()
					+ "', '" + libro.getLengua() + "','" + libro.getEdicion()
					+ "','" + edicion + "','"
					+ impresion + "','" + libro.getEditorial()
					+ "');";

			// le indicamos que nso devuelva la primary key que le genere a la
			// nueva entrada
			stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = stmt.getGeneratedKeys();
			// leemos la primary key
			if (rs.next()) {
				int id = rs.getInt(1);
				rs.close();
				// TODO: Retrieve the created sting from the database to get all
				// the remaining fields
				sql = "select lastUpdate from libros where libroid="
						+ id;

				rs = stmt.executeQuery(sql);
				rs.next();
				libro.setLibroid(id);
				libro.setLastUpdate(rs.getTimestamp("lastUpdate"));
			} else {
				// TODO: Throw exception, something has failed. Don't do now
			}

			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InternalServerException(e.getMessage());
		} finally {

			try {
				stmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return libro;
	}
	
	@PUT
	@Path("/{libroid}")
	@Consumes(MediaType.LIBROS_API_LIBRO)
	@Produces(MediaType.LIBROS_API_LIBRO)
	public Libro updateLibro(@PathParam("libroid") String libroid, Libro libro) {
		// TODO: Update in the database the record identified by stingid with
		// the data values in sting
		Statement stmt = null;
		
		/*
		if (sting.getSubject().length() > 100) {
			throw new BadRequestException(
					"Subject length must be less or equal than 100 characters");
		}
		if (sting.getContent().length() > 500) {
			throw new BadRequestException(
					"Content length must be less or equal than 500 characters");
		}

		if (security.isUserInRole("registered")) {
			if (security.getUserPrincipal().getName()
					.equals(sting.getUsername())) {
				throw new ForbiddenException("You are nor allowed");
			}
		}
		*/
		
		// arrancamos la conexion
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceUnavailableException(e.getMessage());
		}

		// hacemso la consulta
		try {
			SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
			String edicion = dt1.format(libro.getFecha_edicion());
			String impresion = dt1.format(libro.getFecha_impresion());
			// creamos el statement y la consulta
			stmt = conn.createStatement();
			String sql = "update  libros SET libros.titulo='"
					+ libro.getTitulo() + "',libros.autor='"
					+ libro.getAutor() + "', libros.lengua='"
					+ libro.getLengua() + "', libros.edicion='"
					+ libro.getEdicion() + "', libros.fecha_edicion='"
					+ edicion + "', libros.fecha_impresion='"
					+ impresion + "', libros.editorial='"
					+  libro.getEditorial()+ "' where libroid=" + libroid;
			// realizamos la consulta

			int rows = stmt.executeUpdate(sql);
			
			if (rows == 0)
				throw new LibroNotFoundException();

			sql = "select lastUpdate from libros where libroid=" + libroid;
			
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				// creamos el sting
				libro.setLastUpdate(rs.getTimestamp("lastUpdate"));
				// añadimos los links
				libro.addLink(LibrosAPILinkBuilder
						.buildURISting(uriInfo, libro));
			} else {
				throw new LibroNotFoundException();
			}
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InternalServerException(e.getMessage());
		} finally {

			try {
				stmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return libro;
	}
	
	@DELETE
	@Path("/{libroid}")
	public void deleteLibro(@PathParam("libroid") String libroid) {
		// TODO Delete record in database stings identified by stingid.
		// arrancamos la conexion
		Statement stmt = null;
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceUnavailableException(e.getMessage());
		}

		// hacemso la consulta y el array de stings
		try {
			// creamos el statement y la consulta
			stmt = conn.createStatement();
			String sql = "Delete from  libros where libroid=" + libroid;
			// realizamos la consulta

			int rows = stmt.executeUpdate(sql);
			if (rows == 0)
				throw new LibroNotFoundException();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InternalServerException(e.getMessage());
		} finally {

			try {
				stmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.LIBROS_API_LIBRO_COLLECTION)
	public LibroCollection getSearch(@QueryParam("titulo") String titulo,
			@QueryParam("autor") String autor,
			 @Context Request req) {
		
		boolean caso = true;;
		
		if (titulo == null && autor== null)
			throw new BadRequestException(
					"Titulo or Autor is mandatory parameter");		
		if(titulo==null)
			caso = false;
		

		// TODO: Retrieve all stings stored in the database, instantiate one
		// Sting for each one and store them in the StingCollection.

		LibroCollection libros = new LibroCollection();
		Connection con = null;
		Statement stmt = null;
		try {
			con = ds.getConnection();
			stmt = con.createStatement();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}
		String[] palabras;
		String busqueda;
		if(caso)
		{
		 titulo.replace("^A-Za-z\\s]", "");			
		 palabras = titulo.split(" ");
		 busqueda = "titulo";
		}
		else
		{
		 autor.replace("^A-Za-z\\s]", "");			
		 palabras = autor.split(" ");
		 busqueda = "autor";
		}
		try {
			
			int i = 1;
			String query = "Select * from libros where ";
			query += "libros."+busqueda+" LIKE '%"+palabras[0]+"%' ";
			
			while(i<palabras.length)
			{
				query += "or libros."+busqueda+" LIKE '%"+palabras[i]+"%' ";
				i++;
			}
			query += ";";			
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				
				Libro s = new Libro();
				s.setLibroid(rs.getInt("libroid"));
				s.setTitulo(rs.getString("titulo"));
				s.setAutor(rs.getString("autor"));
				s.setLengua(rs.getString("lengua"));
				s.setEdicion(rs.getString("edicion"));
				s.setFecha_edicion(rs.getDate("fecha_edicion"));
				s.setFecha_impresion(rs.getDate("fecha_impresion"));
				s.setEditorial(rs.getString("editorial"));
				s.setLastUpdate(rs.getTimestamp("lastUpdate"));
				s.addLink(LibrosAPILinkBuilder
						.buildURISting(uriInfo, s));
				
				libros.add(s);
			}
			rs.close();
		} catch (SQLException e) {
			throw new InternalServerException(e.getMessage());
		} finally {
			try {
				con.close();
				stmt.close();
			} catch (Exception e) {
			}
		}
		return libros;
	}
	
	


	/*
	// realizamos los metodos GET que nos devuevle los stings de la base de
	// datos
	@GET
	@Produces(MediaType.LIBROS_API_LIBRO_COLLECTION)
	public LibroCollection getStings(@QueryParam("username") String username,
			@QueryParam("offset") String offset,
			@QueryParam("length") String length) {

		if ((offset == null) || (length == null))
			throw new BadRequestException(
					"offset and length are mandatory parameters");
		int ioffset, ilength;
		try {
			ioffset = Integer.parseInt(offset);
			if (ioffset < 0)
				throw new NumberFormatException();
		} catch (NumberFormatException e) {
			throw new BadRequestException(
					"offset must be an integer greater or equal than 0.");
		}
		try {
			ilength = Integer.parseInt(length);
			if (ilength < 1)
				throw new NumberFormatException();
		} catch (NumberFormatException e) {
			throw new BadRequestException(
					"length must be an integer greater or equal than 0.");
		}

		LibroCollection stings = new LibroCollection();

		Statement stmt = null;

		// arrancamos la conexion
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceUnavailableException(e.getMessage());
		}

		// hacemso la consulta y el array de stings
		try {
			// creamos el statement y la consulta
			stmt = conn.createStatement();
			String sql;
			if (username == null) {
				sql = "select users.name, stings.* from users, stings where users.username=stings.username ORDER BY last_modified DESC limit "
						+ offset + "," + length + " ";
			} else {
				sql = "select users.name, stings.* from users, stings where users.username=stings.username and stings.username='"
						+ username
						+ "' ORDER BY last_modified DESC limit "
						+ offset + "," + length + " ";
			}
			// realizamos la consulta
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// creamos el sting
				Sting sting = new Sting();
				sting.setStingid(rs.getString("stingid"));
				sting.setUsername(rs.getString("username"));
				sting.setAuthor(rs.getString("name"));
				sting.setContent(rs.getString("content"));
				sting.setSubject(rs.getString("subject"));
				sting.setLastModified(rs.getTimestamp("last_modified"));

				// añadimos los links
				sting.addLink(LibrosAPILinkBuilder
						.buildURISting(uriInfo, sting));

				// añadimos el sting a la lista
				stings.addSting(sting);
			}
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new InternalServerException(e.getMessage());
		} finally {

			try {
				stmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		stings.addLink(BeeterAPILinkBuilder.buildURIStings(uriInfo, offset,
				length, username, "self"));

		if ((ioffset - ilength) > 0) {
			stings.addLink(BeeterAPILinkBuilder.buildURIStings(uriInfo, offset,
					length, username, "previous"));
		} else {
			int total = stings.getStings().size();
			int puntero = total - ilength;

			stings.addLink(BeeterAPILinkBuilder.buildURIStings(uriInfo,
					Integer.toString(puntero), length, username, "previous"));
		}

		// devolvemos el sting
		return stings;
	}

	// realizamos el metodo POST

	
	

	
*/
}