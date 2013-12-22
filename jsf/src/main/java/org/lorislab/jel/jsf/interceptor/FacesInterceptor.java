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
package org.lorislab.jel.jsf.interceptor;

import org.lorislab.jel.log.interceptor.AbstractInterceptor;
import org.lorislab.jel.jsf.interceptor.annotations.FacesServiceMethod;
import org.lorislab.jel.jsf.util.FacesResourceUtil;
import java.lang.reflect.Method;
import java.security.Principal;
import javax.faces.context.FacesContext;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * The faces interceptor.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
@FacesServiceMethod
@Interceptor
public class FacesInterceptor extends AbstractInterceptor {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -6427308140834397727L;

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getUser() {
        Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
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
    public Object methodExecution(InvocationContext ic) throws Exception {
        Object result = null;
        try {
            result = super.methodExecution(ic);
        } catch (Exception ex) {
            boolean handleException = true;
            Method method = ic.getMethod();
            FacesServiceMethod ano = method.getAnnotation(FacesServiceMethod.class);
            if (ano != null) {
                handleException = ano.handleException();
            }
            if (handleException) {
                FacesResourceUtil.handleExceptionMessage(ex);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isContextLogger(Method method) {
        boolean result = true;
        FacesServiceMethod ano = method.getAnnotation(FacesServiceMethod.class);
        if (ano != null) {
            result = ano.contextLogger();
        }
        return result;
    }
}
