package news.raf.backend.authentication;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class ApplicationSecurityContext implements SecurityContext {

    private final SecurityUser securityUser;

    public ApplicationSecurityContext(SecurityUser securityUser) {
        this.securityUser = securityUser;
    }

    public SecurityUser getSecurityUser() {
        return securityUser;
    }

    @Override
    public Principal getUserPrincipal() {
        return securityUser::getEmail;
    }

    @Override
    public boolean isUserInRole(String role) {
        return securityUser.getRole().equals(role);
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
