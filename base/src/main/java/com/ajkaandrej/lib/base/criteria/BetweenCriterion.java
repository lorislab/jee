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

/**
 * The search criteria between attribute.
 *
 * @param <T> the generic parameter.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
public class BetweenCriterion<T> {

    /**
     * The from value.
     */
    private T from;
    /**
     * The to value.
     */
    private T to;

    /**
     * The default constructor.
     */
    public BetweenCriterion() {
        reset();
    }

    /**
     * Gets the from value.
     *
     * @return the from value.
     */
    public T getFrom() {
        return from;
    }

    /**
     * Sets the from value.
     *
     * @param from the from to set
     */
    public void setFrom(T from) {
        this.from = from;
    }

    /**
     * Gets the to value.
     *
     * @return the to value.
     */
    public T getTo() {
        return to;
    }

    /**
     * Sets the to value.
     *
     * @param to the to to set
     */
    public void setTo(T to) {
        this.to = to;
    }

    /**
     * Reset the criterion.
     */
    public void reset() {
        from = null;
        to = null;
    }

    /**
     * Returns
     * <code>true</code> if the from value is not
     * <code>null</code>.
     *
     * @return <code>true</code> if the from value is not <code>null</code>.
     */
    public boolean isFrom() {
        return from != null;
    }

    /**
     * Returns
     * <code>true</code> if the to value is not
     * <code>null</code>.
     *
     * @return <code>true</code> if the to value is not <code>null</code>.
     */
    public boolean isTo() {
        return to != null;
    }

    /**
     * Returns
     * <code>true</code> if the from and to value is
     * <code>null</code>.
     *
     * @return <code>true</code> if the from and to value is <code>null</code>.
     */
    public boolean isEmpty() {
        return to == null && from == null;
    }

    /**
     * Writes the criterion into string [from:value,to:value].
     *
     * @return the string corresponding to the criterion.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[from:");
        sb.append(from);
        sb.append(',');
        sb.append("to:");
        sb.append(to);
        sb.append(']');
        return sb.toString();
    }
}
