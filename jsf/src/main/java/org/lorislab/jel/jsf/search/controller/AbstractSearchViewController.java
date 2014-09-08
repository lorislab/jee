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
package org.lorislab.jel.jsf.search.controller;

import java.util.ArrayList;
import java.util.List;
import org.lorislab.jel.base.criteria.AbstractSearchCriteria;
import org.lorislab.jel.jsf.api.interceptor.annotations.FacesServiceMethod;
import org.lorislab.jel.jsf.search.controller.action.ResetAction;
import org.lorislab.jel.jsf.search.controller.action.SearchAction;
import org.lorislab.jel.jsf.view.controller.AbstractViewController;

/**
 * The abstract table view controller.
 *
 * @author Andrej Petras
 * @param <T> the model.
 * @param <S> the search criteria.
 */
public abstract class AbstractSearchViewController<T, S extends AbstractSearchCriteria> extends AbstractViewController implements SearchViewController {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = 8552218747873068051L;

    /**
     * The search criteria.
     */
    private S criteria;
    /**
     * The result list.
     */
    private List<T> result;

    /**
     * The search action.
     */
    private SearchAction searchAction;

    /**
     * The reset action.
     */
    private ResetAction resetAction;

    /**
     * The default constructor.
     */
    public AbstractSearchViewController() {
        this(null);
    }

    /**
     * The default constructor.
     *
     * @param context the context.
     */
    public AbstractSearchViewController(Enum context) {
        this.searchAction = new SearchAction(this, context);
        this.resetAction = new ResetAction(this, context);
    }

    /**
     * Gets the reset action.
     *
     * @return the reset action.
     */
    public ResetAction getResetAction() {
        return resetAction;
    }

    /**
     * Gets the search action.
     *
     * @return the search action.
     */
    public SearchAction getSearchAction() {
        return searchAction;
    }

    /**
     * Sets the search criteria.
     *
     * @param criteria the search criteria.
     */
    protected void setCriteria(S criteria) {
        this.criteria = criteria;
    }

    /**
     * Gets the search criteria.
     *
     * @return the search criteria.
     */
    public S getCriteria() {
        return criteria;
    }

    /**
     * Gets the result list.
     *
     * @return the list of results.
     */
    public List<T> getResult() {
        return result;
    }

    /**
     * Search the results.
     *
     * @throws java.lang.Exception is the method fails.
     */
    @Override
    @FacesServiceMethod
    public Object search() throws Exception {
        result = doSearch();
        if (result == null) {
            result = new ArrayList<>();
        }
        return null;
    }

    /**
     * Resets the results.
     *
     * @throws java.lang.Exception if the method fails.
     */
    @Override
    public Object reset() throws Exception {
        result = null;
        criteria.reset();
        return null;
    }

    /**
     * Search method for the result table.
     *
     * @return the result list.
     *
     * @throws java.lang.Exception if the method fails.
     */
    protected abstract List<T> doSearch() throws Exception;
}
