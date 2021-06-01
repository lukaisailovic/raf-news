package news.raf.backend.repositories;


import news.raf.backend.entities.User;
import news.raf.backend.repositories.interfaces.UserRepositoryInterface;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;


public class UserRepository extends AbstractRepository<User> implements UserRepositoryInterface {

    @Inject
    EntityManager entityManager;


    @Override
    public List<User> all() {
        return null;
    }




}
