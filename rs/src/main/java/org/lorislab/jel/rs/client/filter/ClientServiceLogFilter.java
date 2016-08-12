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
package org.lorislab.jel.rs.client.filter;

import java.io.IOException;
import java.io.Serializable;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.ext.Provider;
import org.lorislab.jel.base.interceptor.RequestDataThreadHolder;
import org.lorislab.jel.base.interceptor.annotation.LoggerService;
import org.lorislab.jel.base.interceptor.model.RequestData;
import org.lorislab.jel.base.logger.HostNameService;
import org.lorislab.jel.base.logger.LoggerContext;
import org.lorislab.jel.rs.interceptor.RequestDataHeaderProperties;
import org.lorislab.jel.rs.logger.LoggerRestConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The rest client method logger filter.
 *
 * @author Andrej_Petras
 */
@Provider
@LoggerService(log = false)
public class ClientServiceLogFilter implements ClientRequestFilter, ClientResponseFilter, Serializable {

    private static final long serialVersionUID = 1263952252995068153L;

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientServiceLogFilter.class);

    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
        RequestData requestData = RequestDataThreadHolder.getOrCreate();
        String interval = LoggerContext.intervalString(requestData.getStartTime(), System.currentTimeMillis());
        LOGGER.info(LoggerRestConfiguration.PATTERN_CLIENT_SUCCEED, requestData.getPrincipal(), requestContext.getMethod(), requestContext.getUri().toString(), interval, responseContext.getStatus());
    }

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        RequestData requestData = RequestDataThreadHolder.getOrCreate();
        requestContext.getHeaders().add(RequestDataHeaderProperties.HEADER_ID, requestData.getId());
        requestContext.getHeaders().add(RequestDataHeaderProperties.HEADER_CLIENT_HOST, HostNameService.getHostName());
        requestContext.getHeaders().add(RequestDataHeaderProperties.HEADER_PRINCIPAL, requestData.getPrincipal());
        LOGGER.info(LoggerRestConfiguration.PATTERN_CLIENT_START, requestData.getPrincipal(), requestContext.getMethod(), requestContext.getUri().toString());
    }
}
