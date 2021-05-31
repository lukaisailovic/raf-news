package news.raf.backend.repositories;


import news.raf.backend.entities.User;
import news.raf.backend.repositories.interfaces.UserRepositoryInterface;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
        entityManager.getTransaction().begin();
        User user = entityManager.find(User.class,id);
        entityManager.getTransaction().commit();
        return user;
    }

    @Override
    public User findByEmail(String email) {
        try {
            entityManager.getTransaction().begin();
            User result = (User) entityManager.createQuery("Select user FROM User user WHERE user.email = :email")
                    .setParameter("email",email).getSingleResult();
            entityManager.getTransaction().commit();
            return result;
        } catch (NoResultException exception){
            return null;
        }

    }
}
