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

package org.lorislab.jel.rs.mapper;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.lorislab.jel.base.exception.AbstractSystemException;
import org.lorislab.jel.base.resources.ResourceManager;
import org.lorislab.jel.base.resources.model.ResourceMessage;
import org.lorislab.jel.rs.exception.RSException;

/**
 * The rest service exception mapper.
 * 
 * @author Andrej Petras
 */
@Provider
public class RSExceptionMapper implements ExceptionMapper<AbstractSystemException> {
    
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
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Response toResponse(AbstractSystemException exception) {
        Locale locale = request.getLocale();
        
        RSException entity = new RSException();        
        ResourceMessage rm = exception.getResourceMessage();
        entity.message = ResourceManager.getMessage(rm, locale);
        entity.key = rm.getResourceKey();
        entity.ref = rm.getReference();        
        entity.params = rm.getArguments();
        
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
        
        return Response.status(Status.BAD_REQUEST).entity(entity).type(type).build();

    }
    
}
