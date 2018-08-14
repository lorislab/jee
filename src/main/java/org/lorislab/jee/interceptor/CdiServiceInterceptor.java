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
package org.lorislab.jee.interceptor;

import java.security.Principal;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.Interceptor;
import org.lorislab.jee.annotation.LoggerService;

/**
 *
 * @author Andrej Petras
 */
@Interceptor
@LoggerService
@Priority(Interceptor.Priority.APPLICATION)
public class CdiServiceInterceptor extends AbstractServiceInterceptor {

    private static final long serialVersionUID = 2488602696161384535L;

    @Inject
    private Principal principal;

    @Override
    protected String getPrincipal() {
        return InterceptorUtil.getPrincipalName(principal);
    }
    
}
