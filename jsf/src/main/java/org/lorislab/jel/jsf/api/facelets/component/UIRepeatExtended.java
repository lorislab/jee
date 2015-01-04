/*
 * Copyright 2015 lorislab.org.
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
package org.lorislab.jel.jsf.api.facelets.component;

import com.sun.faces.facelets.component.UIRepeat;
import java.util.Collection;
import javax.faces.model.CollectionDataModel;

/**
 * The extend UIRepeat faces component.
 * Add support for the collection.
 * 
 * @author Andrej_Petras
 */
public class UIRepeatExtended extends UIRepeat {

    /**
     * {@inheritDoc }
     * 
     * @return if the object is a instance of the {@code java.util.Collection} return {@code javax.faces.model.CollectionDataModel}
     * 
     * @see com.sun.faces.facelets.component.UIRepeat#getValue() 
     * @see com.sun.faces.facelets.component.UIRepeat#getDataModel() 
     */
    @Override
    public Object getValue() {
        Object object = super.getValue();
        if (object != null) {
            if (object instanceof Collection) {
                object = new CollectionDataModel((Collection)object);
            }
        }
        return object;
    }
    
}
