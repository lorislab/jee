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
package org.lorislab.jel.log.context;

import java.util.List;

/**
 * The context logger.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
public interface ContextLogger {

    /**
     * Gets the start log string.
     *
     * @param user the user name.
     * @param clazz the class.
     * @param method the method.
     * @param parameters the list of parameters.
     * @return the logger string.
     */
    String getLogStart(String user, String clazz, String method, List<String> parameters);

    /**
     * Gets the succeed log string.
     *
     * @param user the user name.
     * @param clazz the class.
     * @param method the method
     * @param parameters the list of parameters.
     * @param time the start time.
     * @param result the result value.
     * @return the logger string.
     */
    String getLogSucceed(String user, String clazz, String method, List<String> parameters, double time, String result);

    /**
     * Gets the failed log string.
     *
     * @param user the user name.
     * @param clazz the class.
     * @param method the method.
     * @param parameters the list of parameters.
     * @param time the start time.
     * @return the logger string.
     */
    String getLogFailed(String user, String clazz, String method, List<String> parameters, double time);
}
