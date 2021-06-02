package news.raf.backend.requests.post;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class BasicPostRequest {

    @NotEmpty(message = "Title cannot be empty")
    @NotNull(message = "Title cannot be empty")
    @Size(min = 1,max = 254,message = "Title must be between 1 and 254 characters")
    private String title;

    @NotEmpty(message = "Category ID cannot be empty")
    @NotNull(message = "Category ID cannot be empty")
    private String categoryId;

    @NotEmpty(message = "Text cannot be empty")
    @NotNull(message = "Text cannot be empty")
    @Size(min = 1,max = 1023,message = "Text must be between 1 and 1023 characters")
    private String text;

    private List<String> tags;

    public BasicPostRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
