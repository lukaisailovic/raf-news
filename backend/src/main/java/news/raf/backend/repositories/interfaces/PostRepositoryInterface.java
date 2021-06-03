package news.raf.backend.repositories.interfaces;

import news.raf.backend.entities.Post;

import java.util.List;

public interface PostRepositoryInterface extends AbstractRepositoryInterface<Post>{
    List<Post>  findByCategory(int page, String id);
    long countByCategory(String id);
    List<Post>  findByTag(int page, String tag);
    long countByTag(String tag);
    List<Post>  mostPopular();
}
