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
package com.ajkaandrej.lib.ejb.wrapper;

import java.io.Serializable;

/**
 * The abstract wrapper class for the model.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 *
 * @param <T> the model.
 */
public abstract class AbstractWrapper<T extends Object> implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -2696404652007518945L;
    /**
     * The model.
     */
    protected T model;

    /**
     * Gets the model.
     *
     * @return the model.
     */
    public final T getModel() {
        return model;
    }

    /**
     * Sets the model.
     *
     * @param model the new model.
     */
    public final void setModel(final T model) {
        this.model = model;
    }

    /**
     * Returns
     * <code>true</code> if the model is null.
     *
     * @return <code>true</code> if the model is null.
     */
    public final boolean isModelEmpty() {
        return model == null;
    }

    /**
     * Gets the model GUID.
     *
     * @return the model GUID.
     */
    public abstract String getGuid();
}
