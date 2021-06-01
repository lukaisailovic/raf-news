package news.raf.backend.resources;

import news.raf.backend.authentication.annotations.Authorized;
import news.raf.backend.core.ApplicationResponseBuilder;
import news.raf.backend.entities.User;
import news.raf.backend.repositories.interfaces.UserRepositoryInterface;


import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/users")
public class UserResource extends BasicResource{

    @Inject
    private UserRepositoryInterface userRepository;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all(){
        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setEmail("neki@email.com"+System.currentTimeMillis());
        user.setPassword("pass");
        this.userRepository.save(user);
        return Response.ok("CREATED").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/me")
    @Authorized
    public Response self(@Context ContainerRequestContext context){
        String userEmail = getUserEmail(context);
        return ApplicationResponseBuilder.status(Response.Status.OK).data("USER OBJECT WITH EMAIL "+ userEmail).build();
    }
}
