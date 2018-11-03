package stores;

import org.glassfish.soteria.identitystores.annotation.EmbeddedIdentityStoreDefinition;

import javax.enterprise.context.ApplicationScoped;

import org.glassfish.soteria.identitystores.annotation.Credentials;

//An IdentityStore with lower priority is treated first.
@EmbeddedIdentityStoreDefinition(value={
    @Credentials(callerName = "mo", password = "mo", groups = {"admin", "user"}),
    @Credentials(callerName = "ed", password = "ed", groups = {"admin"}),
    @Credentials(callerName = "michael", password = "michael", groups = {"foo"})},
	
	priority=10
 )

@ApplicationScoped
public class EmbeddedIdentityStore {}
