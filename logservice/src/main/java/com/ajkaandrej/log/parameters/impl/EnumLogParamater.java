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
package com.ajkaandrej.log.parameters.impl;

import com.ajkaandrej.log.parameters.InstanceOfLogParameter;

/**
 * The enumeration log parameter.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
public class EnumLogParamater implements InstanceOfLogParameter {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isResult() {
        return true;
    }

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
    public boolean instanceOfClasses(Object parameter) {
        return parameter instanceof Enum;
    }
}
