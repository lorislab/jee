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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBContext;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(TraceableListener.class.getName());

//    @Inject
//    private Principal principal;

    /**
     * The JNDI of the EJB context.
     */
    private static final String JNDI_CONTEXT = "java:comp/EJBContext";


    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -7253672246009843767L;

    /**
     * The initial context.
     */
    private static InitialContext ctx;

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
     *
     * @param entity the entity.
     */
    @PrePersist
    public void prePersist(Traceable entity) {
        Principal principal = null;
        try {
            if (ctx != null) {
                EJBContext ejbCtx = (EJBContext) ctx.lookup(JNDI_CONTEXT);
                principal = ejbCtx.getCallerPrincipal();
            }
        } catch (Exception e) {
            LOGGER.log(Level.FINEST, "Error by get caller identity", e);
            // do nothing
        }
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
        Principal principal = null;
        try {
            if (ctx != null) {
                EJBContext ejbCtx = (EJBContext) ctx.lookup(JNDI_CONTEXT);
                principal = ejbCtx.getCallerPrincipal();
            }
        } catch (Exception e) {
            LOGGER.log(Level.FINEST, "Error by get caller identity", e);
            // do nothing
        }
        String user = InterceptorUtil.getPrincipalName(principal);
        entity.setModificationUser(user);
        entity.setModificationDate(new Date());
    }

}
