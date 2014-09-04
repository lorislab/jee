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
package org.lorislab.jel.jsf.api.listener;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitHint;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

/**
 * The reset input AJAX action listener.
 *
 * @author Andrej Petras
 */
public class ResetInputAjaxActionListener implements ActionListener {

    /**
     * The set of visit hints.
     */
    private static final Set<VisitHint> VISIT_HINTS = EnumSet.of(VisitHint.SKIP_TRANSIENT, VisitHint.SKIP_UNRENDERED);

    /**
     * {@inheritDoc }
     */
    @Override
    public void processAction(ActionEvent event) throws AbortProcessingException {
        FacesContext context = FacesContext.getCurrentInstance();
        PartialViewContext partialViewContext = context.getPartialViewContext();
        if (partialViewContext.isAjaxRequest()) {
            Collection<String> renderIds = partialViewContext.getRenderIds();
            Collection<String> executeIds = partialViewContext.getExecuteIds();
            if (!renderIds.isEmpty() && !renderIds.containsAll(executeIds)) {
                reset(VisitContext.createVisitContext(context, renderIds, VISIT_HINTS), context.getViewRoot(), executeIds);
            }
        }
    }

    /**
     * Resets the components.
     *
     * @param context the context.
     * @param component the component.
     * @param executeIds the set of execute ID's.
     */
    private void reset(VisitContext context, final UIComponent component, final Collection<String> executeIds) {
        component.visitTree(context, new VisitCallback() {
            @Override
            public VisitResult visit(VisitContext context, UIComponent target) {
                if (executeIds.contains(target.getClientId(context.getFacesContext()))) {
                    return VisitResult.REJECT;
                }
                if (target instanceof EditableValueHolder) {
                    ((EditableValueHolder) target).resetValue();
                } else if (context.getIdsToVisit() != VisitContext.ALL_IDS) {
                    reset(VisitContext.createVisitContext(context.getFacesContext(), null, context.getHints()), target, executeIds);
                }
                return VisitResult.ACCEPT;
            }
        });
    }
}
