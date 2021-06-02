package news.raf.backend.repositories;

import news.raf.backend.repositories.interfaces.AbstractRepositoryInterface;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.util.List;

abstract class AbstractRepository<T> implements AbstractRepositoryInterface<T> {


    public static final int PAGE_SIZE = 10;

    @Inject
    EntityManager entityManager;

    private Class<T> typeOfT;

    public List<T> all(int page) {
        TypedQuery<T> query = entityManager.createQuery("select e FROM "+getType()+" e",typeOfT);
        query.setFirstResult((page-1)*PAGE_SIZE);
        query.setMaxResults(PAGE_SIZE);
        return query.getResultList();
    }

    public long count(){
        try {
            Query query = entityManager.createQuery("select count(e.id) FROM "+getType()+" e");
            return (long) query.getSingleResult();
        }catch (NoResultException e){
            return 0;
        }
    }

    @SuppressWarnings("unchecked")
    public String getType() {
        this.typeOfT = (Class<T>)
                ((ParameterizedType)getClass()
                        .getGenericSuperclass())
                        .getActualTypeArguments()[0];
        return typeOfT.getName();
    }

    public T find(String id) {
        try {
            TypedQuery<T> query = entityManager.createQuery("Select e FROM "+getType()+" e WHERE e.id = :value",typeOfT);
            query.setParameter("value",id);
            T result = query.getSingleResult();
            entityManager.refresh(result);
            return result;
        } catch (NoResultException exception){
            return null;
        }
    }

    public T findBy(String parameter, String value) {
        try {
            TypedQuery<T> query = entityManager.createQuery("Select e FROM "+getType()+" e WHERE e."+parameter+" = :value",typeOfT);
            query.setParameter("value",value);
            T result = query.getSingleResult();
            entityManager.refresh(result);
            return result;
        } catch (NoResultException exception){
            return null;
        }
    }
    public boolean existsBy(String parameter, String value){
        try {
            Query query = entityManager.createQuery("Select count(e.id) FROM "+getType()+" e WHERE e."+parameter+" = :value");
            query.setParameter("value",value);
            return (long)query.getSingleResult() > 0;
        } catch (NoResultException exception){
            return false;
        }
    }

    public void save(T entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    public int getPageSize() {
        return PAGE_SIZE;
    }
}
