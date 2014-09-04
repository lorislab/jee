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

package org.lorislab.jel.jsf.permission.controller.impl;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import org.lorislab.jel.jsf.permission.controller.PermissionController;

/**
 * The default permission controller.
 * 
 * @author Andrej_Petras
 */
@SessionScoped
public class DefaultPermissionController implements PermissionController, Serializable {
    
    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = 1598626325425493251L;

    /**
     * {@inheritDoc }
     * 
     * @return returns always {@code true}.
     */
    @Override
    public boolean hasUserAction(Enum context, Enum permission) {
        return true;
    }
    
}
