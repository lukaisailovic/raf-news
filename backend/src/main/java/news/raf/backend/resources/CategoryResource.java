package news.raf.backend.resources;



import news.raf.backend.authentication.annotations.Authorized;
import news.raf.backend.core.ApplicationResponseBuilder;
import news.raf.backend.core.annotations.NotEmptyBody;
import news.raf.backend.entities.Category;
import news.raf.backend.entities.Post;
import news.raf.backend.repositories.interfaces.CategoryRepositoryInterface;
import news.raf.backend.repositories.interfaces.PostRepositoryInterface;
import news.raf.backend.requests.category.CreateCategoryRequest;
import news.raf.backend.requests.category.EditCategoryRequest;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/categories")
public class CategoryResource extends BasicResource{

    @Inject
    private CategoryRepositoryInterface categoryRepository;

    @Inject
    private PostRepositoryInterface postRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all(@DefaultValue("1") @QueryParam("page") int page){
        List<Category> categories = categoryRepository.all(page);
        int count = (int) categoryRepository.count();
        return ApplicationResponseBuilder
                .status(Response.Status.OK)
                .data(categories)
                .paginated(count,categoryRepository.getPageSize(),page)
                .build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response single(@PathParam("id") String id){
        Category category = categoryRepository.find(id);
        if (category == null){
            return ApplicationResponseBuilder.status(Response.Status.BAD_REQUEST).data("Category with that ID does not exist").build();
        }
        return ApplicationResponseBuilder.status(Response.Status.OK).data(category).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authorized
    @NotEmptyBody
    public Response create(@Valid CreateCategoryRequest request){
        if (categoryRepository.existsBy("name", request.getName())){
            return ApplicationResponseBuilder.status(Response.Status.BAD_REQUEST).data("Category with that name already exists").build();
        }
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        categoryRepository.save(category);
        return ApplicationResponseBuilder.status(Response.Status.OK).data(category).build();
    }

    @PATCH
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authorized
    @NotEmptyBody
    public Response edit(@PathParam("id") String id,@Valid EditCategoryRequest request){
        Category category = categoryRepository.find(id);
        if (category == null){
            return ApplicationResponseBuilder.status(Response.Status.BAD_REQUEST).data("Category with that ID does not exist").build();
        }
        if (!request.getName().equals(category.getName()) && categoryRepository.existsBy("name", request.getName())){
            return ApplicationResponseBuilder.status(Response.Status.BAD_REQUEST).data("Category with that name already exists").build();
        }
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        categoryRepository.save(category);
        return ApplicationResponseBuilder.status(Response.Status.OK).data(category).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Authorized
    public Response delete(@PathParam("id") String id){
        Category category = categoryRepository.find(id);
        if (category == null){
            return ApplicationResponseBuilder.status(Response.Status.BAD_REQUEST).data("Category with that ID does not exist").build();
        }
        List<Post> postsInCategory = postRepository.findByCategory(1,id);
        if (postsInCategory.size() > 0){
            return ApplicationResponseBuilder.status(Response.Status.BAD_REQUEST).data("Category that has posts cannot be deleted").build();
        }
        categoryRepository.delete(category);
        return ApplicationResponseBuilder.status(Response.Status.OK).data("Category has been deleted").build();
    }
}
