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
package com.ajkaandrej.lib.jpa.model.util;

import com.ajkaandrej.lib.jpa.model.Persistent;
import com.ajkaandrej.lib.jpa.model.TraceablePersistent;
import java.security.Principal;
import javax.ejb.EJBContext;

/**
 * The persistent utility.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
public final class PersistentUtil {

    /**
     * The default constructor.
     */
    private PersistentUtil() {
        // empty constructor
    }

    /**
     * Gets the object GUID.
     *
     * @param object the object.
     * @return the object GUID.
     */
    public static String getGuid(Persistent object) {
        String result = null;
        if (object != null) {
            result = object.getGuid();
        }
        return result;
    }

    /**
     * Updates the user information in the model.
     *
     * @param entity the entity.
     * @param context the EJB context.
     */
    public static void updateUser(TraceablePersistent entity, EJBContext context) {
        if (entity != null && context != null) {

            Principal principal = null;

            try {
                principal = context.getCallerPrincipal();
            } catch (Exception e) {
                // context throws an NPE if security context is not assigned
                // do nothing
            }

            if (principal != null) {
                updateUser(entity, principal.getName());
            }
        }
    }

    /**
     * Updates the user information in the model.
     *
     * @param entity the entity.
     * @param user the user name.
     */
    public static void updateUser(TraceablePersistent entity, String user) {
        if (entity != null) {
            entity.setModificationUser(user);
            if (entity.isNew()) {
                entity.setCreationUser(user);
            }
        }
    }
}
