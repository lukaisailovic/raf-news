package news.raf.backend.requests.category;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BasicCategoryRequest {

    @NotEmpty(message = "Name cannot be empty")
    @NotNull(message = "Name cannot be empty")
    @Size(min = 1,max = 254,message = "Name must be between 1 and 254 characters")
    private String name;

    @NotNull(message = "Description cannot be empty")
    @NotEmpty(message = "Description cannot be empty")
    @Size(min = 1,max = 254,message = "Description must be between 1 and 254 characters")
    private String description;

    public BasicCategoryRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
