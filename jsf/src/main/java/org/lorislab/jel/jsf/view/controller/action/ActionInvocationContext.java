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

import java.lang.reflect.Method;
import java.util.Map;
import javax.interceptor.InvocationContext;

/**
 *
 * @author Andrej_Petras
 */
public class ActionInvocationContext implements InvocationContext {
  
    private final AbstractAction action;

    private final Method method;

    public ActionInvocationContext(AbstractAction action) {
        this.action = action;
        Method tmp = null;
        try {
            tmp = action.getClass().getDeclaredMethod("execute");
        } catch (Exception ex) {
            // do nothing
        }
        method = tmp;
    }

    @Override
    public Object getTarget() {
        return action;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getParameters() throws IllegalStateException {
        return null;
    }

    @Override
    public void setParameters(Object[] os) throws IllegalStateException, IllegalArgumentException {

    }

    @Override
    public Map<String, Object> getContextData() {
        return null;
    }

    @Override
    public Object getTimer() {
        return null;
    }

    @Override
    public Object proceed() throws Exception {
        return action.doExecute();
    }

}
