/*
 * Copyright 2011 Andrej Petras <andrej@ajka-andrej.com>.
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
package org.lorislab.jel.jsf.view;

import org.lorislab.jel.jpa.model.Persistent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The abstract table search view controller.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 * @param <T> the model.
 */
public abstract class AbstractTableViewController<T extends Persistent> implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = 8552218747873068051L;
    /**
     * The result list.
     */
    private List<T> result;
    /**
     * The hash map with object and table index.
     */
    private Map<Object, Integer> tmp;

    /**
     * The default constructor.
     */
    public AbstractTableViewController() {
        result = new ArrayList<>();
        tmp = new HashMap<>();
    }

    /**
     * Gets the result list.
     *
     * @return the result list.
     */
    public List<T> getResult() {
        return result;
    }

    /**
     * Search the results.
     * 
     * @throws Exception is the method fails.
     */
    public void search() throws Exception {
        tmp = new HashMap<>();
        result = doSearch();
        if (result != null) {
            int i = 0;
            for (T item : result) {
                tmp.put(item.getGuid(), i++);
            }
        }
    }

    /**
     * Deletes object from table.
     *
     * @param model the model.
     *
     * @return the removed model.
     */
    public T tableDelete(T model) {
        if (result != null && model != null) {
            Integer index = tmp.remove(model.getGuid());
            if (index != null) {
                result.remove(model);
            }
        }
        return null;
    }

    /**
     * Updates the model in the result table.
     *
     * @param model the model.
     *
     * @return the updated model.
     */
    public T tableUpdate(T model) {
        if (result != null && model != null) {
            Integer index = tmp.get(model.getGuid());
            if (index != null) {
                result.set(index, model);
            } else {
                tmp.put(model.getGuid(), result.size());
                result.add(model);
            }
        }
        return model;
    }

    /**
     * Search method for the result table.
     *
     * @return the result list.
     *
     * @throws Exception if the method fails.
     */
    protected abstract List<T> doSearch() throws Exception;
}
