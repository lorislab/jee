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
package org.lorislab.jel.base.resources;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.lorislab.jel.base.exception.ServiceException;

/**
 * This class load the resources or the resource key from the class-path.
 *
 * @author Andrej Petras <andrej@lorislab.org>
 */
public final class ResourceManager {

    /**
     * The default constructor.
     */
    private ResourceManager() {
        // empty private constructor
    }

    public static String getMessage(ServiceException exception, Locale locale) {
        List<Serializable> args = exception.getParameters();
        Serializable[] tmp = args.toArray(new Serializable[args.size()]);
        return getMessage(exception.getKey(), locale, tmp);
    }
    
    /**
     * Loads the bundle from class-path.
     *
     * @param bundleName the name of bundle.
     * @param locale the local for the bundle file.
     * @param loader the loader of class.
     * @return the resource bundle.
     */
    private static ResourceBundle lookupBundle(final String bundleName, final Locale locale, final ClassLoader loader) {
        ResourceBundle bundle;
        if (loader != null && locale != null) {
            bundle = ResourceBundle.getBundle(bundleName, locale, loader);
        } else if (locale != null) {
            bundle = ResourceBundle.getBundle(bundleName, locale);
        } else {
            bundle = ResourceBundle.getBundle(bundleName);
        }
        return bundle;
    }

    /**
     * Gets the text for the resource key from the resource file specified by
     * locale.
     *
     * @param key the key of resource.
     * @param locale the locale for text.
     * @return the text from the resource.
     */
    public static String getString(final Enum<?> key, final Locale locale) {
        return getString(key, locale, key.getClass().getClassLoader());
    }

    /**
     * Gets the text for the resource key from the resource file specified by
     * locale and class loader.
     *
     * @param key the key of resource.
     * @param locale the locale for text.
     * @param classLoader the class loader of class.
     * @return the text from the resource.
     */
    public static String getString(final Enum<?> key, final Locale locale, final ClassLoader classLoader) {
        try {
            ResourceBundle bundle = lookupBundle(key.getClass().getName(), locale, classLoader);
            return bundle.getString(key.name());            
        } catch (MissingResourceException ex) {
            return key.toString();
        }
    }

    /**
     * Gets the message for the resource key specified by locale and arguments.
     *
     * @param key the key of resource.
     * @param locale the locale of resource (text).
     * @param arguments the arguments for the message.
     * @return the message of the resource key.
     */
    public static String getMessage(final Enum<?> key, final Locale locale, final Serializable... arguments) {
        return getMessage(key, locale, null, arguments);
    }

    /**
     * Gets the message for the resource key specified by locale and arguments.
     *
     * @param key the key of resource.
     * @param locale the locale of resource (text).
     * @param arguments the arguments for the message.
     * @param loader the loader of class.
     * @return the message of the resource key.
     */
    public static String getMessage(final Enum<?> key, final Locale locale, final ClassLoader loader, final Serializable[] arguments) {
        String result;
        try {
            ClassLoader classLoader = loader;
            if (classLoader == null) {
                classLoader = key.getClass().getClassLoader();
            }

            ResourceBundle bundle = lookupBundle(key.getClass().getName(), locale, classLoader);
            String value = bundle.getString(key.name());
        
            if (arguments != null) {
                MessageFormat msgFormat;
                if (locale != null) {
                    msgFormat = new MessageFormat(value, locale);
                } else {
                    msgFormat = new MessageFormat(value);
                }
                StringBuffer bf = msgFormat.format(arguments, new StringBuffer(), null);
                result = bf.toString();
            } else {
                result = value;
            }
        } catch (MissingResourceException e) {
            return key.toString();
        }
        return result;
    }

}
