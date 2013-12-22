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
package org.lorislab.jel.jsf.util;

import org.lorislab.jel.base.exception.AbstractSystemException;
import org.lorislab.jel.base.resources.ResourceManager;
import org.lorislab.jel.base.resources.model.ResourceMessage;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
 
/**
 * The faces resource utility class.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
public final class FacesResourceUtil {

    /**
     * The private constructor.
     */
    private FacesResourceUtil() {
        // empty constructor
    }

    /**
     * Gets the current locale.
     *
     * @return the current locale.
     */
    public static Locale getCurrentLocale() {
        return FacesContext.getCurrentInstance().getViewRoot().getLocale();
    }

    /**
     * Adds the message of an exception to the current Faces context in severity
     * <code>FacesMessage.SEVERITY_ERROR</code>.
     *
     * @param exception the exception.
     */
    public static void handleExceptionMessage(final Exception exception) {
        String message = null;
        String description = null;

        if (exception instanceof AbstractSystemException) {
            AbstractSystemException systemException = (AbstractSystemException) exception;
            ResourceMessage resource = systemException.getResourceMessage();

            if (resource != null) {
                message = ResourceManager.getMessage(resource, getCurrentLocale());
            }
        }

        if (message == null) {
            message = exception.getMessage();
        }

        addFacesMessage(null, FacesMessage.SEVERITY_ERROR, message, description);
    }

    /**
     * Looks up a string resource in the associated resource bundle.
     *
     * @param key the resource key.
     * @return the string value for the resource key.
     */
    public static String getMessage(final Enum<?> key) {
        Locale locale = getCurrentLocale();
        ClassLoader loader = key.getClass().getClassLoader();
        return ResourceManager.getString(key, locale, loader);
    }

    /**
     * Looks up a message resource in the associated resource bundle with the locale
     * provided by the view root of the current.
     *
     * @param key the resource key.
     * @param arguments the message arguments.
     * @return the look-up message string.
     */
    public static String getMessage(final Enum<?> key, final Object... arguments) {
        Locale locale = getCurrentLocale();
        ClassLoader loader = key.getClass().getClassLoader();

        if (arguments == null || arguments.length == 0) {
            return ResourceManager.getMessage(key, locale, loader);
        } else {
            return ResourceManager.getMessage(key, locale, loader, arguments);
        }
    }

    /**
     * Looks up a message resource and wraps it in a new <code>FacesMessage</code>
     * instance.
     *
     * @param severity a severity of message.
     * @param key the resource key.
     * @param arguments the message arguments
     * @return a <code>FacesMessage</code> with message string.
     */
    public static FacesMessage getFacesMessage(final Severity severity, final Enum<?> key,
            final Object... arguments) {
        Locale locale = getCurrentLocale();
        ClassLoader loader = key.getClass().getClassLoader();

        if (arguments == null || arguments.length == 0) {
            String str = ResourceManager.getMessage(key, locale, loader);
            return new FacesMessage(severity, str, "");
        } else {
            String str = ResourceManager.getMessage(key, locale, loader, arguments);
            return new FacesMessage(severity, str, "");
        }
    }

    /**
     * Looks up a message resource and wraps it in a new <code>FacesMessage</code>
     * instance with a severity of <code>SEVERITY_INFO</code>.
     *
     * @param key the resource key.
     * @param arguments the message arguments
     * @return a <code>FacesMessage</code> with message string.
     */
    public static FacesMessage getFacesMessage(final Enum<?> key, final Object... arguments) {
        return getFacesMessage(FacesMessage.SEVERITY_INFO, key, arguments);
    }

    /**
     * Adds a faces error message associated on the resource key.
     *
     * @param resourceKey the resource key.
     */
    public static void addFacesErrorMessage(final Enum<?> resourceKey) {
        addFacesMessage(null, FacesMessage.SEVERITY_ERROR, resourceKey);
    }

    /**
     * Adds a faces error message associated on the resource key with message arguments.
     *
     * @param resourceKey the resource key.
     * @param arguments the list of arguments.
     */
    public static void addFacesErrorMessage(final Enum<?> resourceKey, final String... arguments) {
        addFacesMessage(null, FacesMessage.SEVERITY_ERROR, resourceKey, (Object[]) arguments);
    }

    /**
     * Adds a faces error message to the clientId associated on the resource key.
     *
     * @param clientId the client id.
     * @param resourceKey the resource key.
     */
    public static void addFacesErrorMessage(final String clientId, final Enum<?> resourceKey) {
        addFacesMessage(clientId, FacesMessage.SEVERITY_ERROR, resourceKey);
    }

    /**
     * Adds a faces info message associated on the resource key.
     *
     * @param resourceKey the resource key.
     */
    public static void addFacesInfoMessage(final Enum<?> resourceKey) {
        addFacesMessage(null, FacesMessage.SEVERITY_INFO, resourceKey);
    }

    /**
     * Adds a faces info message to the clientId associated on the resource key.
     *
     * @param clientId the client id.
     * @param resourceKey the resource key.
     */
    public static void addFacesInfoMessage(final String clientId, final Enum<?> resourceKey) {
        addFacesMessage(clientId, FacesMessage.SEVERITY_INFO, resourceKey);
    }

    /**
     * Adds a faces info message associated on the resource key with message arguments.
     *
     * @param resourceKey the resource key.
     * @param arguments the list of arguments.
     */
    public static void addFacesInfoMessage(final Enum<?> resourceKey, final String... arguments) {
        addFacesMessage(null, FacesMessage.SEVERITY_INFO, resourceKey, (Object[]) arguments);
    }

    /**
     * Adds a  faces message to the clientId with faces severity associated on the
     * resource key.
     *
     * @param clientId the client id.
     * @param severity the faces severity.
     * @param resourceKey the resource key.
     */
    public static void addFacesMessage(final String clientId, final Severity severity,
            final Enum<?> resourceKey) {
        addFacesMessage(clientId, severity, resourceKey, (Object[]) null);
    }

    /**
     * Adds a  faces message to the clientId with faces severity associated on the
     * resource key with message arguments.
     *
     * @param clientId the client id.
     * @param severity the faces severity.
     * @param resourceKey the resource key.
     * @param arguments the list of arguments.
     */
    public static void addFacesMessage(final String clientId, final Severity severity,
            final Enum<?> resourceKey, final Object... arguments) {
            FacesMessage message = getFacesMessage(severity, resourceKey, arguments);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(clientId, message);
    }

    /**
     * Adds a  faces message to the clientId with faces severity, localized message
     * and description.
     *
     * @param clientId the client id.
     * @param severity the faces severity.
     * @param localizedMessage the localized message.
     * @param detailedMessage the description of the message.
     */
    public static void addFacesMessage(final String clientId, final Severity severity,
            final String localizedMessage, final String detailedMessage) {
        FacesMessage fm = new FacesMessage(severity, localizedMessage, detailedMessage);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(clientId, fm);
    }
}
