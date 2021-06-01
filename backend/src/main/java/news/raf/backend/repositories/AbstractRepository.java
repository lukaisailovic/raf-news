package news.raf.backend.repositories;

import news.raf.backend.entities.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;

abstract class AbstractRepository<T> {

    @Inject
    EntityManager entityManager;

    private Class<T> typeOfT;

    @SuppressWarnings("unchecked")
    public String getType() {
        this.typeOfT = (Class<T>)
                ((ParameterizedType)getClass()
                        .getGenericSuperclass())
                        .getActualTypeArguments()[0];
        return typeOfT.getName();
    }

    public T find(String id) {
        entityManager.getTransaction().begin();
        T result = entityManager.find(typeOfT,id);
        entityManager.getTransaction().commit();
        return result;
    }

    public T findBy(String parameter, String value) {
        try {
            TypedQuery<T> query = entityManager.createQuery("Select e FROM "+getType()+" e WHERE e."+parameter+" = :value",typeOfT);
            query.setParameter("value",value);
            return query.getSingleResult();
        } catch (NoResultException exception){
            return null;
        }
    }

    public void save(T entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }


}
