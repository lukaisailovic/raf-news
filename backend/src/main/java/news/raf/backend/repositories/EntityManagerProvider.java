package news.raf.backend.repositories;

import org.glassfish.hk2.api.Factory;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


public class EntityManagerProvider implements Factory<EntityManager> {

    private final EntityManager entityManager;

    @Inject
    public EntityManagerProvider(EntityManagerFactory entityManagerFactory) {
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public EntityManager provide() {
        return this.entityManager;
    }

    @Override
    public void dispose(EntityManager entityManager) {
        entityManager.close();
    }
}
