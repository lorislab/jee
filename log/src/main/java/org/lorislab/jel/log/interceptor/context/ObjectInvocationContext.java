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
import java.util.Map;
import javax.interceptor.InvocationContext;

/**
 * The object invocation context.
 *
 * @author Andrej Petras
 */
public class ObjectInvocationContext implements InvocationContext {

    /**
     * The target object.
     */
    private final Object target;

    /**
     * The method to execute.
     */
    private final Method method;

    /**
     * The method parameters.
     */
    private Object[] parameters;

    /**
     * The default constructor.
     *
     * @param target the target object.
     * @param method the method name.
     * @param parameters the method parameters.
     */
    protected ObjectInvocationContext(Object target, Method method, Object... parameters) {
        this.target = target;
        this.parameters = parameters;
        this.method = method;        
    }

    /**
     * Gets the target object.
     *
     * @return the target object.
     */
    @Override
    public Object getTarget() {
        return target;
    }

    /**
     * Gets the method.
     *
     * @return the method.
     */
    @Override
    public Method getMethod() {
        return method;
    }

    /**
     * Gets the method parameters.
     *
     * @return the method parameters.
     * @throws IllegalStateException if the method fails.
     */
    @Override
    public Object[] getParameters() throws IllegalStateException {
        return parameters;
    }

    /**
     * Sets the method parameters.
     *
     * @param parameters the method parameters.
     * @throws IllegalStateException if the method fails.
     * @throws IllegalArgumentException if the method fails.
     */
    @Override
    public void setParameters(Object[] parameters) throws IllegalStateException, IllegalArgumentException {
        this.parameters = parameters;
    }

    /**
     * Gets the timer. This method returns {@code null}.
     *
     * @return returns {@code null}.
     */
    @Override
    public Map<String, Object> getContextData() {
        return null;
    }

    /**
     * Gets the timer. This method returns {@code null}.
     *
     * @return returns {@code null}.
     */
    @Override
    public Object getTimer() {
        return null;
    }

    /**
     * Proceeds the method execution.
     *
     * @return the return value from method execution.
     * @throws Exception if the method fails.
     */
    @Override
    public Object proceed() throws Exception {
        Object result = null;
        boolean access = method.isAccessible();
        method.setAccessible(true);
        try {
            result = method.invoke(target, (Object[]) parameters);
        } finally {
            method.setAccessible(access);
        }
        return result;
    }

}
