/*
 * Copyright 2014 lorislab.org.
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
package org.lorislab.jel.jsf.view.controller;

import java.io.Serializable;
import org.lorislab.jel.jsf.view.common.ViewControllerMode;

/**
 * The default basic view controller.
 *
 * @author Andrej_Petras
 *
 * @param <T> the parent view controller type.
 */
public abstract class AbstractChildViewController<T extends ViewController> implements ViewController, Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = 8458381369263822821L;

    /**
     * The parent view controller.
     */
    private final T parent;

    /**
     * The default constructor.
     *
     * @param parent the parent view controller.
     */
    public AbstractChildViewController(T parent) {
        this.parent = parent;
    }

    /**
     * Gets the parent view controller.
     *
     * @return the parent view controller.
     */
    protected T getParent() {
        return parent;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ViewControllerMode getMode() {
        return parent.getMode();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean hasUserAction(Enum context, Enum permission) {
        return parent.hasUserAction(context, permission);
    }

}
