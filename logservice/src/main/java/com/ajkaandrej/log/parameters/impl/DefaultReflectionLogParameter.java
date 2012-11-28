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
package com.ajkaandrej.log.parameters.impl;

import com.ajkaandrej.log.parameters.LogParameter;
import com.ajkaandrej.log.util.ReflectionUtil;

/**
 * The default reflection log parameter.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
public class DefaultReflectionLogParameter implements LogParameter {

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
        return ReflectionUtil.toString(parameter);
    }
}
