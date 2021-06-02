package news.raf.backend.repositories;

import news.raf.backend.entities.Tag;
import news.raf.backend.repositories.interfaces.TagRepositoryInterface;

import javax.persistence.TypedQuery;
import java.util.List;

public class TagRepository extends AbstractRepository<Tag> implements TagRepositoryInterface {

    public List<Tag> getByNames(List<String> names) {
        TypedQuery<Tag> query = entityManager.createQuery("select tag from Tag tag where tag.description in :names",Tag.class);
        query.setParameter("names",names);
        return query.getResultList();
    }
}
