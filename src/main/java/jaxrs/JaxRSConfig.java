package jaxrs;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


@DeclareRoles({ "admin", "user", "foo" })
@ApplicationPath("res")
public class JaxRSConfig extends Application {}
