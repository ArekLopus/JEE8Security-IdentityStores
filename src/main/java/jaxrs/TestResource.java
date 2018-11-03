package jaxrs;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

//-An IdentityStore with lower priority is treated first. Here:
//	DatabaseIdentityStore	- priotity 1
//	EmbeddedIdentityStore	- priotity 10
//	SimpleIdentityStore		- priotity 20

//http://localhost:8080/JEE8Security-IdentityStores/res/sec?name=mo&password=mo
//http://localhost:8080/JEE8Security-IdentityStores/res/sec?name=aa&password=aa
//http://localhost:8080/JEE8Security-IdentityStores/res/sec?name=ed&password=ed
//http://localhost:8080/JEE8Security-IdentityStores/res/sec/secured
@Path("sec")
@Produces(MediaType.TEXT_HTML)
public class TestResource {
	
	@Inject
	SecurityContext sc;
	
	@Context
	HttpServletRequest request;
	
	@Context
	HttpServletResponse response;
	
	
	@RolesAllowed("admin")
	@Path("secured")
	@GET
	public String testLogin() {
		
		return "Accessed";
	}

	
	
	@GET
	public String test(@QueryParam("name") String name, @QueryParam("password") String password) throws ServletException {
		
		if (request.getSession(false) != null) {
			request.logout();
			request.getSession().invalidate();
		}
		
		UsernamePasswordCredential credentials = new UsernamePasswordCredential(name, password);
		
		sc.authenticate(request, response, AuthenticationParameters.withParams().credential(credentials));
		
		if(sc.getCallerPrincipal() == null) {
			return "Principal NULL";
		}
		String info = "GET Query Login"
				+ "<br/>User: " + sc.getCallerPrincipal().getName()
				+ "<br/>is caller in role 'admin' -> "+sc.isCallerInRole("admin")
				+ "<br/>is caller in role 'user' -> "+sc.isCallerInRole("user")
				+ "<br/>is caller in role 'foo' -> "+sc.isCallerInRole("foo");
		
		return info;
	}
}
