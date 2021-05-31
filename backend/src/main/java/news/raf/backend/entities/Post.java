package news.raf.backend.entities;


import org.eclipse.persistence.annotations.UuidGenerator;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "posts")
@UuidGenerator(name="POST_ID_GEN")
public class Post {

    @Id
    @GeneratedValue(generator="POST_ID_GEN")
    private String id;

    @Column(name = "title", unique = true, nullable = false, length = 150)
    private String title;

    @Column(name = "text", nullable = false, length = 1024)
    private String text;

    @Column(name = "view_count", nullable = false)
    private Integer viewCount = 0;

    @Column(name = "created_at", nullable = false,updatable = false)
    private Date created;

    @Column(name = "updated_at", nullable = false)
    private Date updated;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @PrePersist
    protected void onCreate() {
        created = new Date(System.currentTimeMillis());
        updated = new Date(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date(System.currentTimeMillis());
    }

    public Post() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
