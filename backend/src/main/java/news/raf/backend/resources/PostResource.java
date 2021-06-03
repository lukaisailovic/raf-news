package news.raf.backend.resources;


import news.raf.backend.authentication.annotations.Authorized;
import news.raf.backend.core.ApplicationResponseBuilder;
import news.raf.backend.core.annotations.NotEmptyBody;
import news.raf.backend.entities.*;
import news.raf.backend.repositories.interfaces.CategoryRepositoryInterface;
import news.raf.backend.repositories.interfaces.PostRepositoryInterface;
import news.raf.backend.repositories.interfaces.TagRepositoryInterface;
import news.raf.backend.requests.post.CreatePostRequest;
import news.raf.backend.requests.post.EditPostRequest;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/posts")
public class PostResource extends BasicResource{

    @Inject
    private PostRepositoryInterface postRepository;

    @Inject
    private CategoryRepositoryInterface categoryRepository;

    @Inject
    private TagRepositoryInterface tagRepository;


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

    @GET
    @Path("/filter")
    @Produces(MediaType.APPLICATION_JSON)
    public Response filter(
            @DefaultValue("1") @QueryParam("page") int page,
            @NotNull @NotEmpty @QueryParam("param") String param,
            @NotNull @NotEmpty  @QueryParam("value") String value
            ){
        List<Post> posts = new ArrayList<>();
        int count = 0;
        if (param.equals("category")){
            posts = this.postRepository.findByCategory(page,value);
            count = (int) this.postRepository.countByCategory(value);
        }

        return ApplicationResponseBuilder
                .status(Response.Status.OK)
                .data(posts)
                .paginated(count,postRepository.getPageSize(),page)
                .build();
    }

    @GET
    @Path("/popular")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mostPopular(){
        List<Post> posts = postRepository.mostPopular();
        return ApplicationResponseBuilder.status(Response.Status.OK).data(posts).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response single(@PathParam("id") String id){
        Post post = postRepository.find(id);
        if (post == null){
            return ApplicationResponseBuilder.status(Response.Status.BAD_REQUEST).data("Post with that ID does not exist").build();
        }
        post.setViewCount(post.getViewCount()+1);
        postRepository.save(post);
        return ApplicationResponseBuilder.status(Response.Status.OK).data(post).build();
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
        List<Tag> tagsToAttach = new ArrayList<>();
        if (request.getTags() != null && request.getTags().size() > 0){
            tagsToAttach = getTagsForPost(request.getTags());
        }
        User user = getCurrentlyAuthenticatedUser();
        Post post = new Post();
        post.setAuthor(user);
        post.setCategory(category);
        post.setTitle(request.getTitle());
        post.setText(request.getText());
        for (Tag tag : tagsToAttach){
            post.addTag(tag);
        }
        this.postRepository.save(post);
        return ApplicationResponseBuilder.status(Response.Status.OK).data(post).build();
    }

    @PATCH
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authorized
    @NotEmptyBody
    public Response edit(@PathParam("id") String id,@Valid EditPostRequest request){
        Post post = postRepository.find(id);
        if (post == null){
            return ApplicationResponseBuilder.status(Response.Status.BAD_REQUEST).data("Post with that ID does not exist").build();
        }
        post.setTags(new ArrayList<>());
        postRepository.save(post);
        Category category = categoryRepository.find(request.getCategoryId());
        if (category == null){
            return ApplicationResponseBuilder.status(Response.Status.BAD_REQUEST).data("Category with that ID does not exist").build();
        }
        List<Tag> tagsToAttach = new ArrayList<>();
        if (request.getTags() != null && request.getTags().size() > 0){
            tagsToAttach = getTagsForPost(request.getTags());
        }
        User user = getCurrentlyAuthenticatedUser();

        post.setAuthor(user);
        post.setCategory(category);
        post.setTitle(request.getTitle());
        post.setText(request.getText());
        for (Tag tag : tagsToAttach){
            post.addTag(tag);
        }
        this.postRepository.save(post);
        return ApplicationResponseBuilder.status(Response.Status.OK).data(post).build();
    }

    protected List<Tag> getTagsForPost(List<String> tagNames){
        List<Tag> tagsToAttach = new ArrayList<>();
        List<Tag> existingTags = tagRepository.getByNames(tagNames);
        for(String providedTagName: tagNames){
            Tag tagToAttach = null;
            for (Tag existingTag: existingTags){
                if (existingTag.getDescription().equals(providedTagName)){
                    tagToAttach = existingTag;
                }
            }
            if (tagToAttach != null){
                tagsToAttach.add(tagToAttach);
                continue;
            }
            Tag newTag = new Tag();
            newTag.setDescription(providedTagName);
            tagRepository.save(newTag);
            tagsToAttach.add(newTag);
        }
        return tagsToAttach;
    }


    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Authorized
    public Response delete(@PathParam("id") String id){
        Post post = postRepository.find(id);
        if (post == null){
            return ApplicationResponseBuilder.status(Response.Status.BAD_REQUEST).data("Post with that ID does not exist").build();
        }
        postRepository.delete(post);
        return ApplicationResponseBuilder.status(Response.Status.OK).data("Post has been deleted").build();
    }


}
