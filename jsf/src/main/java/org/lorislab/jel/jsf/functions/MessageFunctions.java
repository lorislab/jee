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
package org.lorislab.jel.jsf.functions;

import java.util.List;
import javax.faces.context.FacesContext;

/**
 * The message functions.
 *
 * @author Andrej Petras
 */
public final class MessageFunctions {

    /**
     * The default constructor.
     */
    private MessageFunctions() {
        // empty constructor
    }

    /**
     * Returns {@code true} if the is not message for parent and ID.
     *
     * @param parent the parent ID.
     * @param id the component ID.
     * @return {@code true} if the is not message for parent and ID.
     */
    public static boolean isNotMessage(String parent, String id) {
        return !isMessage(parent, id);
    }

    /**
     * Returns {@code true} if the is message for parent and ID.
     *
     * @param parent the parent ID.
     * @param id the component ID.
     * @return {@code true} if the is message for parent and ID.
     */
    public static boolean isMessage(String parent, String id) {
        List tmp = FacesContext.getCurrentInstance().getMessageList(parent + id);
        return tmp != null && tmp.size() > 0;
    }

    /**
     * Returns list of messages for the component.
     *
     * @param clientId the client ID.
     * @return the list of messages.
     */
    public static List getMessages(String clientId) {
        return FacesContext.getCurrentInstance().getMessageList(clientId);
    }

    /**
     * Returns list of messages for the component in the parent.
     *
     * @param parent the parent ID.
     * @param id the component ID.
     * @return the list of messages.
     */
    public static List getMessages(String parent, String id) {
        return FacesContext.getCurrentInstance().getMessageList(parent + id);
    }

    /**
     * Gets the list of global messages.
     *
     * @return the list of global messages.
     */
    public static List getMessages() {
        return FacesContext.getCurrentInstance().getMessageList(null);
    }
}
