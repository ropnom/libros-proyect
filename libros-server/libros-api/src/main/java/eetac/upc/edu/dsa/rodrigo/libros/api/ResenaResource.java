package eetac.upc.edu.dsa.rodrigo.libros.api;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import eetac.upc.edu.dsa.rodrigo.libros.api.links.ResenasAPILinkBuilder;
import eetac.upc.edu.dsa.rodrigo.libros.api.model.Resena;

//@Path("/resena")
@Path("/libros/{libroid}/resena")
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
	public Resena createResena(@PathParam("libroid") String libroid, Resena resena) {

		Statement stmt = null;

		/*
		 * if (sting.getSubject().length() > 100) { throw new
		 * BadRequestException(
		 * "Subject length must be less or equal than 100 characters"); } if
		 * (sting.getContent().length() > 500) { throw new BadRequestException(
		 * "Content length must be less or equal than 500 characters"); }
		 */

		// realizamos conexion

		if (security.isUserInRole("registered")) {
			if (!security.getUserPrincipal().getName()
					.equals(resena.getUsername())) {
				throw new ForbiddenException("You are nor allowed");
			}
		}

		resena.setLibroid(Integer.parseInt(libroid));
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
			String sql = "select * from resenas where username='"
					+ resena.getUsername() + "' and libroid='"
					+ resena.getLibroid() + "';";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next())
				throw new ResenaNotFoundException();

			sql = "insert into resenas (libroid, username, content) ";
			sql += "values (" + resena.getLibroid() + ", '"
					+ resena.getUsername() + "', '" + resena.getContent()
					+ "');";
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
				sql = "select users.name, resenas.* from users,resenas where resenas.resenaid="
						+ id + " and users.username=resenas.username";

				rs = stmt.executeQuery(sql);
				rs.next();
				resena.setResenaid(id);
				resena.setLibroid(rs.getInt("libroid"));
				resena.setContent(rs.getString("content"));
				resena.setUsername(rs.getString("username"));
				resena.setLasupdate(rs.getDate("lastUpdate"));
				resena.addLink(ResenasAPILinkBuilder.buildURIResenaId(uriInfo,
						resena.getLibroid(), resena.getResenaid(), "self"));
			

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
	public Resena updateResena(@PathParam("resenaid") String resenaid,
			Resena resena) {
		// TODO: Update in the database the record identified by stingid with
		// the data values in sting
		Statement stmt = null;

		/*
		 * if (sting.getSubject().length() > 100) { throw new
		 * BadRequestException(
		 * "Subject length must be less or equal than 100 characters"); } if
		 * (sting.getContent().length() > 500) { throw new BadRequestException(
		 * "Content length must be less or equal than 500 characters"); }
		 * 
		 * if (security.isUserInRole("registered")) { if
		 * (security.getUserPrincipal().getName() .equals(sting.getUsername()))
		 * { throw new ForbiddenException("You are nor allowed"); } }
		 */
		if (security.isUserInRole("registered")) {
			if (!security.getUserPrincipal().getName()
					.equals(resena.getUsername())) {
				throw new ForbiddenException("You are nor allowed");
			}
		}

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
			String sql;

			sql = "update  resenas SET resenas.content='" + resena.getContent()
					+ "' where resenaid=" + resenaid;
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
				resena.addLink(ResenasAPILinkBuilder.buildURIResenaId(uriInfo,
						resena.getLibroid(), resena.getResenaid(), "self"));
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
			String query = "Select resenas.username from resenas where resenaid="
					+ resenaid + ";";
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {

				String username = rs.getString("username");
				if (security.isUserInRole("registered")) {
					if (!security.getUserPrincipal().getName().equals(username)) {
						throw new ForbiddenException("You are nor allowed");
					}
				}

			} else {
				rs.close();
				throw new LibroNotFoundException();
			}

			query = "DELETE FROM resenas WHERE resenaid=" + resenaid + ";";
			int rows = stmt.executeUpdate(query);
			if (rows == 0)
				throw new LibroNotFoundException();

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
	}
	
	@GET
    @Path("/{resenaid}")
    @Produces(MediaType.LIBROS_API_RESENA)
    public Response getResena(@PathParam("libroid") String libroid, @PathParam("resenaid") String resenaid, @Context Request req){
    //public Response getResena(@PathParam("libroid") String libroid, @Context Request req){
            
            // Create CacheControl
            CacheControl cc = new CacheControl();
            
            Resena resena = new Resena();
            
            Connection con = null;
            Statement stmt = null;
            try {
                    con = ds.getConnection();
            } catch (SQLException e) {
                    throw new ServiceUnavailableException(e.getMessage());
            }
            
            try {
                    stmt = con.createStatement();
                    String query = "SELECT * FROM resenas WHERE resenaid=" + resenaid + " and libroid="+libroid+";";
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                            resena.setContent(rs.getString("content"));
                            resena.setLasupdate(rs.getTimestamp("lastUpdate"));
                            resena.setLibroid(rs.getInt("libroid"));
                            resena.setResenaid(rs.getInt("resenaid"));
                            resena.setUsername(rs.getString("username"));                
                            resena.addLink(ResenasAPILinkBuilder.buildURIResenaId(uriInfo, Integer.parseInt(libroid), Integer.parseInt(resenaid), "self"));
                    } else
                            throw new ResenaNotFoundException();
            } catch (SQLException e) {
                    throw new InternalServerException(e.getMessage());
            } finally {
                    try {
                            con.close();
                            stmt.close();
                    } catch (Exception e) {
                    }
            }

            // Calculate the ETag on last modified date of user resource
            EntityTag eTag = new EntityTag(Integer.toString(resena.getLasupdate().hashCode()));

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
            rb = Response.ok(resena).cacheControl(cc).tag(eTag);

            return rb.build();
    }


}