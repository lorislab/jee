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
package org.lorislab.jel.log.parameters;

/**
 * The instance log parameter.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
public interface InstanceOfLogParameter extends LogParameter {

    /**
     * Returns
     * <code>true</code> if the parameter is
     * <code>instanceof</code> of defined classes.
     *
     * @param parameter the method parameter.
     * @return <code>true</code> if the parameter is <code>instanceof</code>
     * class.
     */
    boolean instanceOfClasses(Object parameter);
}
