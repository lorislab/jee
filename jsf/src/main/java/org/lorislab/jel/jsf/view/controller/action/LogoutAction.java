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
package org.lorislab.jel.jsf.view.controller.action;

import javax.faces.context.FacesContext;
import org.lorislab.jel.jsf.view.common.Permission;
import org.lorislab.jel.jsf.view.controller.ViewController;

/**
 * The logout action.
 * 
 * @param <T> the parent type.
 * 
 * @author Andrej Petras
 */
public class LogoutAction<T extends ViewController> extends MenuAction<T> {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -9154512589848412309L;

    /**
     * The default constructor.
     *
     * @param parent the parent view controller.
     * @param context the context.
     * @param permission the permission.
     * @param navigation the navigation path.
     */
    public LogoutAction(T parent, Enum context, Enum permission, String navigation) {
        super(parent, context, permission, navigation);
    }

    /**
     * The default constructor.
     *
     * @param parent the parent view controller.
     * @param navigation the navigation path.
     */
    public LogoutAction(T parent, String navigation) {
        super(parent, Permission.LOGOUT, navigation);
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public Object doExecute() throws Exception {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return super.execute();
    }
}
