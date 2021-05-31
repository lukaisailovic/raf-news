package news.raf.backend.entities;


import org.eclipse.persistence.annotations.UuidGenerator;

import javax.persistence.*;

@Entity
@Table(name = "categories")
@UuidGenerator(name="CATEGORY_ID_GEN")
public class Category {

    @Id
    @GeneratedValue(generator="USER_ID_GEN")
    private String id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    public Category() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
