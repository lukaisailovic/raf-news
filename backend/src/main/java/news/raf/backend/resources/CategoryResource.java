package news.raf.backend.resources;



import news.raf.backend.core.ApplicationResponseBuilder;
import news.raf.backend.entities.Category;
import news.raf.backend.repositories.interfaces.CategoryRepositoryInterface;

import javax.inject.Inject;
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
}
