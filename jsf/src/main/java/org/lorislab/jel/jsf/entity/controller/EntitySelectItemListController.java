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
package org.lorislab.jel.jsf.entity.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.lorislab.jel.jpa.model.Persistent;
import org.lorislab.jel.jsf.entity.model.EntitySelectItem;

/**
 * The entity select item list controller.
 *
 * @author Andrej_Petras
 * @param <T> the item type.
 */
public class EntitySelectItemListController<T extends Persistent> implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = 7684599083430372713L;

    /**
     * The items.
     */
    private List<EntitySelectItem<T>> data = new ArrayList<>();

    /**
     * Gets the data.
     *
     * @return the data.
     */
    public List<EntitySelectItem<T>> getData() {
        return data;
    }

    /**
     * Loads the data.
     *
     * @param items the list of items.
     */
    public void loadData(List<T> items) {
        loadData(items, null);
    }

    /**
     * Loads the data.
     *
     * @param items the list of items.
     * @param keys the set of keys.
     */
    public void loadData(List<T> items, Set<String> keys) {
        data = new ArrayList<>();
        if (keys == null) {
            keys = new HashSet<>();
        }
        if (items != null) {
            for (T item : items) {
                data.add(new EntitySelectItem<>(item, keys.contains(getKey(item))));
            }
        }
    }

    /**
     * Gets the selected keys.
     *
     * @return the selected keys.
     */
    public Set<String> getKeys() {
        Set<String> result = new HashSet<>();
        for (EntitySelectItem<T> item : data) {
            if (item.isSelected()) {
                result.add(getKey(item.getData()));
            }
        }
        return result;
    }

    /**
     * Gets the key for the model.
     *
     * @param model the model.
     * @return the corresponding key.
     */
    protected String getKey(T model) {
        return model.getGuid();
    }
}
