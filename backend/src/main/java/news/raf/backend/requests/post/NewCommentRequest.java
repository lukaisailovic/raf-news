package news.raf.backend.requests.post;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NewCommentRequest {

    @NotEmpty(message = "Author name cannot be empty")
    @NotNull(message = "Author name be empty")
    @Size(min = 1,max = 149,message = "Author name must be between 1 and 149 characters")
    private String author;


    @NotEmpty(message = "Text cannot be empty")
    @NotNull(message = "Text cannot be empty")
    @Size(min = 1,max = 511,message = "Text must be between 1 and 511 characters")
    private String text;

    public NewCommentRequest() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
