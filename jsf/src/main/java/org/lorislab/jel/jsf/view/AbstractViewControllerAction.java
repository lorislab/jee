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
package org.lorislab.jel.jsf.view;

import java.io.Serializable;

/**
 * The abstract view controller action.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
public abstract class AbstractViewControllerAction<T> implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -2802481738839679670L;
    /**
     * The parent view controller.
     */
    private T parent;

    /**
     * The default constructor.
     *
     * @param parent the parent view controller.
     */
    public AbstractViewControllerAction(T parent) {
        this.parent = parent;
    }

    /**
     * Gets the parent view controller.
     *
     * @return the parent view controller.
     */
    public T getParent() {
        return parent;
    }

    /**
     * Gets the enabled status.
     *
     * @return return <code>true</code> if the action is enabled else
     * return <code>false</code>. Default implementation return
     * always <code>true</code>.
     */
    public boolean isEnabled() {
        return true;
    }

    /**
     * Gets the available status.
     *
     * @return return <code>true</code> if the action is available else
     * return <code>false</code>. Default implementation return
     * always <code>true</code>.
     */
    public boolean isAvailable() {
        return true;
    }

    /**
     * The execution method of button.
     *
     * @return the value of the web site navigation.
     * @throws Exception if the method fails.
     */
    public Object execute() throws Exception {
        return null;
    }
}
