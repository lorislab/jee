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
import org.lorislab.jel.jsf.view.common.ControllerMode;
import org.lorislab.jel.jsf.view.common.Permission;

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
     * The view controller context.
     */
    private Enum context;

    private ControllerMode mode;

    public AbstractViewController() {
    }
    
    public AbstractViewController(Enum context) {
        this.context = context;
        initiliaseMode();
    }

    /**
     * Initialise controller mode.
     */
    protected final void initiliaseMode() {
        mode = null;
        boolean tmp = changeToEditMode();
        if (!tmp) {
            changeToViewMode();
        }
    }

    protected boolean changeToEditMode() {
        boolean result = isEditMode();
        if (!result) {
            result = hasUserAction(context, Permission.EDIT);
            if (result) {
                mode = ControllerMode.EDIT;
            }
        }
        return result;
    }
    
    protected boolean changeToViewMode() {
        boolean result = isEditMode();
        if (!result) {
            result = hasUserAction(context, Permission.VIEW);
            if (result) {
                mode = ControllerMode.VIEW;
            }
        }
        return result;
    }

    public boolean isViewMode() {
        return ControllerMode.VIEW == mode;
    }

    public boolean isEditMode() {
        return ControllerMode.EDIT == mode;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean hasUserAction(Enum context, Enum permission) {
        return permissionController.hasUserAction(context, permission);
    }
}
