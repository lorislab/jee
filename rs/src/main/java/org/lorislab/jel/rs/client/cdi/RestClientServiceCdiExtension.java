package org.lorislab.jel.rs.client.cdi;

import java.util.HashSet;
import java.util.Set;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.util.AnnotationLiteral;
import org.lorislab.jel.rs.client.annotation.RestClient;

/**
 *
 * @author Andrej Petras
 */
public class RestClientServiceCdiExtension implements Extension {

    /**
     * Discovery the custom REST beans.
     *
     * @param abd the after bean discovery.
     * @param bm the bean manager.
     */
    void afterBeanDiscovery(@Observes AfterBeanDiscovery abd, BeanManager bm) {

        Set<String> restClients = new HashSet<>();
        // create the rest clients
        Set<Bean<?>> allBeans = bm.getBeans(Object.class, new AnnotationLiteral<Any>() {
            private static final long serialVersionUID = 3109256773218160485L;
        });

        for (Bean<?> bean : allBeans) {
            Set<InjectionPoint> bb = bean.getInjectionPoints();
            if (bb != null) {
                for (InjectionPoint ip : bb) {
                    RestClient rest = ip.getAnnotated().getAnnotation(RestClient.class);
                    if (rest != null) {

                        // create ID for the bean = class name + configuration id
                        Class<?> clazz = (Class<?>) ip.getType();
                        String value = rest.value();
                        String id = clazz.getName() + value;
                        
                        if (!restClients.contains(id)) {
                            
                            restClients.add(id);
                            RestClientServiceBean sbean = new RestClientServiceBean(clazz, value);
                            abd.addBean(sbean);                           
                        }
                    }
                }
            }
        }
    }
    
}
