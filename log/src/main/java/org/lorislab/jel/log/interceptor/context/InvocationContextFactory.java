/*
 * Copyright 2014 Andrej_Petras.
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
package org.lorislab.jel.log.interceptor.context;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.interceptor.InvocationContext;

/**
 * The invocation context factory.
 *
 * @author Andrej_Petras
 */
public final class InvocationContextFactory {

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(InvocationContextFactory.class.getName());

    /**
     * The default constructor.
     */
    public InvocationContextFactory() {
        // empty constructor
    }

    /**
     * Creates the invocation context.
     *
     * @param target the target object.
     * @param method the method.
     * @param parameters the list of method parameters.
     * @return the invocation context or {@code null} if the target object is
     * {@code null}.
     * @throws Exception if the method fails.
     */
    public static InvocationContext createInvocationContext(Object target, String method, Object... parameters) throws Exception {
        InvocationContext result = null;
        if (target != null) {
            Method tmp = null;
            try {
                tmp = target.getClass().getDeclaredMethod(method);
            } catch (NoSuchMethodException nsme) {
                LOGGER.log(Level.SEVERE, "The class {0} does not contains the method {1}", new Object[]{target.getClass(), method});
                throw nsme;
            } catch (SecurityException ex) {
                LOGGER.log(Level.SEVERE, "Could not access the method {1} in the class {0}.", new Object[]{target.getClass(), method});
                throw ex;
            }
            result = new ObjectInvocationContext(target, tmp, (Object[]) parameters);
        }
        return result;
    }

}
