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
package org.lorislab.jel.jsf.api.converter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import org.lorislab.jel.jpa.model.Persistent;

/**
 * The entity persistent converter.
 *
 * @param <T> the entity.
 *
 * @author Andrej Petras
 */
public class EntityPersistentConverter<T extends Persistent> implements Converter, Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -3163831463232772936L;

    /**
     * The map of values.
     */
    private Map<String, T> values;

    /**
     * The list of data.
     */
    private List<SelectItem> data;

    /**
     * The label callback handler.
     */
    private EntityLabelCallback callback;

    /**
     * The default constructor.
     *
     * @param callback the label callback handler.
     */
    public EntityPersistentConverter(EntityLabelCallback callback) {
        this.values = new HashMap<>(0);
        this.data = new ArrayList<>(0);
        this.callback = callback;
    }

    /**
     * Clears the data.
     */
    public void clear() {
        this.values = new HashMap<>(0);
        this.data = new ArrayList<>(0);
    }

    /**
     * Gets the list of data.
     *
     * @return the list of data.
     */
    public List<SelectItem> getData() {
        return data;
    }

    /**
     * Sets the list of data.
     *
     * @param items the list of data.
     */
    public void setData(List<T> items) {
        if (items != null) {
            data = new ArrayList<>(items.size());
            values = new HashMap<>(items.size());
            for (T item : items) {
                values.put(item.getGuid(), item);
                String label = callback.getEntityLabel(item);
                data.add(new SelectItem(item, label));
            }
        } else {
            data = new ArrayList<>(0);
            values = new HashMap<>(0);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null) {
            return values.get(value);
        }
        return null;
    }

    /**
     * {@inheritDoc }
     */    
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && value instanceof Persistent) {
            return ((T) value).getGuid();
        }
        return null;
    }

}
