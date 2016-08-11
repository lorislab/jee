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
package org.lorislab.jel.jpa.service;

import org.lorislab.jel.jpa.resources.EntityServiceErrors;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import org.lorislab.jel.base.exception.ServiceException;
import org.lorislab.jel.jpa.model.Persistent;

/**
 * The abstract EAO service class using an entity <T>.
 *
 * @author Andrej Petras <andrej@lorislab.org>
 * @param <T> the entity class.
 */
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public abstract class AbstractEntityService<T extends Persistent> {

    /**
     * The entity manager.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * The entity class.
     */
    private final Class<T> entityClass;

    /**
     * The entity name.
     */
    private final String entityName;

    @SuppressWarnings("unchecked")
    public AbstractEntityService() {
        Type generSuperCls = getClass().getGenericSuperclass();
        if (generSuperCls instanceof Class) {
            generSuperCls = ((Class<?>) generSuperCls).getGenericSuperclass();
        }
        ParameterizedType parameterizedType = (ParameterizedType) generSuperCls;
        Type type = parameterizedType.getActualTypeArguments()[0];
        Class<T> tmp = null;
        if (type instanceof Class) {
            tmp = (Class<T>) type;
        } else if (type instanceof ParameterizedType) {
            tmp = (Class<T>) ((ParameterizedType) type).getRawType();
        }
        this.entityClass = tmp;

        // load the entity name
        Entity entity = entityClass.getAnnotation(Entity.class);
        if (entity != null && entity.name() != null && entity.name().length() > 0) {
            this.entityName = entity.name();
        } else {
            this.entityName = entityClass.getSimpleName();
        }
    }

    /**
     * Gets the entity manager.
     *
     * @return the entity manager.
     */
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Gets all entities.
     *
     * @return the list of all entities.
     * @throws org.lorislab.jel.base.exception.ServiceException
     */
    @SuppressWarnings("unchecked")
    protected final List<T> findAll() throws ServiceException {
        try {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(entityClass);
            cq.from(entityClass);
            TypedQuery<T> query = getEntityManager().createQuery(cq);
            return query.getResultList();
        } catch (Exception e) {
            throw new ServiceException(EntityServiceErrors.FIND_ALL_ENTITIES_FAILED, e);
        }
    }

    /**
     * Gets the entity by id.
     *
     * @param guid the entity GUID.
     * @return the entity corresponding to the GUID.
     * @throws org.lorislab.jel.base.exception.ServiceException
     */
    protected final T findByGuid(final Object guid) throws ServiceException {
        try {
            T entity = (T) getEntityManager().find(entityClass, guid);
            return entity;
        } catch (Exception e) {
            throw new ServiceException(EntityServiceErrors.FIND_ENTITY_BY_ID_FAILED, e);
        }
    }

    /**
     * Save the entity.
     *
     * @param entity the entity
     * @return the saved entity.
     * @throws org.lorislab.jel.base.exception.ServiceException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public T save(T entity) throws ServiceException {
        try {
            T loaded = null;
            if (entity.getGuid() != null) {
                loaded = this.findByGuid(entity.getGuid());
            }
            if (loaded != null) {
                return this.update(entity);
            }
            this.create(entity);

            return entity;
        } catch (Exception e) {
            throw new ServiceException(EntityServiceErrors.SAVE_ENTITY_FAILED, e);
        }
    }

    /**
     * Saves the list of entities.
     *
     * @param entities the list of entities.
     * @return the list of saved entities.
     * @throws org.lorislab.jel.base.exception.ServiceException
     *
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<T> saveAll(List<T> entities) throws ServiceException {
        try {
            List<T> result = new ArrayList<>();
            for (T entity : entities) {
                result.add(this.save(entity));
            }
            return result;
        } catch (Exception e) {
            throw new ServiceException(EntityServiceErrors.SAVE_ENTITIES_FAILED, e);
        }
    }

    /**
     *
     * @param entity
     * @return
     * @throws ServiceException
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public T update(T entity) throws ServiceException {
        try {
            T result = getEntityManager().merge(entity);
            flush();
            return result;
        } catch (Exception e) {
            throw new ServiceException(EntityServiceErrors.MERGE_ENTITY_FAILED, e);
        }
    }

    /**
     *
     * @param entity
     * @return
     * @throws ServiceException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public T create(T entity) throws ServiceException {
        try {
            this.getEntityManager().persist(entity);
            this.flush();
        } catch (Exception e) {
            throw new ServiceException(EntityServiceErrors.PERSIST_ENTITY_FAILED, e);
        }
        return entity;
    }

    /**
     * Performs persist followed by flush.
     *
     * @param entity
     * @see EntityManager#refresh(Object)
     */
    protected void refresh(T entity) {
        getEntityManager().refresh(entity);
    }

    /**
     * Deletes the entity.
     *
     * @param guid the GUID.
     * @return <code>true</code> if the entity was correctly deleted.
     * @throws org.lorislab.jel.base.exception.ServiceException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean deleteByGuid(Object guid) throws ServiceException {
        try {
            T loaded = this.findByGuid(guid);
            if (loaded != null) {
                getEntityManager().remove(loaded);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new ServiceException(EntityServiceErrors.DELETE_ENTITY_BY_GUID_FAILED, e);
        }
    }

    /**
     * Deletes the entity.
     *
     * @param entity the entity.
     * @return <code>true</code> if the entity was correctly deleted.
     * @throws org.lorislab.jel.base.exception.ServiceException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean delete(T entity) throws ServiceException {
        try {
            if (entity != null) {
                getEntityManager().remove(entity);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new ServiceException(EntityServiceErrors.DELETE_ENTITY_FAILED, e);
        }
    }

    /**
     * Performs delete operation on a list of entities. false is returned if one
     * object fails to be deleted.
     *
     * @param entities
     * @return
     * @throws org.lorislab.jel.base.exception.ServiceException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean deleteAll(List<T> entities) throws ServiceException {
        try {
            if (entities != null && !entities.isEmpty()) {
                for (T entity : entities) {
                    getEntityManager().remove(entity);
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new ServiceException(EntityServiceErrors.DELETE_ENTITIES_FAILED, e);
        }
    }

    /**
     *
     * @param namedQuery
     * @param parameters
     * @return
     */
    protected Query createNamedQuery(String namedQuery, Map<String, Object> parameters) {
        Query query = getEntityManager().createNamedQuery(namedQuery);
        if (parameters != null && parameters.size() > 0) {
            Set<String> keys = parameters.keySet();
            for (String key : keys) {
                Object o = parameters.get(key);
                query.setParameter(key, o);
            }
        }
        return query;
    }

    /**
     * Flush entity Manager
     */
    protected void flush() {
        getEntityManager().flush();
    }

    /**
     * Lock Entity in EntityManager.
     *
     * @param entity the entity
     * @param lockMode the lock mode
     */
    protected void lock(T entity, LockModeType lockMode) {
        getEntityManager().lock(entity, lockMode);
    }

    /**
     * Finds all entities in the corresponding interval.
     *
     * @param from the from index.
     * @param to the size index.
     * @return the corresponding list of the entities.
     * @throws ServiceException if the method fails.
     */
    public List<T> findAll(Integer from, Integer to) throws ServiceException {
        try {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(entityClass);
            cq.from(entityClass);
            TypedQuery<T> query = getEntityManager().createQuery(cq);
            if (from != null) {
                query.setFirstResult(from);
            }
            if (to != null) {
                if (from != null) {
                    query.setMaxResults(from + to);
                } else {
                    query.setMaxResults(to);
                }
            }
            return query.getResultList();
        } catch (Exception e) {
            throw new ServiceException(EntityServiceErrors.FIND_FROM_TO_ENTITIES_FAILED, e, from, to);
        }
    }
}
