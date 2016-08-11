/*
 * Copyright 2011 Andrej Petras <andrej@ajka-andrej.com>.
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
package org.lorislab.jel.base.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.ApplicationException;

/**
 * The exception class for service exception with localised message.
 *
 * @author Andrej Petras <andrej@lorislab.org>
 */
@ApplicationException(rollback = true)
public class ServiceException extends Exception implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -4874552216768714025L;

    /**
     * The key of resource.
     */
    private final Enum<?> key;

    /**
     * The arguments for the message.
     */
    private final List<Serializable> parameters = new ArrayList<>();

    private final Map<String, Serializable> namedParameters = new HashMap<>();

    private boolean stackTraceLog = true;

    /**
     * The constructor with the resource key and cause.
     *
     * @param key the resource key.
     * @param parameters the resource key arguments.
     * @param cause the throw able cause.
     */
    public ServiceException(final Enum<?> key, final Throwable cause, Serializable... parameters) {
        super(cause);
        this.key = key;
        if (parameters != null) {
            this.parameters.addAll(Arrays.asList(parameters));
        }
    }

    /**
     * Gets the key of resource.
     *
     * @return the key of resource.
     */
    public final Enum<?> getKey() {
        return key;
    }

    /**
     * Gets the arguments of the message.
     *
     * @return the arguments of the message.
     */
    public final List<Serializable> getParameters() {
        return parameters;
    }
    
    public void addParameter(String name, Serializable parameter) {
        namedParameters.put(name, parameter);
    }

    public Map<String, Serializable> getNamedParameters() {
        return namedParameters;
    }
    
    public boolean isStackTraceLog() {
        return stackTraceLog;
    }

    public void setStackTraceLog(boolean stackTraceLog) {
        this.stackTraceLog = stackTraceLog;
    }

}
