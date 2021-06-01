package news.raf.backend.authentication;

import news.raf.backend.authentication.requests.SignInRequest;
import news.raf.backend.authentication.requests.SignUpRequest;
import news.raf.backend.core.ApplicationResponseBuilder;
import news.raf.backend.core.annotations.NotEmptyBody;
import news.raf.backend.entities.User;
import news.raf.backend.repositories.interfaces.UserRepositoryInterface;
import org.mindrot.jbcrypt.BCrypt;


import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.HashSet;

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
        User potentialUser = this.userRepository.findBy("email",signUpRequest.getEmail());
        if (potentialUser != null){
            return ApplicationResponseBuilder.status(Response.Status.BAD_REQUEST).data("User with that email already exists").build();
        }
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        String hashedPassword = BCrypt.hashpw(signUpRequest.getPassword(),BCrypt.gensalt());
        user.setPassword(hashedPassword);
        this.userRepository.save(user);
        return ApplicationResponseBuilder.status(Response.Status.OK).data(user).build();
    }

    @POST
    @Path("/signin")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @NotEmptyBody
    public Response signin(@Valid SignInRequest signInRequest){
        User potentialUser = this.userRepository.findBy("email",signInRequest.getEmail());
        if (potentialUser == null){
            return ApplicationResponseBuilder.status(Response.Status.BAD_REQUEST).data("User with that email does not exist").build();
        }
        if (!BCrypt.checkpw(signInRequest.getPassword(),potentialUser.getPassword())){
            return ApplicationResponseBuilder.status(Response.Status.FORBIDDEN).data("Incorrect password").build();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("token",Token.generate(potentialUser));
        return ApplicationResponseBuilder.status(Response.Status.OK).data(map).build();
    }
}
