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

package org.lorislab.jel.jsf.entity.controller.action;

import org.lorislab.jel.jsf.entity.common.EntityPermission;
import org.lorislab.jel.jsf.entity.controller.DeleteViewController;
import org.lorislab.jel.jsf.view.controller.action.AbstractAction;

/**
 * The delete action.
 * 
 * @param <T> the delete view controller.
 * 
 * @author Andrej Petras
 */
public class DeleteAction<T extends DeleteViewController> extends AbstractAction<T> {
    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -401771961900364439L;
        
    /**
     * The default constructor.
     * @param parent the parent view controller.
     * @param context the context.
     */
    public DeleteAction(T parent, Enum context) {
        super(parent, context, EntityPermission.CREATE);
    } 
    
    /**
     * {@inheritDoc }
     */
    @Override
    protected Object doExecute() throws Exception { 
        return getParent().delete();
    }       
}
