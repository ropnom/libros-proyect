package eetac.upc.edu.dsa.rodrigo.libros.api;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import eetac.upc.edu.dsa.rodrigo.libros.api.links.LibrosAPILinkBuilder;
import eetac.upc.edu.dsa.rodrigo.libros.api.model.Libro;
import eetac.upc.edu.dsa.rodrigo.libros.api.model.Resena;

@Path("/resena")
public class ResenaResource {

	@Context
	private UriInfo uriInfo;
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();

	@Context
	private SecurityContext security;

	// Raul tiene el el Libro collection aqui;

	
	@POST
	@Consumes(MediaType.LIBROS_API_RESENA)
	@Produces(MediaType.LIBROS_API_RESENA)
	public Resena createResena(Resena resena) {

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
			String sql = "select * from resenas where username='"+resena.getUsername()+"' and libroid='"+resena.getLibroid()+"';";
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
				throw new ResenaNotFoundException();
			
						
			sql = "insert into resenas (libroid, username, content) ";
			sql+= "values ("+resena.getLibroid()+", '"+resena.getUsername()+"', '"+resena.getContent()+"');";
			// le indicamos que nso devuelva la primary key que le genere a la
			// nueva entrada
			stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			rs = stmt.getGeneratedKeys();
			// leemos la primary key
			if (rs.next()) {
				int id = rs.getInt(1);
				rs.close();
				// TODO: Retrieve the created sting from the database to get all
				// the remaining fields
				sql = "select users.name, resenas.* from users,resenas where resenas.resenaid="+id+" and users.username=resenas.username";

				rs = stmt.executeQuery(sql);
				rs.next();
				resena.setResenaid(id);
				resena.setLibroid(rs.getInt("libroid"));
				resena.setContent(rs.getString("content"));
				resena.setUsername(rs.getString("content"));
				resena.setLasupdate(rs.getDate("lastUpdate"));
				//resena.addLink(LibrosAPILinkBuilder.buildURISting(uriInfo, libro));
				
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
		return resena;
	}
	
	@PUT
	@Path("/{resenaid}")
	@Consumes(MediaType.LIBROS_API_RESENA)
	@Produces(MediaType.LIBROS_API_RESENA)
	public Resena updateSting(@PathParam("resenaid") String resenaid, Resena resena) {
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
			
			// creamos el statement y la consulta
			stmt = conn.createStatement();
			String sql ;
					
					
			sql = "update  resenas SET resenas.content='"					
					+  resena.getContent()+ "' where resenaid=" + resenaid;
			// realizamos la consulta

			int rows = stmt.executeUpdate(sql);
			
			if (rows == 0)
				throw new LibroNotFoundException();

			sql = "select * from resenas where resenaid=" + resenaid;
			
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				// creamos la resena
				resena.setResenaid(rs.getInt("resenaid"));
				resena.setLibroid(rs.getInt("libroid"));
				resena.setUsername(rs.getString("username"));
				resena.setContent(rs.getString("content"));
				resena.setLasupdate(rs.getTimestamp("lastUpdate"));
				// añadimos los links
				//libro.addLink(LibrosAPILinkBuilder.buildURISting(uriInfo, libro));
				
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

		return resena;
	}
	
	@DELETE
	@Path("/{resenaid}")
	public void deleteResena(@PathParam("resenaid") String resenaid) {
		// TODO Delete record in database stings identified by stingid.
		Connection con = null;
		Statement stmt = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}
		try {
			stmt = con.createStatement();
			String query = "DELETE FROM resenas WHERE resenaid=" + resenaid + ";";
			int rows = stmt.executeUpdate(query);
			if (rows == 0)
				throw new LibroNotFoundException();
		} catch (SQLException e) {
			throw new InternalServerException(e.getMessage());
		} finally {
			try {
				con.close();
				stmt.close();
			} catch (Exception e) {
			}
		}
	}
	
}