package news.raf.backend.repositories;

import org.glassfish.hk2.api.Factory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryProvider implements Factory<EntityManagerFactory> {

    private final EntityManagerFactory entityManagerFactory;

    public EntityManagerFactoryProvider() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("raf-news");
    }

    @Override
    public EntityManagerFactory provide() {
        return this.entityManagerFactory;
    }

    @Override
    public void dispose(EntityManagerFactory entityManagerFactory) {
        entityManagerFactory.close();
    }
}
