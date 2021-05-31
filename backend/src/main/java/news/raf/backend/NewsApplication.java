package news.raf.backend;

import news.raf.backend.repositories.EntityManagerFactoryProvider;
import news.raf.backend.repositories.EntityManagerProvider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;

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
                this.bindFactory(EntityManagerFactoryProvider.class).to(EntityManagerFactory.class).in(Singleton.class);
                this.bindFactory(EntityManagerProvider.class).proxy(true).proxyForSameScope(false).to(EntityManager.class).in(RequestScoped.class);
            }
        };
        register(binder);

        packages("news.raf.backend.resources");
    }
}