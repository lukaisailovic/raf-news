package news.raf.backend.repositories.interfaces;

import news.raf.backend.entities.Tag;

import java.util.List;

public interface TagRepositoryInterface extends AbstractRepositoryInterface<Tag> {
    List<Tag> getByNames(List<String> names);
}
