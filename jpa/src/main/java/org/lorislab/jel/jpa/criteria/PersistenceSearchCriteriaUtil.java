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
package org.lorislab.jel.jpa.criteria;

import org.lorislab.jel.base.criteria.BetweenCriterion;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

/**
 * The persistence search criteria utility class.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
public final class PersistenceSearchCriteriaUtil {

    /**
     * The default constructor.
     */
    private PersistenceSearchCriteriaUtil() {
        // empty constructor
    }

    /**
     * Create predicate for the between criteria.
     *
     * @param <Y> the entity class.
     * @param builder persistence criteria builder.
     * @param expression the expression.
     * @param criteria the between criteria.
     * @return the predicate.
     */
    public static <Y extends Comparable<? super Y>> Predicate createBetweenPredicate(CriteriaBuilder builder, Expression<Y> expression, BetweenCriterion<Y> criteria) {
        Predicate result = null;
        if (criteria != null && !criteria.isEmpty()) {
            if (criteria.isFrom() && criteria.isTo()) {
                result = builder.between(expression, criteria.getFrom(), criteria.getTo());
            } else {
                if (criteria.isFrom()) {
                    result = builder.equal(expression, criteria.getFrom());
                } else if (criteria.isTo()) {
                    result = builder.lessThanOrEqualTo(expression, criteria.getTo());
                }
            }
        }
        return result;
    }
}
