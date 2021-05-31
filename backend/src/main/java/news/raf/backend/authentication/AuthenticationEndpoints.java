package news.raf.backend.authentication;

import news.raf.backend.authentication.requests.SignUpRequest;
import news.raf.backend.core.ApplicationResponseBuilder;
import news.raf.backend.core.annotations.NotEmptyBody;
import news.raf.backend.entities.User;
import news.raf.backend.repositories.interfaces.UserRepositoryInterface;


import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/auth")
public class AuthenticationEndpoints {

    @Inject
    private UserRepositoryInterface userRepository;

    @POST
    @Path("/signup")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @NotEmptyBody
    public Response signup(@Valid SignUpRequest signUpRequest){
        User potentialUser = this.userRepository.findByEmail(signUpRequest.getEmail());
        if (potentialUser != null){
            return ApplicationResponseBuilder.status(Response.Status.BAD_REQUEST).data("User with that email already exists").build();
        }
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setPassword(signUpRequest.getPassword());
        this.userRepository.save(user);
        return ApplicationResponseBuilder.status(Response.Status.OK).data(user).build();
    }
}
