package news.raf.backend.resources;

import news.raf.backend.authentication.ApplicationSecurityContext;
import news.raf.backend.entities.User;
import news.raf.backend.repositories.interfaces.UserRepositoryInterface;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;

abstract class BasicResource {

    @Context
    ContainerRequestContext context;

    @Inject
    private UserRepositoryInterface userRepository;

    public User getCurrentlyAuthenticatedUser(){
        ApplicationSecurityContext securityContext = (ApplicationSecurityContext) context.getSecurityContext();
        String userEmail =  securityContext.getUserPrincipal().getName();
        return this.userRepository.findByEmail(userEmail);
    }
}
