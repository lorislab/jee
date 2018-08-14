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
package org.lorislab.jee.jpa.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * String GUID Persistent entity implementation.The implementation class for Persistent interface.
 *
 *
 * @author Andrej Petras
 * @param <T> the key type.
 */
@MappedSuperclass
public abstract class AbstractPersistent<T> implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -3728883740616373890L;

    /**
     * The persisted flag.
     */
    @Transient
    protected boolean persisted;

    /**
     * The version attribute.
     */
    @Version
    @Column(name = "C_OPLOCK")
    private Integer version;

    /**
     * The entity life-cycle method.
     */
    @PostPersist
    @PostLoad
    @PostUpdate
    public void checkPersistentState() {
        this.persisted = true;
    }
  
    /**
     * Gets the entity version.
     *
     * @return the entity version.
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * Sets the version of the entity.
     *
     * @param version the version.
     */
    public void setVersion(final Integer version) {
        this.version = version;
    }

    /**
     * Gets the GUID of entity.
     *
     * @return the GUID of entity.
     */
    public abstract T getGuid();

    /**
     * Sets the GUID of entity.
     *
     * @param guid the GUID of this entity.
     */
    public abstract void setGuid(final T guid);

    /**
     * Gets the {@code true} if the entity is persisted.
     *
     * @return {@code true} if the entity is persisted.
     */
    public boolean isPersisted() {
        return persisted;
    }

    /**
     * Sets the persisted flag.
     *
     * @param persisted the persisted flag.
     */
    public void setPersisted(boolean persisted) {
        this.persisted = persisted;
    }
    
}
