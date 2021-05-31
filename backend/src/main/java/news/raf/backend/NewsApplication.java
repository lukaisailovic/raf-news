package news.raf.backend;

import news.raf.backend.repositories.EntityManagerFactoryProvider;
import news.raf.backend.repositories.EntityManagerProvider;
import news.raf.backend.repositories.UserRepository;
import news.raf.backend.repositories.interfaces.UserRepositoryInterface;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class NewsApplication extends ResourceConfig {
    public NewsApplication() {
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(UserRepository.class).to(UserRepositoryInterface.class).in(Singleton.class);
                this.bindFactory(EntityManagerFactoryProvider.class).to(EntityManagerFactory.class).in(Singleton.class);
                this.bindFactory(EntityManagerProvider.class).proxy(true).proxyForSameScope(false).to(EntityManager.class).in(RequestScoped.class);
            }
        };
        register(binder);
        register(new ConstraintViolationMapper());
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        packages("news.raf.backend.authentication");
        packages("news.raf.backend.resources");
    }
}