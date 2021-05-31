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
}
