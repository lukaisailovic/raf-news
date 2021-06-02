package news.raf.backend.resources;


import news.raf.backend.authentication.annotations.Authorized;
import news.raf.backend.core.ApplicationResponseBuilder;
import news.raf.backend.core.annotations.NotEmptyBody;
import news.raf.backend.entities.Category;
import news.raf.backend.entities.Post;
import news.raf.backend.entities.User;
import news.raf.backend.repositories.interfaces.CategoryRepositoryInterface;
import news.raf.backend.repositories.interfaces.PostRepositoryInterface;
import news.raf.backend.requests.post.CreatePostRequest;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/posts")
public class PostResource extends BasicResource{

    @Inject
    private PostRepositoryInterface postRepository;

    @Inject
    private CategoryRepositoryInterface categoryRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all(@DefaultValue("1") @QueryParam("page") int page){
        List<Post> posts = postRepository.all(page);
        int count = (int) postRepository.count();
        return ApplicationResponseBuilder
                .status(Response.Status.OK)
                .data(posts)
                .paginated(count,postRepository.getPageSize(),page)
                .build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authorized
    @NotEmptyBody
    public Response create(@Valid CreatePostRequest request){
        Category category = categoryRepository.find(request.getCategoryId());
        if (category == null){
            return ApplicationResponseBuilder.status(Response.Status.BAD_REQUEST).data("Category with that ID does not exist").build();
        }
        User user = getCurrentlyAuthenticatedUser();
        Post post = new Post();
        post.setAuthor(user);
        post.setCategory(category);
        post.setTitle(request.getTitle());
        post.setText(request.getText());
        this.postRepository.save(post);
        return ApplicationResponseBuilder.status(Response.Status.OK).data(post).build();
    }
}
