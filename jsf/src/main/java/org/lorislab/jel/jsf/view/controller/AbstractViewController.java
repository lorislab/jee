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
package org.lorislab.jel.jsf.view.controller;

import java.io.Serializable;
import javax.inject.Inject;
import org.lorislab.jel.jsf.permission.controller.PermissionController;
import org.lorislab.jel.jsf.view.common.ViewControllerMode;

/**
 * The abstract view controller.
 *
 * @author Andrej_Petras
 */
public abstract class AbstractViewController implements ViewController, Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -4986807990797011426L;

    /**
     * The permission controller.
     */
    @Inject
    private PermissionController permissionController;

    /**
     * The controller mode.
     */
    private ViewControllerMode mode;

    /**
     * The default constructor.
     */
    public AbstractViewController() {
        mode = ViewControllerMode.EDIT;
    }

    /**
     * Gets the view controller mode.
     *
     * @return the view controller mode.
     */
    @Override
    public ViewControllerMode getMode() {
        return mode;
    }

    /**
     * Sets view controller mode.
     *
     * @param mode the new view controller mode.
     */
    protected void setoMode(ViewControllerMode mode) {
        this.mode = mode;
    }

    /**
     * Returns {@code true} is the controller mode is view mode.
     *
     * @return {@code true} is the controller mode is view mode.
     */
    public boolean isViewMode() {
        return ViewControllerMode.VIEW == mode;
    }

    /**
     * Returns {@code true} is the controller mode is edit mode.
     *
     * @return {@code true} is the controller mode is edit mode.
     */
    public boolean isEditMode() {
        return ViewControllerMode.EDIT == mode;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean hasUserAction(Enum context, Enum permission) {
        return permissionController.hasUserAction(context, permission);
    }
}
