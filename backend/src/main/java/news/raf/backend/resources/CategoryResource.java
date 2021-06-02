package news.raf.backend.resources;



import news.raf.backend.authentication.annotations.Authorized;
import news.raf.backend.core.ApplicationResponseBuilder;
import news.raf.backend.core.annotations.NotEmptyBody;
import news.raf.backend.entities.Category;
import news.raf.backend.repositories.interfaces.CategoryRepositoryInterface;
import news.raf.backend.requests.category.CreateCategoryRequest;

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
}
