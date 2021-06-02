package news.raf.backend.repositories;

import news.raf.backend.entities.Post;
import news.raf.backend.repositories.interfaces.PostRepositoryInterface;

import javax.persistence.TypedQuery;
import java.util.List;

public class PostRepository extends AbstractRepository<Post> implements PostRepositoryInterface {

    @Override
    public List<Post> all(int page) {
        TypedQuery<Post> query = entityManager.createQuery("select post FROM Post post order by post.created desc",Post.class);
        query.setFirstResult((page-1)*PAGE_SIZE);
        query.setMaxResults(PAGE_SIZE);
        return query.getResultList();
    }

    @Override
    public List<Post> findByCategory(int page, String id) {
        TypedQuery<Post> query = entityManager.createQuery("select post FROM Post post join post.category category where category.id = :id order by post.created desc",Post.class);
        query.setParameter("id",id);
        query.setFirstResult((page-1)*PAGE_SIZE);
        query.setMaxResults(PAGE_SIZE);
        return query.getResultList();
    }
}
