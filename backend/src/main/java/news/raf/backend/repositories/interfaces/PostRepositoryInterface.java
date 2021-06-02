package news.raf.backend.repositories.interfaces;

import news.raf.backend.entities.Post;

import java.util.List;

public interface PostRepositoryInterface extends AbstractRepositoryInterface<Post>{
    List<Post>  findByCategory(int page, String id);
    List<Post>  mostPopular();
}
