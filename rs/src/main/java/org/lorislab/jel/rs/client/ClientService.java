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
package org.lorislab.jel.rs.client;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.lorislab.jel.rs.client.filter.ClientServiceLogInterceptor;

/**
 *
 * @author Andrej Petras
 */
public class ClientService {

    private static Map<Class<?>, Object> clientCache = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T createClient(final Class<T> clazz, ClientConfig config) {

        // create the server URI
        URI uri = null;
        try {
            uri = new URI(config.getServer());
        } catch (Exception ex) {
            throw new RuntimeException("Error creating the URI form the server string: " + config.getServer(), ex);
        }

        T service = null;
        if (clientCache.containsKey(clazz)) {
            service = (T) clientCache.get(clazz);
        } else {

            // create the rest-easy provider factory
            ClientBuilder builder = ClientBuilder.newBuilder();
            if (config.isAllowAllHttps()) {
                builder.hostnameVerifier(new AllowAllHostnameVerifier());
            }

            // add the default interceptors.
            if (config.isDefaultLogInterceptor()) {
                builder.register(ClientServiceLogInterceptor.class);
            }

            // add the advanced interceptors
            List<Class> cpc = config.getComponentClasses();
            if (cpc != null) {
                for (Class filter : cpc) {
                    builder.register(filter);
                }
            }
            List<Object> filters = config.getComponents();
            if (filters != null) {
                for (Object filter : filters) {
                    builder.register(filter);
                }
            }

            Client client = builder.build();
            WebTarget target = client.target(uri);
            service = ClientServiceProxyFactory.newResource(clazz, target);
            clientCache.put(clazz, service);
        }
        return service;
    }

    public static class AllowAllHostnameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(String string, SSLSession ssls) {
            return true;
        }

    }
}
