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

import com.ajkaandrej.log.parameters.ClassLogParameter;
import java.util.ArrayList;
import java.util.List;

/**
 * The basic log parameter.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
public class BasicLogParamater implements ClassLogParameter {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Class<?>> getClasses() {
        List<Class<?>> result = new ArrayList<>();
        result.add(Class.class);
        result.add(int.class);
        result.add(double.class);
        result.add(float.class);
        result.add(boolean.class);
        result.add(long.class);
        result.add(Integer.class);
        result.add(Double.class);
        result.add(String.class);
        result.add(Boolean.class);
        result.add(Long.class);
        return result;
    }

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
        return "" + parameter;
    }
}
