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
package org.lorislab.jel.ejb.services;

import org.lorislab.jel.jpa.eao.AbstractEAO;
import org.lorislab.jel.jpa.model.Persistent;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * The abstract EAO session bean class using an entity <T>.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 * @param <T> the entity class.
 */
public abstract class AbstractEntityServiceBean<T extends Persistent> extends AbstractServiceBean {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = 1980534470610958672L;
    /**
     * The persistence context.
     */
    @PersistenceContext
    private EntityManager em;
    /**
     * The base EAO class.
     */
    protected AbstractEAO<T> baseEAO;

    /**
     * Gets the base EAO class for the entity.
     *
     * @return the base EAO class for the entity.
     */
    protected final AbstractEAO<T> getBaseEAO() {
        if (baseEAO != null && baseEAO.getEntityManager() == null) {
            baseEAO.setEntityManager(em);
        }
        return baseEAO;
    }

    /**
     * Initialised the EAO class.
     */
    @PostConstruct
    protected void init() {
        baseEAO = new AbstractEAO<>(this.getClass());
    }

    /**
     * Gets all entities.
     *
     * @return the list of all entities.
     *
     * @see AbstractServiceEAO#getAll()
     */
    protected final List<T> getAll() {
        return getBaseEAO().getAll();
    }

    /**
     * Gets the entity by id.
     *
     * @param id the entity id.
     * @return the entity corresponding to the id.
     * @see AbstractServiceEAO#findById(java.lang.Object)
     */
    protected final T getById(final Object id) {
        return getBaseEAO().findById(id);
    }

    /**
     * Save the entity.
     *
     * @param entity the entity
     * @return the saved entity.
     *
     * @see AbstractServiceEAO#save(com.ajkaandrej.lib.jpa.model.Persistent)
     */
    protected final T save(final T entity) {
        return getBaseEAO().save(entity);
    }

    /**
     * Saves the list of entities.
     *
     * @param entities the list of entities.
     * @return the list of saved entities.
     * @see AbstractServiceEAO#saveAll(java.util.List)
     */
    protected final List<T> saveAll(final List<T> entities) {
        return getBaseEAO().saveAll(entities);
    }

    /**
     * Deletes the entity.
     *
     * @param enitity the entity.
     * @return <code>true</code> if the entity was correctly deleted.
     * @see AbstractServiceEAO#delete(org.lorislab.jpa.model.Persistent)
     */
    protected final boolean delete(final T enitity) {
        return getBaseEAO().delete(enitity);
    }

    /**
     * Deletes the entity.
     *
     * @param id the ID.
     * @return <code>true</code> if the entity was correctly deleted.
     * @see AbstractServiceEAO#delete(java.lang.String)
     */
    protected final boolean delete(final Object id) {
        return getBaseEAO().delete(id);
    }
}
