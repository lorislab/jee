/*
 * Copyright 2011 Andrej Petras <andrej@ajka-andrej.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lorislab.jel.jpa.eao;

import org.lorislab.jel.jpa.model.Persistent;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.Map.Entry;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Interface for abstract class the EAO pattern.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 * @param <T> Entity class must extend the Persistent interface.
 */
public class AbstractEAO<T extends Persistent> {

    /**
     * JPA Entity manager.
     */
    protected EntityManager entityManager;
    /**
     * The persistent class.
     */
    private Class<T> persistentClass;
    /**
     * The name of the entity.
     */
    private String entityName;

    public AbstractEAO() {
        discoverEntityClass(this.getClass());
        discoverEntityName();
    }

    /**
     * The default constructor.
     *
     * @param serviceClass the service class.
     */
    public AbstractEAO(Class serviceClass) {
        discoverEntityClass(serviceClass);
        discoverEntityName();
    }

    /**
     * The default constructor.
     *
     * @param persistentClass the persistence class.
     * @param em entity manager.
     */
    public AbstractEAO(final Class<T> persistentClass, final EntityManager em) {
        this.persistentClass = persistentClass;
        discoverEntityName();
        setEntityManager(em);
    }

    /**
     * Determines the entity class from the class.
     *
     * @param clazz the class.
     */
    protected final void discoverEntityClass(Class clazz) {
        ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericSuperclass();
        Type type = parameterizedType.getActualTypeArguments()[0];
        if (type instanceof Class) {
            this.persistentClass = (Class<T>) type;
        } else if (type instanceof ParameterizedType) {
            this.persistentClass = (Class<T>) ((ParameterizedType) type).getRawType();
        }
    }

    /**
     * Determines the entity name from persistent class for the query.
     */
    protected final void discoverEntityName() {
        Entity entity = persistentClass.getAnnotation(Entity.class);
        if (entity != null && entity.name() != null && !entity.name().isEmpty()) {
            entityName = entity.name();
        } else {
            entityName = persistentClass.getSimpleName();
        }
    }

    /**
     * Gets the persistent entity class.
     *
     * @return the persistent entity class.
     */
    protected final Class<T> getPeristentClass() {
        return persistentClass;
    }

    /**
     * Gets the string name of the entity.
     *
     * @return the string name of the entity.
     */
    public final String getEntityName() {
        return entityName;
    }

    /**
     * Sets the entity manager.
     *
     * @param entityManager the entity manager.
     */
    public final void setEntityManager(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Gets the entity manager.
     *
     * @return the entity manager.
     */
    public final EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Gets the entity manager and check for
     * <code>null</code>.
     *
     * @return the entity manager.
     */
    private EntityManager getManager() {
        if (entityManager == null) {
            throw new IllegalStateException();
        }
        return entityManager;
    }

    /**
     * Remove the entity.
     *
     * @param entity witch should be removed.
     */
    public final void remove(final T entity) {
        getManager().remove(entity);
    }

    /**
     * Entity manager refresh.
     *
     * @param entity the entity to refresh.
     */
    public final void refresh(final T entity) {
        getManager().refresh(entity);
    }

    /**
     * Entity manager persist.
     *
     * @param entity the entity to persist.
     */
    public final void persist(final T entity) {
        getManager().persist(entity);
    }

    /**
     * Entity manager merge.
     *
     * @param entity the entity to merge.
     * @return the entity T.
     */
    public final T merge(final T entity) {
        return getManager().merge(entity);
    }

    /**
     * Entity manager lock.
     *
     * @param entity the entity for lock.
     * @param lockMode the mode of lock.
     */
    public final void lock(final Object entity, final LockModeType lockMode) {
        getManager().lock(entity, lockMode);
    }

    /**
     * Entity manager flush.
     */
    public final void flush() {
        getManager().flush();
    }

    /**
     * Creates the query from
     * <code>jpql</code>.
     *
     * @param ejbql the JPQL query.
     * @return the JPQL query
     */
    public final Query createQuery(final String ejbql) {
        return getManager().createQuery(ejbql);
    }

    /**
     * Find the entity by named query.
     *
     * @param namedQuery the JPQL statement.
     * @param parameters the parameters for the query.
     * @return the list of entities.
     */
    @SuppressWarnings("unchecked")
    public final List<T> findByNamedQuery(final String namedQuery, final Map<String, Object> parameters) {
        Query result = createNamedQuery(namedQuery, parameters);
        return (List<T>) result.getResultList();
    }

    /**
     * Find the
     * <code>clazz</code> entity by Id.
     *
     * @param id the id of entity.
     * @return the entity T.
     */
    public final T findById(final Object id) {
        return getManager().find(getPeristentClass(), id);
    }

    /**
     * Find the
     * <code>clazz</code> entity by Id.
     *
     * @param clazz the class of the entity.
     * @param id the id of entity.
     * @return the entity T.
     */
    public final T findById(final Class<T> clazz, final Object id) {
        return getManager().find(clazz, id);
    }

    /**
     * Find by named query.
     *
     * @param namedQuery the JPQL query.
     * @return list of entities.
     */
    public final List<T> findByNamedQuery(final String namedQuery) {
        return findByNamedQuery(namedQuery, null);
    }

    /**
     * Gets all entities.
     *
     * @return list of entities.
     */
    @SuppressWarnings("unchecked")
    public final List<T> getAll() {
        CriteriaQuery<T> cq = createCriteriaQuery();
        Root<T> root = cq.from(persistentClass);
        cq.select(root);
        TypedQuery<T> query = createTypedQuery(cq);
        return query.getResultList();
    }

    /**
     * Creates the named query with the parameters.
     *
     * @param namedQuery the name of the query.
     * @param parameters the parameters.
     * @return the java persistence query.
     */
    protected final Query createNamedQuery(final String namedQuery, final Map<String, Object> parameters) {
        final Query query = entityManager.createNamedQuery(namedQuery);
        if (parameters != null && !parameters.isEmpty()) {
            final Set<Entry<String, Object>> keys = parameters.entrySet();
            for (Entry<String, Object> entry : keys) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return query;
    }

    /**
     * Finds distinct by named query.
     *
     * @param namedQuery the name of the query.
     * @param parameters the parameters.
     * @return the result entity.
     */
    @SuppressWarnings("unchecked")
    public final T findSingleByNamedQuery(final String namedQuery, final Map<String, Object> parameters) {
        Query query = createNamedQuery(namedQuery, parameters);
        Object result = query.getSingleResult();
        if (result != null) {
            return (T) result;
        }
        return null;
    }

    /**
     * Finds distinct by named query.
     *
     * @param namedQuery the name of the query.
     * @return the result entity.
     */
    public final T findSingleByNamedQuery(final String namedQuery) {
        return findSingleByNamedQuery(namedQuery, null);
    }

    
    public final boolean delete(Object id) {
        if (id != null) {
            T tmp = findById(id);
            if (tmp != null) {
                remove(tmp);
                return true;
            }
        }
        return false;        
    }
    
    /**
     * Deletes the entity.
     *
     * @param entity the entity
     * @return returns <code>true</code> if entity was removed.
     */
    public final boolean delete(final T entity) {
        if (entity != null) {
            return delete(entity.getGuid());
        }
        return false;
    }

    /**
     * Deletes a list of entities.
     *
     * @param entities the list of entities.
     * @return returns false if one object fails to be deleted.
     */
    public final boolean deleteAll(final List<T> entities) {
        boolean result = true;
        for (T entity : entities) {
            result = delete(entity) && result;
        }
        return result;
    }

    /**
     * Save the entity.
     *
     * @param entity the entity.
     * @return the saved entity.
     */
    public final T save(final T entity) {
        T result;
        if (entity.isNew()) {
            persist(entity);
            result = findById(entity.getGuid());
        } else {
            result = merge(entity);
        }
        return result;
    }

    /**
     * Saves the list of entities.
     *
     * @param entities the list of entities.
     * @return the list old saved entities.
     */
    public final List<T> saveAll(final List<T> entities) {
        List<T> result = new ArrayList<>();
        for (T entity : entities) {
            result.add(save(entity));
        }
        return result;
    }

    /**
     * Gets the criteria builder.
     *
     * @return the criteria builder.
     */
    public final CriteriaBuilder getCriteriaBuilder() {
        return getManager().getCriteriaBuilder();
    }

    /**
     * Creates the criteria query.
     *
     * @return the criteria query.
     */
    public final CriteriaQuery<T> createCriteriaQuery() {
        return createCriteriaQuery(persistentClass);
    }

    /**
     * Creates the type query from the criteria.
     *
     * @param criteria the criteria.
     * @return the type query.
     */
    public final <E> TypedQuery<E> createQuery(CriteriaQuery<E> criteria) {
        return getManager().createQuery(criteria);
    }
    
    /**
     * Creates the type query from the criteria.
     *
     * @param criteria the criteria.
     * @return the type query.
     */
    public final TypedQuery<T> createTypedQuery(CriteriaQuery<T> criteria) {
        return getManager().createQuery(criteria);
    }

    /**
     * Creates the criteria query.
     *
     * @param <S> the criteria query
     * @param clazz the class.
     * @return the criteria query.
     */
    public final <S> CriteriaQuery<S> createCriteriaQuery(Class<S> clazz) {
        return getCriteriaBuilder().createQuery(clazz);
    }
}
