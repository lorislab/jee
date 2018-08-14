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

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * String GUID Persistent entity implementation.
 *
 * The implementation class for Persistent interface.
 *
 *  <pre>
 * {@code
 *  {@literal @}Table(name = "TABLE_NAME", indexes = {@literal @}Index(name = "TABLE_NAME_GUID_IDX", columnList = "C_GUID", unique = true))
 *  {@literal @}SequenceGenerator(name = "GEN_TABLE", sequenceName = "SEQ_NAME_ID", allocationSize = 1, initialValue = 1)
 * }
 * </pre>
 * 
 * @author Andrej Petras
 */
@MappedSuperclass
public class Business extends AbstractPersistent<Long> {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -6081185800586714162L;

    /**
     * The GUID of entity.
     */
    @Id
    @Column(name = "C_GUID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_GUID")
    private Long guid;

    /**
     * Gets the GUID of entity.
     *
     * @return the GUID of entity.
     */
    @Override
    public Long getGuid() {
        return guid;
    }

    /**
     * Sets the GUID of entity.
     *
     * @param guid the GUID of this entity.
     */
    @Override
    public void setGuid(final Long guid) {
        this.guid = guid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        int code = 0;
        if (guid != null) {
            result = guid.hashCode();
        }
        result = prime * result + code;
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Business other = (Business) obj;
        if (guid == null) {
            if (other.guid != null) {
                return false;
            }
        } else if (!guid.equals(other.guid)) {
            return false;
        }
        return true;
    }
}
