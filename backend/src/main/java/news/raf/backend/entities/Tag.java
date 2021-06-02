package news.raf.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.eclipse.persistence.annotations.UuidGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tags")
@UuidGenerator(name="TAG_ID_GEN")
public class Tag {

    @Id
    @GeneratedValue(generator="TAG_ID_GEN")
    private String id;

    @Column(name = "description",nullable = false)
    private String description;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private List<Post> posts;

    public Tag() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", posts=" + posts +
                '}';
    }
}
