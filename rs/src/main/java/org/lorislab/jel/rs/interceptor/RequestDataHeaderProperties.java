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
package org.lorislab.jel.rs.interceptor;

/**
 *
 * @author Andrej Petras
 */
public final class RequestDataHeaderProperties {

    /**
     * HTTP header name for client request id.
     */
    public static String HEADER_ID = "X-JEL-ID";

    /**
     * HTTP header name for client identification.
     */
    public static String HEADER_CLIENT = "X-JEL-CLIENT";

    /**
     * HTTP header name for client principal.
     */
    public static String HEADER_PRINCIPAL = "X-JEL-PRINCIPAL";

    /**
     * HTTP header name for client host.
     */
    public static String HEADER_CLIENT_HOST = "X-JEL-CLIENT-HOST";

    public static String HEADER_EXCEPTION = "X-JEL-EXCEPTION";
    
    private RequestDataHeaderProperties() {
    }
    
    
}
