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
package org.lorislab.jee.jpa.model;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.lorislab.jee.logger.LoggerParameter;


/**
 * The persistent log service parameter.
 *
 * @author Andrej Petras
 */
@Named
@ApplicationScoped
public class PersistentLogParameter extends LoggerParameter {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getObject(Object parameter) {
        AbstractPersistent tmp = (AbstractPersistent) parameter;
        return parameter.getClass().getSimpleName() + ":" + tmp.getGuid();
    }

    @Override
    public Class getAssignableFrom() {
        return AbstractPersistent.class;
    }

    
}
