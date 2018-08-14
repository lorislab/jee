package org.lorislab.jee.rs.client;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.interceptor.InvocationContext;

/**
 * The object invocation context.
 *
 * @author Andrej_Petras
 */
public class ObjectInvocationContext implements InvocationContext {

    /**
     * The action.
     */
    private final Object target;

    /**
     * The method.
     */
    private final Method method;

    /**
     * The method parameters.
     */
    private Object[] parameters;

    /**
     * The invocation context data.
     */
    private Map<String, Object> contextData;

    /**
     * The default constructor.
     *
     * @param target the target.
     * @param method the method.
     * @param parameters the method parameters.
     */
    protected ObjectInvocationContext(Object target, Method method, Object... parameters) {
        this.target = target;
        this.method = method;
        this.parameters = parameters;
        this.contextData = new HashMap<>();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Object getTarget() {
        return target;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Method getMethod() {
        return method;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Object[] getParameters() throws IllegalStateException {
        return parameters;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setParameters(Object[] parameters) throws IllegalStateException, IllegalArgumentException {
        this.parameters = parameters;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Map<String, Object> getContextData() {
        return contextData;
    }

    /**
     * {@inheritDoc }
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

    @Override
    public Constructor<?> getConstructor() {
        return null;
    }
}
