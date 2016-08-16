package org.lorislab.jel.rs.client.cdi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.PassivationCapable;
import javax.enterprise.util.AnnotationLiteral;
import org.lorislab.jel.base.interceptor.CdiServiceInterceptor;
import org.lorislab.jel.base.util.CDIUtil;
import org.lorislab.jel.rs.client.ClientConfig;
import org.lorislab.jel.rs.client.ClientService;
import org.lorislab.jel.rs.client.annotation.RestClient;

/**
 * The rest service bean.
 *
 * @author Andrej_Petras
 * @param <T> the service interface.
 */
public class RestClientServiceBean<T> implements Bean<T>, PassivationCapable {

    /**
     * The service class.
     */
    private final Class<T> clazz;

    /**
     * The rest configuration name.
     */
    private final String value;

    private final boolean interceptor;

    /**
     * The default constructor.
     *
     * @param clazz the class.
     * @param value rest configuration name.
     */
    public RestClientServiceBean(Class<T> clazz, String value, boolean interceptor) {
        this.clazz = clazz;
        this.value = value;
        this.interceptor = interceptor;
    }

    /**
     * Gets the bean class.
     *
     * @return the bean class.
     */
    @Override
    public Class<?> getBeanClass() {
        return clazz;
    }

    /**
     * Gets the set of injection points.
     *
     * @return the set of injection points.
     */
    @Override
    public Set<InjectionPoint> getInjectionPoints() {
        return Collections.emptySet();
    }

    /**
     * Gets the bean names.
     *
     * @return the bean names.
     */
    @Override
    public String getName() {
        return clazz.getSimpleName() + "#" + value;
    }

    /**
     * Gets the set of annotation qualifiers.
     *
     * @return the set of annotation qualifiers.
     */
    @Override
    public Set<Annotation> getQualifiers() {
        Set<Annotation> qualifiers = new HashSet<>();
        qualifiers.add(new AnnotationLiteral<Default>() {
            private static final long serialVersionUID = 3109256773218160485L;
        });
        qualifiers.add(new AnnotationLiteral<Any>() {
            private static final long serialVersionUID = 3109256773218160485L;
        });
        qualifiers.add(new RestClient() {

            @Override
            public String value() {
                return value;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return RestClient.class;
            }
        });
        return qualifiers;
    }

    /**
     * Gets the scope.
     *
     * @return the scope.
     */
    @Override
    public Class<? extends Annotation> getScope() {
        return ApplicationScoped.class;
    }

    /**
     * Gets the set of stereotypes.
     *
     * @return the set of stereotypes.
     */
    @Override
    public Set<Class<? extends Annotation>> getStereotypes() {
        return Collections.emptySet();
    }

    /**
     * Gets the set of types.
     *
     * @return the set of types.
     */
    @Override
    public Set<Type> getTypes() {
        Set<Type> types = new HashSet<>();
        types.add(clazz);
        types.add(Object.class);
        return types;
    }

    /**
     * Gets the alternative flag.
     *
     * @return the alternative flag.
     */
    @Override
    public boolean isAlternative() {
        return false;
    }

    /**
     * Gets the null able flag.
     *
     * @return the null able flag.
     */
    @Override
    public boolean isNullable() {
        return false;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @SuppressWarnings("unchecked")
    public T create(CreationalContext<T> creationalContext) {
        final RestClientConfigService rccs = CDIUtil.getBean(RestClientConfigService.class);

        ClientConfig config = rccs.getClientConfig(clazz, value);
        T result = (T) ClientService.createClient(clazz, config);

        if (interceptor) {
            // create the proxy interceptor instance
            final CdiServiceInterceptor inter = CDIUtil.getBean(CdiServiceInterceptor.class);
            result = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new RestClientInterceptorInvocationHandler(result, clazz, inter));
        }
        return result;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void destroy(T instance, CreationalContext<T> creationalContext) {

    }

    @Override
    public String getId() {
        return clazz.getName() + value;
    }

}
