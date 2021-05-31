package news.raf.backend.entities;

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

    @Column(name = "description",nullable = false, unique = true)
    private String description;

    @ManyToMany(mappedBy = "tags")
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
}
