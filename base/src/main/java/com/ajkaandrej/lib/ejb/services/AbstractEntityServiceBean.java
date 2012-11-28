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
package com.ajkaandrej.lib.ejb.services;

import com.ajkaandrej.lib.jpa.eao.AbstractEAO;
import com.ajkaandrej.lib.jpa.model.Persistent;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * The abstract EAO session bean class using an entity <T>.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 * @param <T> the entity class.
 * @param <E> the abstract EAO service.
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
     * Initialized the EAO class.
     */
    @PostConstruct
    public void init() {
        baseEAO = new AbstractEAO<>(this.getClass());
    }

    /**
     * Gets all entities.
     *
     * @see AbstractServiceEAO#getAll()
     */
    public final List<T> getAll() {
        return getBaseEAO().getAll();
    }

    /**
     * Gets the entity by id.
     *
     * @see AbstractServiceEAO#findById(java.lang.Object)
     */
    public final T getById(final Object id) {
        return getBaseEAO().findById(id);
    }

    /**
     * Save the entity.
     *
     * @see AbstractServiceEAO#save(com.ajkaandrej.lib.jpa.model.Persistent)
     */
    public final T save(final T entity) {
        return getBaseEAO().save(entity);
    }

    /**
     * Saves the list of entities.
     *
     * @see AbstractServiceEAO#saveAll(java.util.List)
     */
    public final List<T> saveAll(final List<T> entities) {
        return getBaseEAO().saveAll(entities);
    }

    /**
     * Deletes the entity.
     *
     * @see AbstractServiceEAO#delete(com.ajkaandrej.lib.jpa.model.Persistent)
     */
    public final boolean delete(final T enitity) {
        return getBaseEAO().delete(enitity);
    }
}
