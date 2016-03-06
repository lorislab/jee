/*
 * Copyright 2012 Andrej Petras <andrej@ajka-andrej.com>.
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
package org.lorislab.jel.logger.parameters;

import javax.inject.Named;
import org.lorislab.jel.logger.parameter.LoggerParameter;

/**
 * The enumeration log parameter.
 *
 * @author Andrej Petras <andrej@lorislab.org>
 */
@Named
public class EnumLoggerParamater extends LoggerParameter {

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getObject(Object parameter) {
        return parameter.getClass().getSimpleName() + ":" + parameter.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class[] getAssignableFrom() {
        return new Class[] { Enum.class };
    }

}
