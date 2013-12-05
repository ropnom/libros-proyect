package eetac.upc.edu.dsa.rodrigo.libros.auth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;

import javax.sql.DataSource;
/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DataSource ds;

    
    public RegisterServlet() {
        // TODO Auto-generated constructor stub
    }
    
    
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		ds = DataSourceSPA.getInstance().getDataSource();
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String action = request.getParameter("action");
		
		if( action.equals("REGISTER"))
		{
			String username,pass,name,email;
            username = request.getParameter("nick");
            pass = request.getParameter("pass");
            name = request.getParameter("nombre");
            email = request.getParameter("correo");
            try {
                    Connection con = ds.getConnection();
                    con.setAutoCommit(false);
                    Statement stmt = con.createStatement();
                    String update = "INSERT INTO users VALUES('"+username+"', MD5('"+pass+"'),'"+name+"','"+email+"');";
                    int row = stmt.executeUpdate(update);
                   
                    update = "INSERT INTO user_roles VALUES('"+username+"','registered');";
                    row = stmt.executeUpdate(update);
                    
                    HttpHost targetHost = new HttpHost("localhost", 8000, "http");
            		CredentialsProvider credsProvider = new BasicCredentialsProvider();
            		credsProvider.setCredentials(new AuthScope(targetHost.getHostName(),
            				targetHost.getPort()), new UsernamePasswordCredentials("Administrador",
            				"Administrador"));
             
            		// Create AuthCache instance
            		AuthCache authCache = new BasicAuthCache();
            		// Generate BASIC scheme object and add it to the local auth cache
            		BasicScheme basicAuth = new BasicScheme();
            		authCache.put(targetHost, basicAuth);
             
            		// Add AuthCache to the execution context
            		HttpClientContext context = HttpClientContext.create();
            		context.setCredentialsProvider(credsProvider);
             
            		HttpPost httpPost = new HttpPost(
            				"http://localhost:8000/beeter-api/users");
            		httpPost.addHeader("Content-Type",
            				"application/vnd.beeter.api.user+json");
            		httpPost.addHeader("Accept",
            				"application/vnd.beeter.api.user+json");
            		
            		JSONObject obj = new JSONObject();
            		obj.put("name", name);
            		obj.put("username", username);
            		obj.put("email", email);
            		
            		String user = obj.toJSONString();
            		
            		httpPost.setEntity(new StringEntity(user));
            		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
             
            		CloseableHttpResponse httpResponse = closeableHttpClient.execute(
            				targetHost, httpPost, context);
            		HttpEntity entity = httpResponse.getEntity();
            		
            		BufferedReader reader = new BufferedReader(new InputStreamReader(
            				entity.getContent()));
            		String line = null;        		
            		JSONParser parser = new JSONParser();
            		while ((line = reader.readLine()) != null){
            			System.out.println(line);
            			
            		}
            		httpResponse.close();
            		
            		if(line.equals(user))
            		{
            			con.commit();
            		}
            		stmt.close();
                    con.close();
                    
            } catch (Exception e) {
            	e.printStackTrace();
            }
            String url = "/validar.jsp";
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            rd.forward(request, response);      
    
        		
			
		} else
		{
			String url = "/Register.jsp";
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(url);
            rd.forward(request, response); 
		}
		
	}

}
