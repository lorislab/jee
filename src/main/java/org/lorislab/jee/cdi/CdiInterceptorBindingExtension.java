/*
 * Copyright 2016 Andrej Petras.
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
package org.lorislab.jee.cdi;

import org.lorislab.jee.annotation.LoggerService;
import org.lorislab.jee.cdi.util.JelConfig;
import org.lorislab.jee.interceptor.CdiServiceInterceptor;
import org.lorislab.jee.logger.LoggerParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.WithAnnotations;

/**
 *
 * @author Andrej Petras
 */
public class CdiInterceptorBindingExtension implements Extension {

    private static final String PROPERTY_CDI_AUTOBINDING = "org.lorislab.jel.base.interceptor.cdi.autobinding";

    public static final boolean CDI_AUTOBINDING;

    private static final Logger LOGGER = LoggerFactory.getLogger(CdiInterceptorBindingExtension.class);

    static {
        CDI_AUTOBINDING = JelConfig.getBooleanProperty(PROPERTY_CDI_AUTOBINDING, true, CdiInterceptorBindingExtension.class);
    }
    
    @SuppressWarnings("unchecked")
    <T> void processAnnotatedType(@Observes @WithAnnotations({RequestScoped.class, SessionScoped.class, ApplicationScoped.class}) ProcessAnnotatedType<T> processAnnotatedType) {
        if (CDI_AUTOBINDING) {
            AnnotatedType<T> annotatedType = processAnnotatedType.getAnnotatedType();
            Class clazz = annotatedType.getJavaClass();
            if (!clazz.isAnnotationPresent(LoggerService.class) && !LoggerParameter.class.isAssignableFrom(clazz)) {
                LOGGER.debug("Found bean: {} activate the  LoggerService binding. ", clazz.getName());
                LoggerService annotation = CdiServiceInterceptor.class.getAnnotation(LoggerService.class);

                AnnotatedTypeWrapper<T> wrapper = new AnnotatedTypeWrapper<>(annotatedType, annotatedType.getAnnotations());
                wrapper.addAnnotation(annotation);

                processAnnotatedType.setAnnotatedType(wrapper);
            }
        }
    }
}
