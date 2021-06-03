package news.raf.backend.repositories;

import news.raf.backend.entities.Comment;
import news.raf.backend.repositories.interfaces.CommentRepositoryInterface;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

public class CommentRepository extends AbstractRepository<Comment> implements CommentRepositoryInterface {
    @Override
    public List<Comment> byPost(int page, String id) {
        TypedQuery<Comment> query = entityManager.createQuery("select comment FROM Comment comment join comment.post post where post.id = :id order by comment.created desc",Comment.class);
        query.setParameter("id",id);
        query.setFirstResult((page-1)*PAGE_SIZE);
        query.setMaxResults(PAGE_SIZE);
        return query.getResultList();
    }

    @Override
    public long countByPost(String id) {
        try {
            Query query = entityManager.createQuery("select count(comment.id) FROM Comment comment join comment.post post where post.id = :id order by comment.created desc");
            query.setParameter("id",id);
            return (long) query.getSingleResult();
        }catch (NoResultException e){
            return 0;
        }
    }
}
