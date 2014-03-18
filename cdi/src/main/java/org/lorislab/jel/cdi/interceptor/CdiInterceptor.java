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
package org.lorislab.jel.cdi.interceptor;

import org.lorislab.jel.cdi.interceptor.annotations.CdiServiceMethod;
import org.lorislab.jel.log.interceptor.AbstractInterceptor;
import java.lang.reflect.Method;
import java.security.Principal;
import javax.inject.Inject;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * The rest service interceptor.
 *
 * @author Andrej Petras
 */
@CdiServiceMethod
@Interceptor
public class CdiInterceptor extends AbstractInterceptor {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -6427308140834397727L;

    @Inject
    private Principal principal;
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected String getUser() {
        if (principal != null) {
            String name = principal.getName();
            if (name != null && !name.isEmpty()) {
                return name;
            }
        }
        return null;
    }  

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getClassName(Class clazz) {
        String result = clazz.getName();
        int index = result.indexOf('$');
        if (index != -1) {
            result = result.substring(0, index);
        }
        return result;
    }
   
    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isContextLogger(Method method) {
        boolean result = true;
        CdiServiceMethod ano = method.getAnnotation(CdiServiceMethod.class);
        if (ano != null) {
            result = ano.contextLogger();
        }
        return result;
    }
}
