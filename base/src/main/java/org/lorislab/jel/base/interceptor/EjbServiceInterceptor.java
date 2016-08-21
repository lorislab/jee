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
package org.lorislab.jel.base.interceptor;

import javax.annotation.Resource;
import javax.ejb.EJBContext;

/**
 *
 * @author Andrej Petras
 */
public class EjbServiceInterceptor extends AbstractServiceInterceptor {

    private static final long serialVersionUID = 8533857449176560173L;

    /**
     * The EJB context.
     */
    @Resource
    private EJBContext context;
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected String getPrincipal() {
        if (context != null) {
            return InterceptorUtil.getPrincipalName(context.getCallerPrincipal());
        }
        return null;
    }
    
}
