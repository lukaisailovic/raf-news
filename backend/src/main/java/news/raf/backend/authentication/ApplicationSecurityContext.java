package news.raf.backend.authentication;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class ApplicationSecurityContext implements SecurityContext {

    private String userEmail;

    public ApplicationSecurityContext(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public Principal getUserPrincipal() {
        return () -> userEmail;
    }

    @Override
    public boolean isUserInRole(String role) {
        return false;
    }

    @Override
    public boolean isSecure() {
        return true;
    }

    @Override
    public String getAuthenticationScheme() {
        return "Token-Based-Auth-Scheme";
    }
}
