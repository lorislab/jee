/*
 * Copyright 2012 Andrej_Petras.
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
package org.lorislab.jel.log.config;

import org.lorislab.jel.log.context.ContextLogger;
import org.lorislab.jel.log.parameters.ClassLogParameter;
import org.lorislab.jel.log.parameters.InstanceOfLogParameter;
import org.lorislab.jel.log.parameters.LogParameter;
import java.util.List;

/**
 * The log service configuration interface.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
public interface LogServiceConfiguration {

    /**
     * Gets the list of class log parameters.
     *
     * @return the list of class log parameters.
     */
    List<ClassLogParameter> getClassLogParameters();

    /**
     * Gets the list of instance of log parameters.
     *
     * @return the list of instance of log parameters.
     */
    List<InstanceOfLogParameter> getInstanceOfLogParameters();

    /**
     * Gets the default log parameter.
     *
     * @return the default log parameter.
     */
    LogParameter getDefaultLogParameter();

    /**
     * Gets the context logger.
     *
     * @return the context logger.
     */
    ContextLogger getContextLogger();
}
