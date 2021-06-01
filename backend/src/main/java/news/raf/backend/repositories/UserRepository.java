package news.raf.backend.repositories;


import news.raf.backend.entities.User;
import news.raf.backend.repositories.interfaces.UserRepositoryInterface;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
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
    public User findBy(String parameter, String value) {
        try {
            TypedQuery<User> query = entityManager.createQuery("Select user FROM User user WHERE user."+parameter+" = :value",User.class);
            query.setParameter("value",value);
            return query.getSingleResult();
        } catch (NoResultException exception){
            return null;
        }
    }
}
