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
package org.lorislab.jel.jsf.entity.model;

import java.io.Serializable;
import org.lorislab.jel.jpa.model.Persistent;

/**
 * The entity select item.
 *
 * @author Andrej_Petras
 * @param <T> the entity type.
 */
public class EntitySelectItem<T extends Persistent> implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = 2603014614581275490L;

    /**
     * The entity.
     */
    private final T data;

    /**
     * The selected flag.
     */
    private boolean selected;

    /**
     * The default constructor.
     *
     * @param data the data.
     * @param selected the selected flag.
     */
    public EntitySelectItem(T data, boolean selected) {
        this.data = data;
        this.selected = selected;
    }
    
    /**
     * Gets the role.
     *
     * @return the role.
     */
    public T getData() {
        return data;
    }
   
    /**
     * Gets the selected flag.
     *
     * @return the selected flag.
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets the selected flag.
     *
     * @param selected the selected flag.
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
