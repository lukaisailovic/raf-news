package news.raf.backend.resources;

import news.raf.backend.authentication.ApplicationSecurityContext;

import javax.ws.rs.container.ContainerRequestContext;

abstract class BasicResource {

    public String getUserEmail(ContainerRequestContext context){
        ApplicationSecurityContext securityContext = (ApplicationSecurityContext) context.getSecurityContext();
        return securityContext.getUserPrincipal().getName();
    }
}
