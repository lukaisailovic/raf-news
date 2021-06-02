package news.raf.backend.resources;

import news.raf.backend.authentication.ApplicationSecurityContext;
import news.raf.backend.authentication.SecurityUser;
import news.raf.backend.authentication.annotations.Authorized;
import news.raf.backend.core.ApplicationResponseBuilder;
import news.raf.backend.entities.User;
import news.raf.backend.repositories.interfaces.UserRepositoryInterface;


import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;


@Path("/users")
public class UserResource extends BasicResource{

    @Inject
    private UserRepositoryInterface userRepository;

    @GET
    @Authorized
    @RolesAllowed({"ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response all(@DefaultValue("1") @QueryParam("page") int page){
        List<User> users = userRepository.all(page);
        int count = (int) userRepository.count();
        return ApplicationResponseBuilder
                .status(Response.Status.OK)
                .data(users)
                .paginated(count,userRepository.getPageSize(),page)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/me")
    @Authorized
    @RolesAllowed({"ADMIN","CONTENT_CREATOR"})
    public Response self(){

        SecurityUser securityUser = ((ApplicationSecurityContext) context.getSecurityContext()).getSecurityUser();
        User user = this.userRepository.findBy("email",securityUser.getEmail());
        HashMap<String ,Object> msg= new HashMap<String,Object>();
        msg.put("user",user);
        msg.put("security user",securityUser);
        return ApplicationResponseBuilder.status(Response.Status.OK).data(msg).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/me/asadmin")
    @Authorized
    @RolesAllowed({"ADMIN"})
    public Response adminOnly(){
        return ApplicationResponseBuilder.status(Response.Status.OK).data("You are an admin").build();
    }
}
