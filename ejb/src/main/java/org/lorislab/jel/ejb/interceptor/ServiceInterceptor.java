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
package org.lorislab.jel.ejb.interceptor;

import org.lorislab.jel.log.interceptor.AbstractInterceptor;
import java.lang.reflect.Method;
import java.security.Principal;
import javax.annotation.Resource;
import javax.ejb.EJBContext;

/**
 * The service interceptor.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
public class ServiceInterceptor extends AbstractInterceptor {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = 8762320723239369806L;
    /**
     * The EJB context.
     */
    @Resource
    private EJBContext context;

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getUser() {
        if (context != null) {
            Principal principal = context.getCallerPrincipal();
            if (principal != null) {
                return principal.getName();
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getClassName(Class clazz) {
        return clazz.getName();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isContextLogger(Method method) {
        return true;
    }
}