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
package org.lorislab.jel.jsf.entity.controller;

import org.lorislab.jel.jsf.entity.controller.action.CloseAction;
import org.lorislab.jel.jsf.entity.controller.action.CreateAction;
import org.lorislab.jel.jsf.entity.controller.action.DeleteAction;
import org.lorislab.jel.jsf.entity.controller.action.EditAction;
import org.lorislab.jel.jsf.entity.controller.action.SaveAction;
import org.lorislab.jel.jsf.view.controller.AbstractViewController;

/**
 * The abstract entity view controller.
 *
 * @param <T> the entity type.
 *
 * @author Andrej Petras
 */
public abstract class AbstractEntityViewController<T> extends AbstractViewController implements EntityViewController<T> {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = 2085181065161645149L;

    /**
     * The model.
     */
    protected T model;

    /**
     * The open action.
     */
    private EditAction openAction;

    /**
     * The create action.
     */
    private CreateAction createAction;

    /**
     * The close action.
     */
    private CloseAction closeAction;

    /**
     * The save action.
     */
    private SaveAction saveAction;

    /**
     * The delete action.
     */
    private DeleteAction deleteAction;

    /**
     * The default constructor.
     */
    public AbstractEntityViewController() {
        this(null);
    }

    /**
     * The default constructor.
     *
     * @param context the context.
     */
    public AbstractEntityViewController(Enum context) {
        createAction = new CreateAction(this, context);
        closeAction = new CloseAction(this, context);
        saveAction = new SaveAction(this, context);
        deleteAction = new DeleteAction(this, context);
        openAction = new EditAction(this, context);
    }

    /**
     * Gets the model.
     *
     * @return the model.
     */
    @Override
    public T getModel() {
        return model;
    }

    /**
     * Sets the model.
     *
     * @param model the model.
     */
    protected void setModel(T model) {
        this.model = model;
    }

    /**
     * Returns <code>true</code> if the model is <code>null</code>.
     *
     * @return <code>true</code> if the model is <code>null</code>.
     */
    public boolean isEmpty() {
        return model == null;
    }
    
   /**
     * Gets the open action.
     *
     * @return the open action.
     */
    public EditAction getOpenAction() {
        return openAction;
    }

    /**
     * Gets the close action.
     *
     * @return the close action.
     */
    public CloseAction getCloseAction() {
        return closeAction;
    }

    /**
     * Gets the create action.
     *
     * @return the create action.
     */
    public CreateAction getCreateAction() {
        return createAction;
    }

    /**
     * Gets the delete action.
     *
     * @return the delete action.
     */
    public DeleteAction getDeleteAction() {
        return deleteAction;
    }

    /**
     * Gets the save action.
     *
     * @return the save action.
     */
    public SaveAction getSaveAction() {
        return saveAction;
    }    

    /**
     * {@inheritDoc }
     */
    @Override
    public Object close() throws Exception {
        return null;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Object create() throws Exception {
        return null;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Object view(String guid) throws Exception {
        return null;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Object save() throws Exception {
        return null;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Object delete() throws Exception {
        return null;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Object edit(String guid) throws Exception {
        return null;
    }
}
