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
package org.lorislab.jee.jpa.service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Entity;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.lorislab.jee.exception.ServiceException;
import org.lorislab.jee.jpa.model.AbstractPersistent;
import org.lorislab.jee.jpa.model.AbstractPersistent_;
import org.lorislab.jee.jpa.model.BusinessTraceable;

/**
 * The abstract EAO service class using an entity type.
 *
 * @author Andrej Petras
 * @param <T> the entity class.
 * @param <K> the key type.
 */
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public abstract class AbstractEntityService<T extends AbstractPersistent<K>, K> {

    /**
     * The property hint is javax.persistence.fetchgraph.
     *
     * This hint will treat all the specified attributes in the Entity Graph as
     * FetchType.EAGER. Attributes that are not specified are treated as
     * FetchType.LAZY.
     */
    protected final static String HINT_LOAD_GRAPH = "javax.persistence.loadgraph";

    /**
     * The property hint is javax.persistence.loadgraph.
     *
     * This will treat all the specified attributes in the Entity Graph as
     * FetchType.EAGER. Attributes that are not specified are treated to their
     * specified or default FetchType.
     */
    protected final static String HINT_FETCH_GRAPH = "javax.persistence.fetchgraph";

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

    /**
     * The load entity graph.
     */
    private EntityGraph<? super T> loadEntityGraph;

    /**
     * The default constructor.
     */
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
     * Initialize the entity service bean.
     */
    @PostConstruct
    public void init() {
        EntityGraph<? super T> graph = null;
        if (entityClass != null) {
            String loadName = entityName + ".load";

            List<EntityGraph<? super T>> graphs = getEntityManager().getEntityGraphs(entityClass);
            if (graphs != null) {
                for (int i = 0; i < graphs.size() && graph == null; i++) {
                    if (graphs.get(i).getName().equals(loadName)) {
                        graph = graphs.get(i);
                    }
                }
            }
        }
        loadEntityGraph = graph;
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
     * @throws org.lorislab.jee.exception.ServiceException
     */
    @SuppressWarnings("unchecked")
    protected List<T> findAll() throws ServiceException {
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
     * @throws org.lorislab.jee.exception.ServiceException
     */
    public T findByGuid(final K guid) throws ServiceException {
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
     * @throws org.lorislab.jee.exception.ServiceException
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
     * @throws org.lorislab.jee.exception.ServiceException
     *
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<T> saveAll(List<T> entities) throws ServiceException {
        try {
            List<T> result = new ArrayList<>();
            if (entities != null && !entities.isEmpty()) {
                for (int i = 0; i < entities.size(); i++) {
                    result.add(this.save(entities.get(i)));
                }
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
     * @param entities
     * @return
     * @throws ServiceException
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<T> update(List<T> entities) throws ServiceException {
        List<T> result = null;
        if (entities != null) {
            result = new ArrayList<>(entities.size());
            try {
                for (int i = 0; i < entities.size(); i++) {
                    result.add(getEntityManager().merge(entities.get(i)));
                }
                flush();
                return result;
            } catch (Exception e) {
                throw new ServiceException(EntityServiceErrors.MERGE_ENTITY_FAILED, e);
            }
        }
        return result;
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
     *
     * @param entities
     * @return
     * @throws ServiceException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<T> create(List<T> entities) throws ServiceException {
        if (entities != null) {
            try {
                for (int i = 0; i < entities.size(); i++) {
                    this.getEntityManager().persist(entities.get(i));
                }
                this.flush();
            } catch (Exception e) {
                throw new ServiceException(EntityServiceErrors.PERSIST_ENTITY_FAILED, e);
            }
        }
        return entities;
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
     * @throws org.lorislab.jee.exception.ServiceException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean deleteByGuid(K guid) throws ServiceException {
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
     * @throws org.lorislab.jee.exception.ServiceException
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
     * @return the delete flag.
     * @throws org.lorislab.jee.exception.ServiceException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean deleteAll(List<T> entities) throws ServiceException {
        try {
            if (entities != null && !entities.isEmpty()) {
                for (int i = 0; i < entities.size(); i++) {
                    getEntityManager().remove(entities.get(i));
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
            if (keys != null) {
                keys.forEach((key) -> {
                    Object o = parameters.get(key);
                    query.setParameter(key, o);
                });
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

    /**
     * Finds the list of object by GUIDs.
     *
     * @param guids the set of GUIDs.
     * @return the corresponding entity.
     * @throws org.lorislab.jee.exception.ServiceException
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Map<K, T> findByGuidsMap(List<K> guids) throws ServiceException {
        Map<K, T> result = null;
        List<T> tmp = findByGuids(guids);
        if (tmp != null && !tmp.isEmpty()) {
            result = new HashMap<>();
            for (int i = 0; i < tmp.size(); i++) {
                T item = tmp.get(i);
                result.put(item.getGuid(), item);
            }
        }
        return result;
    }

    /**
     * Finds the list of object by GUIDs.
     *
     * @param guids the set of GUIDs.
     * @return the corresponding list of entities.
     * @throws org.lorislab.jee.exception.ServiceException
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<T> findByGuids(List<K> guids) throws ServiceException {
        List<T> result = null;
        if (guids != null && !guids.isEmpty()) {
            try {
                CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
                CriteriaQuery<T> cq = cb.createQuery(entityClass);
                Root<T> root = cq.from(entityClass);
                cq.where(root.get(AbstractPersistent_.guid).in(guids));
                TypedQuery<T> query = getEntityManager().createQuery(cq);
                result = query.getResultList();
            } catch (Exception e) {
                throw new ServiceException(EntityServiceErrors.FAILED_TO_GET_ENTITY_BY_GUIDS, e, entityName);
            }
        }
        return result;
    }

    /**
     * Performs delete operation on a list of entities. false is returned if one
     * object fails to be deleted.
     *
     * @param entities the list of entities.
     * @return {@code true} if all entities removed.
     * @throws org.lorislab.jee.exception.ServiceException
     *
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int delete(List<T> entities) throws ServiceException {
        int result = 0;
        try {
            if (entities != null && !entities.isEmpty()) {
                for (int i = 0; i < entities.size(); i++) {
                    if (this.delete(entities.get(i))) {
                        result = result + 1;
                    }
                }
            }
        } catch (Exception e) {
            throw new ServiceException(EntityServiceErrors.FAILED_TO_DELETE_ENTITY, e, entityName);
        }
        return result;
    }

    /**
     * Performs delete operation on a list of entities. false is returned if one
     * object fails to be deleted.
     *
     * @param guids the list of entities.
     * @return {@code true} if all entities removed.
     * @throws org.lorislab.jee.exception.ServiceException
     *
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int deleteByGuids(List<K> guids) throws ServiceException {
        int result = 0;
        try {
            if (guids != null && !guids.isEmpty()) {
                for (int i = 0; i < guids.size(); i++) {
                    if (this.deleteByGuid(guids.get(i))) {
                        result = result + 1;
                    }
                }
            }
        } catch (Exception e) {
            throw new ServiceException(EntityServiceErrors.FAILED_TO_DELETE_ENTITY, e, entityName);
        }
        return result;
    }

    /**
     * Performs delete operation on a list of entities. false is returned if one
     * object fails to be deleted.
     *
     * @return {@code true} if all entities removed.
     * @throws org.lorislab.jee.exception.ServiceException
     *
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int deleteAll() throws ServiceException {
        int result = 0;
        try {
            List<T> tmp = findAll();
            if (tmp != null && !tmp.isEmpty()) {
                result = delete(tmp);
            }
        } catch (Exception e) {
            throw new ServiceException(EntityServiceErrors.FAILED_TO_DELETE_ALL, e, entityName);
        }
        return result;
    }

    /**
     * Finds all entities in the corresponding interval.
     *
     * @param from the from index.
     * @param count the count index.
     * @return the corresponding list of the entities.
     * @throws ServiceException if the method fails.
     */
    public List<T> find(Integer from, Integer count) throws ServiceException {
        try {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(entityClass);
            cq.from(entityClass);
            TypedQuery<T> query = getEntityManager().createQuery(cq);
            if (from != null) {
                query.setFirstResult(from);
            }
            if (count != null) {
                if (from != null) {
                    query.setMaxResults(from + count);
                } else {
                    query.setMaxResults(count);
                }
            }
            return query.getResultList();
        } catch (Exception e) {
            throw new ServiceException(EntityServiceErrors.FAILED_TO_GET_ALL_ENTITIES, e, entityName, from, count);
        }
    }

    /**
     * Removes all entities. Check on existence is made.
     *
     * @return the number of deleted entities.
     * @throws org.lorislab.jee.exception.ServiceException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int deleteQueryAll() throws ServiceException {
        try {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaDelete<T> cq = cb.createCriteriaDelete(entityClass);
            cq.from(entityClass);
            return getEntityManager().createQuery(cq).executeUpdate();
        } catch (Exception e) {
            throw new ServiceException(EntityServiceErrors.FAILED_TO_DELETE_ALL_QUERY, e, entityName);
        }
    }

    /**
     * Removes an entity by GUID. Check on existence is made.
     *
     * @param guid the GUID of the entity
     * @return true if removed.
     * @throws org.lorislab.jee.exception.ServiceException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean deleteQueryByGuid(K guid) throws ServiceException {
        boolean result = false;
        if (guid != null) {
            try {
                CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
                CriteriaDelete<T> cq = cb.createCriteriaDelete(entityClass);
                Root<T> root = cq.from(entityClass);
                cq.where(cb.equal(root.get(AbstractPersistent_.guid), guid));
                int count = getEntityManager().createQuery(cq).executeUpdate();
                result = count == 1;
            } catch (Exception e) {
                throw new ServiceException(EntityServiceErrors.FAILED_TO_DELETE_BY_GUID_QUERY, e, entityName);
            }
        }
        return result;
    }

    /**
     * Removes entities by GUIDs. Check on existence is made.
     *
     * @param guids the set of GUIDs.
     * @return the number of deleted entities.
     * @throws org.lorislab.jee.exception.ServiceException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int deleteQueryByGuids(List<K> guids) throws ServiceException {
        try {
            if (guids != null && !guids.isEmpty()) {
                CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
                CriteriaDelete<T> cq = cb.createCriteriaDelete(entityClass);
                Root<T> root = cq.from(entityClass);
                cq.where(root.get(AbstractPersistent_.guid).in(guids));
                return getEntityManager().createQuery(cq).executeUpdate();
            }
        } catch (Exception e) {
            throw new ServiceException(EntityServiceErrors.FAILED_TO_DELETE_ALL_BY_GUIDS_QUERY, e, entityName);
        }
        return 0;
    }

    /**
     * Loads all entities.
     *
     * @return the list loaded entities.
     * @throws ServiceException if the method fails.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<T> loadAll() throws ServiceException {
        return loadAll(loadEntityGraph);
    }

    /**
     * Loads all entities.
     *
     * @param entityGraphName the entity graph name.
     *
     * @return the list loaded entities.
     * @throws ServiceException if the method fails.
     */
    protected List<T> loadAll(String entityGraphName) throws ServiceException {
        if (entityGraphName != null && !entityGraphName.isEmpty()) {
            EntityGraph<?> entityGraph = getEntityManager().getEntityGraph(entityGraphName);
            return loadAll(entityGraph);
        }
        return null;
    }

    /**
     * Loads all entities.
     *
     * @param entityGraph the entity graph.
     *
     * @return the list loaded entities.
     * @throws ServiceException if the method fails.
     */
    protected List<T> loadAll(EntityGraph<?> entityGraph) throws ServiceException {
        try {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(entityClass);
            cq.from(entityClass);
            cq.distinct(true);
            TypedQuery<T> query = getEntityManager().createQuery(cq);
            if (entityGraph != null) {
                query.setHint(HINT_LOAD_GRAPH, entityGraph);
            }
            return query.getResultList();
        } catch (Exception e) {
            throw new ServiceException(EntityServiceErrors.FAILED_TO_LOAD_ALL_ENTITIES, e, entityName, entityGraph == null ? null : entityGraph.getName());
        }
    }

    /**
     * Loads all entities.
     *
     * @param guids the set of GUIDs.
     *
     * @return the list loaded entities.
     * @throws ServiceException if the method fails.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Map<K, T> loadByGuidsMap(List<K> guids) throws ServiceException {
        return loadByGuidsMap(guids, loadEntityGraph);
    }

    /**
     * Loads all entities by the set of GUIDs.
     *
     * @param guids the set of GUIDs.
     * @param entityGraphName the entity graph name.
     *
     * @return the map of loaded entities.
     * @throws ServiceException if the method fails.
     */
    protected Map<K, T> loadByGuidsMap(List<K> guids, String entityGraphName) throws ServiceException {
        if (guids != null && entityGraphName != null && !entityGraphName.isEmpty()) {
            EntityGraph<?> entityGraph = getEntityManager().getEntityGraph(entityGraphName);
            return loadByGuidsMap(guids, entityGraph);
        }
        return null;
    }

    /**
     * Loads all entities by the set of GUIDs.
     *
     * @param guids the set of GUIDs.
     * @param entityGraph the entity graph.
     *
     * @return the map of loaded entities.
     * @throws ServiceException if the method fails.
     */
    protected Map<K, T> loadByGuidsMap(List<K> guids, EntityGraph<?> entityGraph) throws ServiceException {
        Map<K, T> result = null;
        List<T> tmp = loadByGuids(guids, entityGraph);
        if (tmp != null && !tmp.isEmpty()) {
            result = new HashMap<>();
            for (int i = 0; i < tmp.size(); i++) {
                T item = tmp.get(i);
                result.put(item.getGuid(), item);
            }
        }
        return result;
    }

    /**
     * Loads all entities.
     *
     * @param guids the set of GUIDs.
     *
     * @return the list loaded entities.
     * @throws ServiceException if the method fails.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<T> loadByGuids(List<K> guids) throws ServiceException {
        return loadByGuids(guids, loadEntityGraph);
    }

    /**
     * Loads all entities.
     *
     * @param guids the set of GUIDs.
     * @param entityGraphName the entity graph name.
     *
     * @return the list loaded entities.
     * @throws ServiceException if the method fails.
     */
    protected List<T> loadByGuids(List<K> guids, String entityGraphName) throws ServiceException {
        if (guids != null && entityGraphName != null && !entityGraphName.isEmpty()) {
            EntityGraph<?> entityGraph = getEntityManager().getEntityGraph(entityGraphName);
            return loadByGuids(guids, entityGraph);
        }
        return null;
    }

    /**
     * Loads all entities.
     *
     * @param guids the set of GUIDs.
     * @param entityGraph the entity graph.
     *
     * @return the list loaded entities.
     * @throws ServiceException if the method fails.
     */
    protected List<T> loadByGuids(List<K> guids, EntityGraph<?> entityGraph) throws ServiceException {
        List<T> result = null;
        try {
            if (guids != null && !guids.isEmpty()) {
                CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
                CriteriaQuery<T> cq = cb.createQuery(entityClass);
                Root<T> root = cq.from(entityClass);
                cq.where(root.get(AbstractPersistent_.guid).in(guids));
                TypedQuery<T> query = getEntityManager().createQuery(cq);
                if (entityGraph != null) {
                    query.setHint(HINT_LOAD_GRAPH, entityGraph);
                }
                result = query.getResultList();
            }
        } catch (Exception e) {
            throw new ServiceException(EntityServiceErrors.FAILED_TO_LOAD_GUIDS_ENTITIES, e, entityName, entityGraph == null ? null : entityGraph.getName());
        }
        return result;
    }

    /**
     * Loads the entity by GUID.
     *
     * @param guid the GUID.
     * @return the entity.
     * @throws org.lorislab.jee.exception.ServiceException
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public T loadByGuid(K guid) throws ServiceException {
        return loadByGuid(guid, loadEntityGraph);
    }

    /**
     * Loads the entity by GUID and entity graph name.
     *
     * @param guid the GUID.
     * @param entityGraphName the entity graph name.
     * @return the entity.
     * @throws org.lorislab.jee.exception.ServiceException
     */
    protected T loadByGuid(K guid, String entityGraphName) throws ServiceException {
        if (guid != null && entityGraphName != null && !entityGraphName.isEmpty()) {
            EntityGraph<?> entityGraph = getEntityManager().getEntityGraph(entityGraphName);
            return loadByGuid(guid, entityGraph);
        }
        return null;
    }

    /**
     * Loads the entity by GUID and entity graph name.
     *
     * @param guid the GUID.
     * @param entityGraph the entity graph.
     * @return the entity.
     * @throws org.lorislab.jee.exception.ServiceException
     */
    protected T loadByGuid(K guid, EntityGraph<?> entityGraph) throws ServiceException {
        if (guid != null) {
            try {
                Map<String, Object> properties = new HashMap<>();
                if (entityGraph != null) {
                    properties.put(HINT_LOAD_GRAPH, entityGraph);
                }
                T entity = (T) getEntityManager().find(entityClass, guid, properties);
                return entity;
            } catch (Exception e) {
                throw new ServiceException(EntityServiceErrors.FAILED_TO_LOAD_ENTITY_BY_GUID, e, entityName, (Serializable) guid, entityGraph == null ? null : entityGraph.getName());
            }
        }
        return null;
    }

}
