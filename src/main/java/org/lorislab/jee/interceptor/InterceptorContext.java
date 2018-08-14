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

/**
 *
 * @author Andrej Petras
 */
class InterceptorContext {

    /**
     * The request id.
     */
    public final String id;

    /**
     * The principal.
     */
    public final String principal;

    /**
     * The service method.
     */
    public final String method;

    /**
     * The list of method parameters.
     */
    public final String parameters;

    /**
     * The result value.
     */
    public String result;

    /**
     * The execution time.
     */
    public String time;

    /**
     * The start time.
     */
    public final long startTime = System.currentTimeMillis();

    public InterceptorContext(String id, String principal, String method, String parameters) {
        this.id = id;
        this.principal = principal;
        this.method = method;
        this.parameters = parameters;
    }

    public Object[] getSuccessParams() {
        return new Object[]{this.principal, this.method, parameters, this.result, time};
    }

    public Object[] getFailedParams() {
        return new Object[]{this.principal, this.method, parameters, this.result, time};
    }
    
}
