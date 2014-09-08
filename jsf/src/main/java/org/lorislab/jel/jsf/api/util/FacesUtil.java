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
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 * The faces utility.
 *
 * @author Andrej Petras
 */
public final class FacesUtil {

    /**
     * The default constructor.
     */
    private FacesUtil() {
        // empty constructor
    }

    
    /**
     * Returns <code>true</code> if the current user is in the role.
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

    /**
     * Gets the bean manager instance.
     * @return the bean manager instance.
     */
    public static BeanManager getBeanManager() {
        return (BeanManager) ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getAttribute("javax.enterprise.inject.spi.BeanManager");
    }
}
