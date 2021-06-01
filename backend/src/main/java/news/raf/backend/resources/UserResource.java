package news.raf.backend.resources;

import news.raf.backend.authentication.ApplicationSecurityContext;
import news.raf.backend.authentication.SecurityUser;
import news.raf.backend.authentication.annotations.Authorized;
import news.raf.backend.core.ApplicationResponseBuilder;
import news.raf.backend.entities.User;
import news.raf.backend.repositories.interfaces.UserRepositoryInterface;


import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;


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
    public Response self(){
        User user = getCurrentlyAuthenticatedUser();
        SecurityUser securityUser = ((ApplicationSecurityContext) context.getSecurityContext()).getSecurityUser();
        HashMap<String ,Object> msg= new HashMap<String,Object>();
        msg.put("user",user);
        msg.put("security user",securityUser);
        return ApplicationResponseBuilder.status(Response.Status.OK).data(msg).build();
    }
}
