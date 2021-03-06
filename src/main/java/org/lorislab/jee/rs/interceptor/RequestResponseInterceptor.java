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
package org.lorislab.jee.rs.interceptor;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.ext.Provider;
import org.lorislab.jee.interceptor.InterceptorUtil;
import org.lorislab.jee.interceptor.RequestDataThreadHolder;
import org.lorislab.jee.annotation.LoggerService;
import org.lorislab.jee.interceptor.RequestData;
import org.lorislab.jee.rs.LoggerRestConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Andrej Petras
 */
@Provider
@LoggerService(log = false)
public class RequestResponseInterceptor implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestResponseInterceptor.class);
    /**
     * The HTTP-SERVLET context.
     */
    @Context
    private HttpServletRequest servletRequest;

    /**
     * The resource info.
     */
    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // load request data from request
        String id = (String) requestContext.getHeaders().getFirst(RequestDataHeaderProperties.HEADER_ID);
        String clientPrincipal = (String) requestContext.getHeaders().getFirst(RequestDataHeaderProperties.HEADER_PRINCIPAL);
        String client = (String) requestContext.getHeaders().getFirst(RequestDataHeaderProperties.HEADER_CLIENT);
        String clientHost = (String) requestContext.getHeaders().getFirst(RequestDataHeaderProperties.HEADER_CLIENT_HOST);
        if (clientHost == null) {
            clientHost = servletRequest.getHeader("X-Forwarded-For");
            if (clientHost == null) {
                clientHost = servletRequest.getRemoteHost();
            }
        }
        String principal = InterceptorUtil.getPrincipalName(requestContext.getSecurityContext().getUserPrincipal());
        clientPrincipal = InterceptorUtil.getPrincipalName(clientPrincipal);

        RequestData requestData = RequestDataThreadHolder.createAndSet(id, principal, clientPrincipal, client, clientHost);
        LoggerService ano = InterceptorUtil.getLoggerServiceAno(resourceInfo.getResourceClass(), resourceInfo.getResourceMethod());

        if (ano.log()) {
            // create the logger
            Logger logger = LoggerFactory.getLogger(resourceInfo.getResourceClass());
            logger.info("{}", LoggerRestConfiguration.msgStart(requestData.getClientPrincipal(), requestData.getClientHost(), servletRequest.getMethod(), requestContext.getUriInfo().getRequestUri()));
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {

        RequestData requestData = RequestDataThreadHolder.get();
        if (requestData != null) {
            try {
                LoggerService ano = InterceptorUtil.getLoggerServiceAno(resourceInfo.getResourceClass(), resourceInfo.getResourceMethod());
                if (ano.log()) {
                    responseContext.getHeaders().add(RequestDataHeaderProperties.HEADER_ID, requestData.getId());

                    if (responseContext.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
                        Logger logger = LoggerFactory.getLogger(resourceInfo.getResourceClass());
                        String interval = InterceptorUtil.intervalToString(requestData.getStartTime(), System.currentTimeMillis());
                        logger.info("{}", LoggerRestConfiguration.msgClientSucceed(requestData.getClientPrincipal(), requestData.getClientHost(), servletRequest.getMethod(), requestContext.getUriInfo().getRequestUri(), interval, responseContext.getStatus()));
                    } else {
                        if (resourceInfo != null && resourceInfo.getResourceClass() != null) {
                            try {
                                Logger logger = LoggerFactory.getLogger(resourceInfo.getResourceClass());
                                String interval = InterceptorUtil.intervalToString(requestData.getStartTime(), System.currentTimeMillis());
                                logger.info("{}", LoggerRestConfiguration.msgSucceed(requestData.getClientPrincipal(), requestData.getClientHost(), servletRequest.getMethod(), requestContext.getUriInfo().getRequestUri(), interval, responseContext.getStatus()));
                            } catch (Exception e) {
                                LOGGER.warn("No REST resouce found matching URI {}", requestContext.getUriInfo().toString(), e);
                            }
                        } else {
                            LOGGER.warn("No REST resouce found matching URI {}", requestContext.getUriInfo().toString());
                        }
                    }
                }
            } finally {
                RequestDataThreadHolder.clear();
            }
        }
    }
}
