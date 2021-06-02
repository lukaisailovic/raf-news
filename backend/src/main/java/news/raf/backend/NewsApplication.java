package news.raf.backend;

import news.raf.backend.authentication.filters.AuthenticationFilter;
import news.raf.backend.core.exceptions.ConstraintViolationMapper;
import news.raf.backend.core.NotEmptyBodyFilter;
import news.raf.backend.core.exceptions.ForbiddenExceptionMapper;
import news.raf.backend.repositories.CategoryRepository;
import news.raf.backend.repositories.EntityManagerFactoryProvider;
import news.raf.backend.repositories.EntityManagerProvider;
import news.raf.backend.repositories.UserRepository;
import news.raf.backend.repositories.interfaces.CategoryRepositoryInterface;
import news.raf.backend.repositories.interfaces.UserRepositoryInterface;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class NewsApplication extends ResourceConfig {
    public NewsApplication() {
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(UserRepository.class).to(UserRepositoryInterface.class).in(Singleton.class);
                this.bind(CategoryRepository.class).to(CategoryRepositoryInterface.class).in(Singleton.class);

                this.bindFactory(EntityManagerFactoryProvider.class).to(EntityManagerFactory.class).in(Singleton.class);
                this.bindFactory(EntityManagerProvider.class).proxy(true).proxyForSameScope(false).to(EntityManager.class).in(RequestScoped.class);
            }
        };
        register(binder);
        register(ConstraintViolationMapper.class);
        register(ForbiddenExceptionMapper.class);
        register(NotEmptyBodyFilter.class);
        register(AuthenticationFilter.class);
        register(RolesAllowedDynamicFeature.class);
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        packages("news.raf.backend.authentication");
        packages("news.raf.backend.resources");
    }
}