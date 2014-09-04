/*
 * Copyright 2011 Andrej Petras <andrej@ajka-andrej.com>.
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
package org.lorislab.jel.jsf.table.controller;

import org.lorislab.jel.base.criteria.AbstractSearchCriteria;
import org.lorislab.jel.jpa.model.Persistent;

/**
 * The abstract table view controller.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 * @param <T> the model.
 * @param <S> the search criteria.
 */
public abstract class AbstractTableSearchViewController<T extends Persistent, S extends AbstractSearchCriteria> extends AbstractTableViewController<T> {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = 8552218747873068051L;
    /**
     * The search criteria.
     */
    private S criteria;

    /**
     * Sets the search criteria.
     *
     * @param criteria the search criteria.
     */
    protected void setCriteria(S criteria) {
        this.criteria = criteria;
    }

    /**
     * Gets the search criteria.
     *
     * @return the search criteria.
     */
    public S getCriteria() {
        return criteria;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Object reset() throws Exception {
        criteria.reset();
        return super.reset();
    }
    
    
}
