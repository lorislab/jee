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
package org.lorislab.jel.jpa.listener;

import org.lorislab.jel.jpa.model.TraceablePersistent;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
public class TraceableListener implements Serializable {

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(TraceableListener.class.getName());
    /**
     * The JNDI of the EJB context.
     */
    private static final String JNDI_CONTEXT = "java:comp/EJBContext";

    /**
     * The default user.
     */
    private static final String DEFAULT_USER = "anonymous";
    
    /**
     * The initial context.
     */
    private static InitialContext ctx;
    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -7253672246009843767L;

    /**
     * The default constructor.
     */
    public TraceableListener() {
        try {
            if (ctx == null) {
                ctx = new InitialContext();
            }
        } catch (NamingException e) {
            LOGGER.log(Level.SEVERE, "Error by creating the initial contenxt");
        }
    }

    /**
     * Marks the entity as created.
     */
    @PrePersist
    public void prePersist(TraceablePersistent entity) {
        Date date = new Date();
        String user = getCallerIdentity();
        entity.setCreationUser(user);
        entity.setCreationDate(date);
        entity.setModificationUser(user);
        entity.setModificationDate(date);
    }

    /**
     * Marks the entity as changed.
     */
    @PreUpdate
    public void preUpdate(TraceablePersistent entity) {
        String user = getCallerIdentity();
        entity.setModificationUser(user);
        entity.setModificationDate(new Date());
    }

    /**
     * Gets the caller identity.
     *
     * @return the caller identity.
     */
    private static String getCallerIdentity() {
        String result = DEFAULT_USER;
        try {
            if (ctx != null) {
                EJBContext ejbCtx = (EJBContext) ctx.lookup(JNDI_CONTEXT);
                result = ejbCtx.getCallerPrincipal().getName();
            }
        } catch (Exception e) {
            LOGGER.log(Level.FINEST, "Error by get caller identity", e);
            // do nothing
        }
        return result;
    }
}
