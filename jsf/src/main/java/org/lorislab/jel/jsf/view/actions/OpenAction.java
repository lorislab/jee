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

package org.lorislab.jel.jsf.view.actions;

import javax.faces.context.FacesContext;
import org.lorislab.jel.jsf.view.OpenViewController;

/**
 * The open action.
 * 
 * @author Andrej Petras
 * @param <T> the open view controller.
 */
public class OpenAction<T extends OpenViewController> extends AbstractViewControllerAction<T> {
    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -401771961900364439L;
    /**
     * The GUID parameter.
     */
    private String guid;
        
    /**
     * The default constructor.
     * @param parent the parent view controller.
     */
    public OpenAction(T parent) {
        super(parent);
    }

    /**
     * Sets the GUID.
     * @param guid the GUID.
     */
    public void setGuid(String guid) {
        this.guid = guid;
    }    
    
    /**
     * {@inheritDoc }
     */
    @Override
    protected Object doExecute() throws Exception {
        String tmp = guid;
        guid = null;
        if (tmp == null) {
            tmp = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("guid");
        }        
        return getParent().open(tmp);
    }       
}
