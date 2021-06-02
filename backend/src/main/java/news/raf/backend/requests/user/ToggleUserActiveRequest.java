package news.raf.backend.requests.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ToggleUserActiveRequest {

    @NotNull(message = "Active cannot be empty")
    @Pattern(regexp = "^true$|^false$", message = "Boolean can only be true or false")
    private String active;

    public ToggleUserActiveRequest() {
    }

    public boolean isActive(){
        return Boolean.parseBoolean(this.active);
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
