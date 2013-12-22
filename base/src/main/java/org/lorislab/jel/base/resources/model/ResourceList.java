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
package org.lorislab.jel.base.resources.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The resource list. This class contains the list of values. The list of values
 * is use in the resource manage to generate one string value. For example for
 * the list of values ["1",34.5,"text"] the string value would be "1,34.5,text".
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
public class ResourceList {

    /**
     * The list of values.
     */
    private List<Object> values;

    /**
     * The default constructor.
     */
    public ResourceList() {
        values = new ArrayList<>();
    }

    /**
     * The default constructor.
     *
     * @param params the list of values.
     */
    public ResourceList(Object[] params) {
        this();
        if (params != null) {
            values.addAll(Arrays.asList(params));
        }
    }

    /**
     * Gets the list of values.
     *
     * @return the list of values.
     */
    public List<Object> getValues() {
        return values;
    }
}
