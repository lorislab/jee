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
package org.lorislab.jel.jsf.view.controller.action;

import org.lorislab.jel.log.interceptor.ObjectInvocationContext;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lorislab.jel.jsf.api.interceptor.FacesInterceptor;
import org.lorislab.jel.log.interceptor.AbstractInterceptor;

/**
 * The action interceptor.
 *
 * @author Andrej_Petras
 */
public class ActionInterceptor {

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(ActionInterceptor.class.getName());

    /**
     * The interceptor instance.
     */
    private static final AbstractInterceptor INSTANCE = new FacesInterceptor();

    /**
     * Executes the action throw the interceptor.
     *
     * @param action the action instance.
     * @return the navigation path.
     */
    public static Object doExecution(final AbstractAction action) {
        Object result = null;
        
        ObjectInvocationContext ic = new ObjectInvocationContext(action, "doExecute");
        
        try {
            result = INSTANCE.methodExecution(ic);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error execute the action!", ex);
        }
        return result;
    }
}
