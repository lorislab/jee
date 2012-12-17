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
package com.ajkaandrej.lib.jpa.model;

import com.ajkaandrej.lib.jpa.listeners.TraceableListener;
import java.util.Date;
import javax.persistence.*;

/**
 * Traceable persistent entity implementation.
 *
 * The implementation class for Persistent interface.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
@MappedSuperclass
@EntityListeners(TraceableListener.class)
public class TraceablePersistent extends Persistent {

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
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creation date.
     *
     * @param creationDate the creation date.
     */
    public void setCreationDate(final Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Gets the creation user.
     *
     * @return the creation user.
     */
    public String getCreationUser() {
        return creationUser;
    }

    /**
     * Sets the creation user.
     *
     * @param creationUser the creation user.
     */
    public void setCreationUser(final String creationUser) {
        this.creationUser = creationUser;
    }

    /**
     * Gets the modification date.
     *
     * @return the modification date.
     */
    public Date getModificationDate() {
        return modificationDate;
    }

    /**
     * Sets the modification date.
     *
     * @param modificationDate the modification date.
     */
    public void setModificationDate(final Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    /**
     * Gets the modification user.
     *
     * @return the modification user.
     */
    public String getModificationUser() {
        return modificationUser;
    }

    /**
     * Sets the modification user.
     *
     * @param modificationUser the modification user.
     */
    public void setModificationUser(final String modificationUser) {
        this.modificationUser = modificationUser;
    }
}
