package news.raf.backend.resources;


import news.raf.backend.core.ApplicationResponseBuilder;
import news.raf.backend.core.annotations.NotEmptyBody;
import news.raf.backend.entities.Comment;
import news.raf.backend.entities.Post;
import news.raf.backend.repositories.interfaces.CommentRepositoryInterface;
import news.raf.backend.repositories.interfaces.PostRepositoryInterface;
import news.raf.backend.requests.post.NewCommentRequest;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/comments")
public class CommentResource extends BasicResource{

    @Inject
    private PostRepositoryInterface postRepository;

    @Inject
    private CommentRepositoryInterface commentRepository;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response newComment(@PathParam("id") String id,@DefaultValue("1") @QueryParam("page") int page){
        Post post = postRepository.find(id);
        if (post == null){
            return ApplicationResponseBuilder.status(Response.Status.BAD_REQUEST).data("Post with that ID does not exist").build();
        }
        List<Comment> comments = this.commentRepository.byPost(page,id);
        int count = (int) commentRepository.countByPost(id);
        return ApplicationResponseBuilder
                .status(Response.Status.OK)
                .data(comments)
                .paginated(count,commentRepository.getPageSize(),page)
                .build();
    }

    @POST
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @NotEmptyBody
    public Response newComment(@PathParam("id") String id, @Valid NewCommentRequest request){
        Post post = postRepository.find(id);
        if (post == null){
            return ApplicationResponseBuilder.status(Response.Status.BAD_REQUEST).data("Post with that ID does not exist").build();
        }
        Comment comment = new Comment();
        comment.setAuthor(request.getAuthor());
        comment.setText(request.getText());
        post.addComment(comment);
        postRepository.save(post);

        return ApplicationResponseBuilder.status(Response.Status.OK).data(comment).build();
    }
}
