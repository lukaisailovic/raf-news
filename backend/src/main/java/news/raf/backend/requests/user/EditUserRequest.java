package news.raf.backend.requests.user;

import news.raf.backend.entities.UserType;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class EditUserRequest {


    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotNull(message = "First name cannot be empty")
    @NotEmpty(message = "First name cannot be empty")
    private String firstName;

    @NotNull(message = "Last name cannot be empty")
    @NotEmpty(message = "Last name cannot be empty")
    private String lastName;

    @NotNull(message = "User type cannot be empty")
    @NotEmpty(message = "User type cannot be empty")
    private String userType;

    public EditUserRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
