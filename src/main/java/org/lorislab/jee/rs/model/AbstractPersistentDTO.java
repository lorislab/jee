/*
 * Copyright 2018 andrej.
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
package org.lorislab.jee.rs.model;

import java.io.Serializable;

/**
 * The abstract persistent DTO.
 * 
 * @author andrej
 * @param <T> the DTO key
 */
public abstract class AbstractPersistentDTO<T> implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -1655784042211703363L;
    
    /**
     * The persisted flag.
     */
    private boolean persisted;

    /**
     * The optimistic lock version
     */
    private Integer version;

    public void setPersisted(boolean persisted) {
        this.persisted = persisted;
    }

    public boolean isPersisted() {
        return persisted;
    }

    public abstract void setGuid(T guid);

    public abstract  T getGuid();

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getVersion() {
        return version;
    }
}
