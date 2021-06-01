package news.raf.backend.authentication.filters;

import news.raf.backend.authentication.ApplicationSecurityContext;
import news.raf.backend.authentication.Token;
import news.raf.backend.authentication.annotations.Authorized;
import news.raf.backend.core.ApplicationResponseBuilder;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@Priority(Priorities.AUTHENTICATION)
@Authorized
public class AuthenticationFilter implements ContainerRequestFilter {
    private static final String SCHEME = "Bearer";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith(SCHEME)){
            abort(requestContext);
        }
        try {
            String token = authorizationHeader.substring(SCHEME.length()).trim();
            String userEmail = Token.validate(token);
            if (userEmail == null || userEmail.length() == 0){
                abort(requestContext);
            }
            SecurityContext securityContext = new ApplicationSecurityContext(userEmail);
            requestContext.setSecurityContext(securityContext);
        }catch (Exception e){
            abort(requestContext);
        }
    }

    private void abort(ContainerRequestContext requestContext){
        requestContext.abortWith(ApplicationResponseBuilder.status(Response.Status.FORBIDDEN).data("You are not authorized to access this endpoint").build());
    }
}
