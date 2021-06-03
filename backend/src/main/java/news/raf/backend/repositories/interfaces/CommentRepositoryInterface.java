package news.raf.backend.repositories.interfaces;

import news.raf.backend.entities.Comment;

import java.util.List;

public interface CommentRepositoryInterface extends AbstractRepositoryInterface<Comment> {

    List<Comment> byPost(int page,String id);
    long countByPost(String id);
}
