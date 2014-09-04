/*
 * Copyright 2012 Andrej Petras <andrej@ajka-andrej.com>.
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
package org.lorislab.jel.jsf.api.util;

import java.security.Principal;
import javax.faces.context.FacesContext;

/**
 * The faces utility.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
public final class FacesUtil {

    /**
     * Returns
     * <code>true</code> if the current user is in the role.
     *
     * @param role the role
     * @return <code>true</code> if the current user is in the role.
     */
    public static boolean isUserInRole(String role) {
        return FacesContext.getCurrentInstance().getExternalContext().isUserInRole(role);
    }

    /**
     * Gets the current user name.
     *
     * @return the current user name.
     */
    public static String getCurrentUser() {
        String result = null;
        Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        if (principal != null) {
            result = principal.getName();
        }
        return result;
    }
}
