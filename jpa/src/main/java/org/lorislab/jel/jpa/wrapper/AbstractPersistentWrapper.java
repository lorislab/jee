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
package org.lorislab.jel.jpa.wrapper;

import org.lorislab.jel.base.wrapper.AbstractWrapper;
import org.lorislab.jel.jpa.model.Persistent;

/**
 * The abstract persistent wrapper.
 * 
 * @author Andrej_Petras
 * @param <T> the persistent type.
 */
public abstract class AbstractPersistentWrapper<T extends Persistent> extends AbstractWrapper<T> {
    
    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -2352862877151575082L;

    /**
     * {@inheritDoc }
     */
    @Override
    public String getGuid() {
        if (isEmpty()) {
            return null;
        }
        return getModel().getGuid();
    }
    
}
