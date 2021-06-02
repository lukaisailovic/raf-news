package news.raf.backend.repositories.interfaces;

import java.util.List;

public interface AbstractRepositoryInterface<T> {
    List<T> all(int page);
    long count();
    T find(String id);
    T findBy(String parameter, String value);
    boolean existsBy(String parameter, String value);
    void save(T entity);
    int getPageSize();
}
