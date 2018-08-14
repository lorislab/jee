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

import java.util.UUID;
import org.lorislab.jee.Configuration;
import org.slf4j.MDC;

/**
 *
 * @author Andrej Petras
 */
public class RequestDataThreadHolder {

    /**
     * The logger request ID. %X{JEL_LOG_ID}
     */
    private static String LOG_ID = "JEL_LOG_ID";

    private static ThreadLocal<RequestData> requestDataThreadLocal = new ThreadLocal<>();

    public static RequestData get() {
        return requestDataThreadLocal.get();
    }

    public static void set(RequestData data) {
        requestDataThreadLocal.set(data);
        if (data != null) {
            MDC.put(LOG_ID, data.getId());
        } else {
            MDC.remove(LOG_ID);
        }
    }

    public static void clear() {
        requestDataThreadLocal.remove();
        MDC.remove(LOG_ID);
    }
    
    public static RequestData createAndSet(String id, String principal, String clientPrincipal, String client, String clientHost) {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        if (principal == null) {
            principal =  Configuration.PATTERN_NO_USER;
        }
        RequestData result = create(id, principal, clientPrincipal, client, clientHost);
        set(result);
        return result;
    }

    private static RequestData create(String id, String principal, String clientPrincipal, String client, String clientHost) {
        RequestData result = new RequestData();
        result.setClient(client);
        result.setId(id);
        result.setPrincipal(principal);
        result.setClientPrincipal(clientPrincipal);
        result.setClientHost(clientHost);
        result.setStartTime(System.currentTimeMillis());
        return result;
    }
}
