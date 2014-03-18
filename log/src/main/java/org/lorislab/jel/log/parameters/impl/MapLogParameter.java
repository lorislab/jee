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
package org.lorislab.jel.log.parameters.impl;

import org.lorislab.jel.log.parameters.InstanceOfLogParameter;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The map instances log parameter.
 *
 * @author Andrej Petras
 */
public class MapLogParameter implements InstanceOfLogParameter {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean instanceOfClasses(Object parameter) {
        return parameter instanceof Map;
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
        Map<?, ?> tmp = (Map<?, ?>) parameter;
        String name = tmp.getClass().getSimpleName();
        if (tmp.isEmpty()) {
            return "empty " + name;
        }
        Entry<?, ?> item = tmp.entrySet().iterator().next();
        return name + " " + tmp.size() + " of [" + item.getKey().getClass().getSimpleName() + "," + item.getValue().getClass().getSimpleName() + "]";
    }
}
