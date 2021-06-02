package news.raf.backend.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.eclipse.persistence.annotations.UuidGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comments")
@UuidGenerator(name="COMMENT_ID_GEN")
public class Comment {

    @Id
    @GeneratedValue(generator="COMMENT_ID_GEN")
    private String id;

    @Column(name = "author", nullable = false, length = 150)
    private String author;

    @Column(name = "text", nullable = false, length = 512)
    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false,updatable = false,columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date created;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;

    @PrePersist
    protected void onCreate() {
        created = new Date(System.currentTimeMillis());
    }

    public Comment() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
