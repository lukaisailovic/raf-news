package news.raf.backend.authentication;

import news.raf.backend.authentication.requests.SignUpRequest;
import news.raf.backend.core.annotations.NotEmptyBody;


import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/auth")
public class AuthenticationEndpoints {

    @POST
    @Path("/signup")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @NotEmptyBody
    public Response signup(@Valid SignUpRequest signUpRequest){
            return Response.ok(signUpRequest).build();
    }
}
