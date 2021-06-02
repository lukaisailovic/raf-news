package news.raf.backend.requests.user;

import news.raf.backend.authentication.requests.SignUpRequest;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CreateUserRequest extends SignUpRequest {

    @NotNull(message = "User type cannot be empty")
    @NotEmpty(message = "User type cannot be empty")
    private String userType;

    @NotNull(message = "Password confirmation cannot be empty")
    @NotEmpty(message = "Password confirmation cannot be empty")
    private String passwordConfirm;

    public CreateUserRequest() {
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}
