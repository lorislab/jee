/*
 * Copyright 2014 Andrej Petras.
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

import org.lorislab.jel.jsf.view.AbstractSearchViewController;

/**
 *
 * @author Andrej Petras
 */
public class ResetAction<T extends AbstractSearchViewController> extends AbstractViewControllerAction<T> {
    
    private static final long serialVersionUID = -3670301925390375306L;

    public ResetAction(T parent) {
        super(parent);
    }

    @Override
    protected Object doExecute() throws Exception {
        getParent().reset();
        return null;
    }
    
    
}
