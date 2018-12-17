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
package org.lorislab.jee.cdi.util;

import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The CDI utility class.
 *
 * @author Andrej_Petras
 */
public final class CDIUtil {

    /**
     * The default constructor.
     */
    private CDIUtil() {
        // empty constructor
    }

    /**
     * Gets the bean instance.
     *
     * @param <T> the bean type.
     * @param clazz the class of the bean.
     * @param bm the bean manager instance.
     * @return the bean instance.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clazz, BeanManager bm) {
        final Set<Bean<?>> beans = bm.getBeans(clazz);
        final Bean<?> bean = bm.resolve(beans);
        CreationalContext ctx = bm.createCreationalContext(bean);
        return (T) bm.getReference(bean, clazz, ctx);
    }

}
