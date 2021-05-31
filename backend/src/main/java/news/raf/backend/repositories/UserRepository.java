package news.raf.backend.repositories;


import news.raf.backend.entities.User;
import news.raf.backend.repositories.interfaces.UserRepositoryInterface;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;


public class UserRepository implements UserRepositoryInterface {

    @Inject
    EntityManager entityManager;

    public void save(User user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<User> all() {
        return null;
    }

    @Override
    public User find(String id) {
        return null;
    }
}
