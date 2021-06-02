package news.raf.backend.repositories;


import news.raf.backend.entities.User;
import news.raf.backend.repositories.interfaces.UserRepositoryInterface;

import javax.inject.Inject;
import javax.persistence.EntityManager;



public class UserRepository extends AbstractRepository<User> implements UserRepositoryInterface {

    @Inject
    EntityManager entityManager;



}
