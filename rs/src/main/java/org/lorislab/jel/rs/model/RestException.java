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

package org.lorislab.jel.rs.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * The rest service exception.
 * 
 * @author Andrej Petras
 */
public final class RestException {
    
    public String requestId;
    /**
     * The exception key.
     */
    public String error;
    /**
     * The object reference.
     */
    public Map<String, Serializable> namedParameters;
    /**
     * The message.
     */
    public String message;
    /**
     * The parameters.
     */
    public List<Serializable> parameters;    
    
    public String errorClass;
}
