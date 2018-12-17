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
package org.lorislab.jee.jpa.listener;

import java.io.Serializable;
import java.security.Principal;
import java.util.Date;
import javax.inject.Inject;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import org.lorislab.jee.interceptor.InterceptorUtil;
import org.lorislab.jee.jpa.model.Traceable;

/**
 * The traceable entity listener.
 *
 * @author Andrej Petras
 */
public class TraceableListener implements Serializable {

    @Inject
    private Principal principal;

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -7253672246009843767L;

    /**
     * Marks the entity as created.
     *
     * @param entity the entity.
     */
    @PrePersist
    public void prePersist(Traceable entity) {
        Date date = new Date();
        String user = InterceptorUtil.getPrincipalName(principal);
        entity.setCreationUser(user);
        entity.setCreationDate(date);
        entity.setModificationUser(user);
        entity.setModificationDate(date);
    }

    /**
     * Marks the entity as changed.
     *
     * @param entity the entity.
     */
    @PreUpdate
    public void preUpdate(Traceable entity) {
        String user = InterceptorUtil.getPrincipalName(principal);
        entity.setModificationUser(user);
        entity.setModificationDate(new Date());
    }

}
