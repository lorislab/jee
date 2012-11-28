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
package com.ajkaandrej.lib.base.criteria;

import java.io.Serializable;

/**
 * The abstract search criteria.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
public abstract class AbstractSearchCriteria implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -1784020779033097744L;

    /**
     * The default constructor.
     */
    public AbstractSearchCriteria() {
        reset();
    }

    /**
     * Resets the all search criteria.
     */
    public abstract void reset();

    /**
     * Returns
     * <code>true</code> if the search criteria are empty else return
     * <code>false</code>.
     *
     * @return returns <code>true</code> if the search criteria are empty else
     * return <code>false</code>.
     */
    public abstract boolean isEmpty();

    /**
     * Returns
     * <code>true</code> if all values are not null.
     *
     * @param values the list of values.
     * @return <code>true</code> if all values are not null.
     */
    protected boolean isEmpty(Object... values) {
        for (Object item : values) {
            if (item != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns
     * <code>true</code> if all values are not null and criteria are not empty.
     *
     * @param values the list of values.
     * @return <code>true</code> if all values are not null.
     *
     * @see BetweenCriterion#isEmpty()
     */
    protected boolean isEmpty(BetweenCriterion... values) {
        for (BetweenCriterion item : values) {
            if (item != null && item.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
