/*
 * Copyright 2012 Andrej Petras <andrej@ajka-andrej.com>.
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
package org.lorislab.jel.core.logger.parameters;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.lorislab.jel.core.logger.parameter.LoggerParameter;

/**
 * The collection log parameter.
 *
 * @author Andrej Petras <andrej@lorislab.org>
 */
@Named
public class CollectionLoggerParameter extends LoggerParameter {
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Class[] getAssignableFrom() {
        return new Class[] { Collection.class };
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Object getObject(Object parameter) {
        Collection<?> tmp = (Collection<?>) parameter;
        String name = tmp.getClass().getSimpleName();

        StringBuilder sb = new StringBuilder();

        if (tmp.isEmpty()) {
            sb.append("empty ").append(name);
        } else {
            sb.append(name).append('(').append(tmp.size());
            Class clazz = Object.class;
            if (parameter.getClass().getGenericSuperclass() instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) parameter.getClass().getGenericSuperclass();
                Type type = parameterizedType.getActualTypeArguments()[0];
                clazz = type.getClass();

                // check generic type variable
                if (type instanceof TypeVariable) {

                    // load first item from the collection
                    Object obj = tmp.iterator().next();
                    if (obj != null) {
                        clazz = obj.getClass();
                    }
                }
            } else {
                Object obj = tmp.iterator().next();
                if (obj != null) {
                    clazz = obj.getClass();
                }
            }
            sb.append(clazz.getSimpleName());
            sb.append(')');
        }
        return sb.toString();        
    }
}
