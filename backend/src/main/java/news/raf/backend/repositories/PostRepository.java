package news.raf.backend.repositories;

import news.raf.backend.entities.Post;
import news.raf.backend.repositories.interfaces.PostRepositoryInterface;

import javax.persistence.NoResultException;
import javax.persistence.Query;
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

    @Override
    public long countByCategory(String id) {
        try {
            Query query = entityManager.createQuery("select count(post.id) FROM Post post join post.category category where category.id = :id order by post.created desc");
            query.setParameter("id",id);
            return (long) query.getSingleResult();
        }catch (NoResultException e){
            return 0;
        }
    }

    @Override
    public List<Post> mostPopular() {
        TypedQuery<Post> query = entityManager.createQuery("select post FROM Post post order by post.viewCount desc",Post.class);
        query.setMaxResults(PAGE_SIZE);
        return query.getResultList();
    }
}
