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

import org.lorislab.jel.jsf.view.common.Context;
import org.lorislab.jel.jsf.view.controller.ViewController;

/**
 * The context menu action.
 *
 * @param <T> the context menu view controller type.
 *
 * @author Andrej Petras
 */
public class MenuAction<T extends ViewController> extends AbstractAction<T> {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -2570364728504344270L;
    /**
     * The navigation path.
     */
    private final String navigation;
   
    /**
     * The default constructor.
     *
     * @param parent the parent view controller.
     * @param context the context.
     * @param permission the permission.
     * @param navigation the navigation path.
     */
    public MenuAction(T parent, Enum context, Enum permission, String navigation) {
        super(parent, context, permission);
        this.navigation = navigation;
    }

    /**
     * The default constructor.
     *
     * @param parent the parent view controller.
     * @param permission the permission.
     * @param navigation the navigation path.
     */
    public MenuAction(T parent, Enum permission, String navigation) {
        this(parent, Context.MENU, permission, navigation);
    }
    
    /**
     * The default constructor.
     *
     * @param parent the parent view controller.
     * @param permission the permission.
     */
    public MenuAction(T parent, Enum permission) {
        this(parent, Context.MENU, permission, null);
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    protected Object doExecute() throws Exception {
        return navigation;
    }

   
}
