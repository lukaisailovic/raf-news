package news.raf.backend.entities;

import org.eclipse.persistence.annotations.UuidGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
@UuidGenerator(name="USER_ID_GEN")
public class User {

    @Id
    @GeneratedValue(generator="USER_ID_GEN")
    private String id;

    @Column(name = "email",unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "user_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private UserType userType = UserType.CONTENT_CREATOR;

    @Column(name = "active", nullable = false)
    private boolean active = false;

    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL)
    private List<Post> posts;

    public User() {
        posts = new ArrayList<>();
    }

    public List<Post> getPosts() {
        return Collections.unmodifiableList(posts);
    }

    public void addPost(Post post){
        post.setAuthor(this);
        posts.add(post);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
