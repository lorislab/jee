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
package org.lorislab.jel.jsf.view.controller.action;

import java.io.Serializable;
import org.lorislab.jel.jsf.api.interceptor.annotations.FacesServiceMethod;
import org.lorislab.jel.jsf.api.util.FacesResourceUtil;
import org.lorislab.jel.jsf.view.controller.ViewController;
import org.lorislab.jel.jsf.view.interceptor.ActionInterceptor;

/**
 * The abstract view controller action.
 *
 * @param <T> the view controller.
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
public abstract class AbstractAction<T extends ViewController> implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -2802481738839679670L;
    
    /**
     * The parent view controller.
     */
    private final T parent;

    /**
     * The action.
     */
    private Enum action;

    /**
     * The context.
     */
    private Enum context;
    
    /**
     * The default constructor.
     *
     * @param parent the parent view controller.
     * @param action the action.
     * @param context the context.
     */
    public AbstractAction(T parent, Enum context, Enum action) {
        this.parent = parent;
        this.action = action;
        this.context = context;
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
     * Gets the enabled status. Returns <code>true</code> if the action is
     * enabled else return <code>false</code>. Default implementation return
     * always <code>true</code>.
     *
     * @return returns {@code true} if the action is enabled.
     */
    public boolean isEnabled() {
        return true;
    }

    /**
     * Gets the available status.
     *
     * Returns <code>true</code> if the action is available else return
     * <code>false</code>.
     *
     * Check if the user has action and context.
     *
     * @return returns {@code true} if the user has action and context enabled.
     */
    public boolean isAvailable() {
        return getParent().hasUserAction(context, action);
    }

    /**
     * The execution method of button.
     *
     * @return the value of the web site navigation.
     */
    @FacesServiceMethod
    public Object execute() {
        Object result = null;
        if (isAvailable()) {
            result = ActionInterceptor.doExecution(this);
        }
        return result;
    }

    /**
     * The execution method of button.
     *
     * @return the value of the web site navigation.
     * @throws java.lang.Exception if the method fails.
     */
    protected Object doExecute() throws Exception {
        return null;
    }
}
