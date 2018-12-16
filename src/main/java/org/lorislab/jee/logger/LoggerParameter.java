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
package org.lorislab.jee.logger;

/**
 * The log parameter interface.
 *
 * @author Andrej Petras
 */
public abstract class LoggerParameter {

    @SuppressWarnings("WeakerAccess")
    protected int getPriority() {
        return 0;
    }
    
    public Class[] getClasses() {
        return null;
    }
    
    public Class getAssignableFrom() {
        return null;
    }
    
    /**
     * Gets the log parameter.
     *
     * @param parameter the service parameter.
     * @return the object value for the service parameter.
     */
    public abstract Object getObject(Object parameter);
}
