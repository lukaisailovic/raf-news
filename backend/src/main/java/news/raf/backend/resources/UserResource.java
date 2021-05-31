package news.raf.backend.resources;

import news.raf.backend.entities.User;
import news.raf.backend.repositories.interfaces.UserRepositoryInterface;


import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/users")
public class UserResource {

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
}
