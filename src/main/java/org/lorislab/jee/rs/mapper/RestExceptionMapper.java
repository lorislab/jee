/*
 * Copyright 2014 Andrej Petras.
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
package org.lorislab.jee.rs.mapper;

import org.lorislab.jee.rs.LoggerRestConfiguration;
import java.io.Serializable;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.lorislab.jee.exception.ServiceException;
import org.lorislab.jee.interceptor.RequestDataThreadHolder;
import org.lorislab.jee.interceptor.RequestData;
import org.lorislab.java.util.ResourceManager;
import org.lorislab.jee.rs.interceptor.RequestDataHeaderProperties;
import org.lorislab.jee.rs.model.RestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The rest service exception mapper.
 *
 * @author Andrej Petras
 */
@Provider
public class RestExceptionMapper implements ExceptionMapper<Exception>, Serializable {

    private static final long serialVersionUID = 867843653953972592L;

    /**
     * The HTTP header context.
     */
    @Context
    private HttpHeaders headers;

    /**
     * The HTTP request.
     */
    @Context
    private HttpServletRequest request;

    @Context
    private ResourceInfo resourceInfo;

    /**
     * {@inheritDoc}
     */
    @Override
    public Response toResponse(Exception exception) {
        Locale locale = request.getLocale();

        // check the media type
        MediaType type = MediaType.APPLICATION_JSON_TYPE;
        try {
            type = headers.getMediaType();
            if (type == null || !MediaType.APPLICATION_XML_TYPE.equals(type)) {
                type = MediaType.APPLICATION_JSON_TYPE;
            }
        } catch (Exception ex) {
            // do nothing if no header set
        }

        String msg = exception.getMessage();
        Enum<?> key = RestServiceErrors.RS_SERVICE_UNDEFINED_ERROR;

        RestException entity = new RestException();
        if (exception instanceof ServiceException) {
            ServiceException se = (ServiceException) exception;
            if (se.getKey() != null) {
                msg = ResourceManager.getMessage(se.getKey(), se.getParameters(), locale);
                key = se.getKey();
            }
            entity.namedParameters = se.getNamedParameters();
            entity.parameters = se.getParameters();
        }
        entity.message = msg;
        entity.errorClass = key.getClass().getName();
        entity.error = key.name();

        // write the log failed message
        RequestData data = RequestDataThreadHolder.get();
        if (data != null) {
            entity.requestId = data.getId();
            Logger logger = LoggerFactory.getLogger(resourceInfo.getResourceClass());

            Exception logEx = exception;
            if (exception instanceof ServiceException) {
                ServiceException ex = (ServiceException) exception;
                if (ex.isStackTraceLog()) {
                    logEx = null;
                }
            }
            logger.error("{}", LoggerRestConfiguration.msgException(data.getClientPrincipal(), data.getClientHost(), resourceInfo.getResourceMethod().getName(), request.getRequestURI(), exception.getClass().getSimpleName()), logEx);
        }

        return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(entity)
                .type(type)
                .header(RequestDataHeaderProperties.HEADER_EXCEPTION, RestException.class.getName())
                .build();

    }

}
