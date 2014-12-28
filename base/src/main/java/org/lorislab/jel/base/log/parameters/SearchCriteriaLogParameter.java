/*
 * Copyright 2014 Andrej_Petras.
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
package org.lorislab.jel.base.log.parameters;

import org.kohsuke.MetaInfServices;
import org.lorislab.jel.base.criteria.AbstractSearchCriteria;
import org.lorislab.jel.log.parameters.InstanceOfLogParameter;
import org.lorislab.jel.log.util.ReflectionUtil;

/**
 * The search criteria log parameter.
 * 
 * @author Andrej_Petras
 */
@MetaInfServices
public class SearchCriteriaLogParameter implements InstanceOfLogParameter {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean instanceOfClasses(Object parameter) {
        return parameter instanceof AbstractSearchCriteria;
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
        return ReflectionUtil.toString(parameter, 5);
    }
}
