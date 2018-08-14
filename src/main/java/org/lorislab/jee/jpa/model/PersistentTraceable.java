/*
 * Copyright 2012 Andrej Petras <andrej@ajka-andrej.com>.
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

import java.util.Date;
import javax.persistence.*;
import org.lorislab.jee.jpa.listener.TraceableListener;

/**
 * Traceable persistent entity implementation.
 *
 * The implementation class for Persistent interface.
 *
 * @author Andrej Petras
 */
@MappedSuperclass
@EntityListeners(TraceableListener.class)
public class PersistentTraceable extends Persistent implements Traceable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = 1942935094847270053L;
    
    /**
     * The creation date.
     */
    @Column(name = "C_CREATIONDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    /**
     * The creation user.
     */
    @Column(name = "C_CREATIONUSER")
    private String creationUser;
    /**
     * The modification user.
     */
    @Column(name = "C_MODIFICATIONUSER")
    private String modificationUser;
    /**
     * The modification date.
     */
    @Column(name = "C_MODIFICATIONDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificationDate;

    /**
     * Gets the creation date.
     *
     * @return the creation date.
     */
    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date.
     *
     * @param creationDate the creation date.
     */
    @Override
    public void setCreationDate(final Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Gets the creation user.
     *
     * @return the creation user.
     */
    @Override
    public String getCreationUser() {
        return creationUser;
    }

    /**
     * Sets the creation user.
     *
     * @param creationUser the creation user.
     */
    @Override
    public void setCreationUser(final String creationUser) {
        this.creationUser = creationUser;
    }

    /**
     * Gets the modification date.
     *
     * @return the modification date.
     */
    @Override
    public Date getModificationDate() {
        return modificationDate;
    }

    /**
     * Sets the modification date.
     *
     * @param modificationDate the modification date.
     */
    @Override
    public void setModificationDate(final Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    /**
     * Gets the modification user.
     *
     * @return the modification user.
     */
    @Override
    public String getModificationUser() {
        return modificationUser;
    }

    /**
     * Sets the modification user.
     *
     * @param modificationUser the modification user.
     */
    @Override
    public void setModificationUser(final String modificationUser) {
        this.modificationUser = modificationUser;
    }
}
