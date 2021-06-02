package news.raf.backend.resources;

import news.raf.backend.authentication.ApplicationSecurityContext;
import news.raf.backend.authentication.SecurityUser;
import news.raf.backend.authentication.annotations.Authorized;
import news.raf.backend.core.ApplicationResponseBuilder;
import news.raf.backend.core.annotations.NotEmptyBody;
import news.raf.backend.entities.User;
import news.raf.backend.entities.UserType;
import news.raf.backend.repositories.interfaces.UserRepositoryInterface;
import news.raf.backend.requests.user.CreateUserRequest;
import news.raf.backend.requests.user.EditUserRequest;
import news.raf.backend.requests.user.ToggleUserActiveRequest;
import org.mindrot.jbcrypt.BCrypt;


import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;


@Path("/users")
public class UserResource extends BasicResource{


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


    @PATCH
    @Path("{id}")
    @Authorized
    @RolesAllowed({"ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @NotEmptyBody
    public Response edit(@PathParam("id") String id, @Valid EditUserRequest request){
        User user = userRepository.find(id);
        if (user == null){
            return ApplicationResponseBuilder.status(Response.Status.BAD_REQUEST).data("User with that email does not exist").build();
        }
        if (!user.getEmail().equals(request.getEmail()) && userRepository.existsBy("email",request.getEmail())){
            return ApplicationResponseBuilder.status(Response.Status.BAD_REQUEST).data("User with that email already exists").build();
        }
        UserType userType;
        try{
            userType = UserType.valueOf(request.getUserType());
        } catch (Exception e){
            return ApplicationResponseBuilder.status(Response.Status.BAD_REQUEST).data("Invalid user type").build();
        }
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setUserType(userType);
        userRepository.save(user);
        return ApplicationResponseBuilder.status(Response.Status.OK).data(user).build();
    }

    @PATCH
    @Path("{id}/toggle/active")
    @Authorized
    @RolesAllowed({"ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @NotEmptyBody
    public Response toggleActive(@PathParam("id") String id, @Valid ToggleUserActiveRequest request){
        User user = userRepository.find(id);
        if (user == null){
            return ApplicationResponseBuilder.status(Response.Status.BAD_REQUEST).data("User with that email does not exist").build();
        }
        if (user.getUserType().equals(UserType.ADMIN)){
            return ApplicationResponseBuilder.status(Response.Status.BAD_REQUEST).data("Admin account cannot be deactivated").build();
        }
        user.setActive(request.isActive());
        userRepository.save(user);
        return ApplicationResponseBuilder.status(Response.Status.OK).data(user).build();
    }

    @POST
    @Authorized
    @RolesAllowed({"ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @NotEmptyBody
    public Response create( @Valid CreateUserRequest request){
        if (!request.getPassword().equals(request.getPasswordConfirm())){
            return ApplicationResponseBuilder.status(Response.Status.BAD_REQUEST).data("Password must be confirmed").build();
        }
        UserType userType;
        try{
            userType = UserType.valueOf(request.getUserType());
        } catch (Exception e){
            return ApplicationResponseBuilder.status(Response.Status.BAD_REQUEST).data("Invalid user type").build();
        }
        if (userRepository.existsBy("email",request.getEmail())){
            return ApplicationResponseBuilder.status(Response.Status.BAD_REQUEST).data("User with that email already exists").build();
        }
        User user = new User();
        user.setActive(true);
        user.setUserType(userType);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        String hashedPassword = BCrypt.hashpw(request.getPassword(),BCrypt.gensalt());
        user.setPassword(hashedPassword);
        userRepository.save(user);
        return ApplicationResponseBuilder.status(Response.Status.OK).data(user).build();
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
