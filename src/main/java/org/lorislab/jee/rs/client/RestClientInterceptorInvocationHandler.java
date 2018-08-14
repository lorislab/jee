package org.lorislab.jee.rs.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import javax.interceptor.InvocationContext;
import org.lorislab.jee.interceptor.CdiServiceInterceptor;

/**
 * The rest client invocation handler.
 *
 * @author Andrej_Petras
 */
public class RestClientInterceptorInvocationHandler implements InvocationHandler {

    /**
     * The client.
     */
    private final Object client;
   
    /**
     * The client class.
     */
    private final Class clientClass;
    
    private final CdiServiceInterceptor interceptor;
    /**
     * The default constructor.
     *
     * @param client the client.
     * @param clientClass the client class.
     */
    public RestClientInterceptorInvocationHandler(Object client, Class clientClass, CdiServiceInterceptor interceptor) {
        this.client = client;
        this.clientClass = clientClass;
        this.interceptor = interceptor;
    }

    /**
     * {@inheritDoc }
     *
     * @throws java.lang.Throwable if the method fails.
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        InvocationContext ctx = new ObjectInvocationContext(client, method, args);
        Object result = interceptor.execution(ctx, clientClass);
        return result;
    }

}
