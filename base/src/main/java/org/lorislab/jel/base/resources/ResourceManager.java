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

import org.lorislab.jel.base.resources.annotations.ResourceKey;
import org.lorislab.jel.base.resources.model.ResourceList;
import org.lorislab.jel.base.resources.model.ResourceMessage;
import org.lorislab.jel.base.resources.util.ResourceUtil;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * This class load the resources or the resource key from the class-path.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
public final class ResourceManager {

    /**
     * The default constructor.
     */
    private ResourceManager() {
        // empty private constructor
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
        ResourceBundle bundle = null;
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
        return getString(key, locale, null);
    }

    /**
     * Gets the text for the resource key from the resource file specified by
     * locale and class loader.
     *
     * @param key the key of resource.
     * @param locale the locale for text.
     * @param loader the loader of class.
     * @return the text from the resource.
     */
    public static String getString(final Enum<?> key, final Locale locale, final ClassLoader loader) {
        try {
            ClassLoader classLoader = loader;
            if (loader == null) {
                classLoader = key.getClass().getClassLoader();
            }
            return getKeyString(key, locale, classLoader);
        } catch (MissingResourceException ex) {
            return key.toString();
        }
    }

    /**
     * Converts the message to a string for the specified locale.
     *
     * @param message the resource message.
     * @param locale the locale of string.
     * @return the string of this message.
     */
    public static String getMessage(final ResourceMessage message, final Locale locale) {
        return getMessage(message.getResourceKey(), locale, message.getArguments());
    }

    /**
     * Gets the message for the resource key specified by locale and arguments.
     *
     * @param key the key of resource.
     * @param locale the locale of resource (text).
     * @param arguments the arguments for the message.
     * @return the message of the resource key.
     */
    public static String getMessage(final Enum<?> key, final Locale locale, final Object... arguments) {
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
    public static String getMessage(final Enum<?> key, final Locale locale, final ClassLoader loader, final Object... arguments) {
        String result = null;
        try {
            ClassLoader classLoader = loader;
            if (classLoader == null) {
                classLoader = key.getClass().getClassLoader();
            }

            String value = getKeyString(key, locale, classLoader);

            Object[] params = createParameters(arguments);
            if (params != null) {
                MessageFormat msgFormat;
                if (locale != null) {
                    msgFormat = new MessageFormat(value, locale);
                } else {
                    msgFormat = new MessageFormat(value);
                }
                StringBuffer bf = msgFormat.format(params, new StringBuffer(), null);
                result = bf.toString();
            } else {
                result = value;
            }
        } catch (MissingResourceException e) {
            return key.toString();
        }
        return result;
    }

    /**
     * Gets the string from the key by locale and class-loader.
     *
     * @param key the key.
     * @param locale the locale.
     * @param classLoader the class loader.
     * @return the string corresponding to the key.
     */
    private static String getKeyString(final Enum<?> key, Locale locale, ClassLoader classLoader) {
        String bundleName = key.getClass().getName();
        String keyPrefix = key.getClass().getSimpleName();
        Class<?> clazz = key.getClass();
        if (clazz.isAnnotationPresent(ResourceKey.class)) {
            ResourceKey resourceKey = clazz.getAnnotation(ResourceKey.class);
            if (resourceKey.bundleName() != null && !resourceKey.bundleName().isEmpty()) {
                bundleName = resourceKey.bundleName();
            }
            if (resourceKey.keyPrefix() != null && !resourceKey.keyPrefix().isEmpty()) {
                keyPrefix = resourceKey.keyPrefix();
            }
        }
        ResourceBundle bundle = lookupBundle(bundleName, locale, classLoader);
        String value = bundle.getString(ResourceUtil.getBundleKey(keyPrefix, key.name()));
        return value;
    }

    /**
     * Creates the list of parameters for the message.
     *
     * @param arguments the arguments.
     * @return the list of parameters.
     */
    private static Object[] createParameters(Object... arguments) {
        Object[] params = null;
        if (arguments != null && arguments.length > 0) {

            params = new Object[arguments.length];

            for (int i = 0; i < arguments.length; i++) {
                Object item = arguments[i];
                if (item instanceof ResourceList) {
                    StringBuilder sb = new StringBuilder();
                    ResourceList list = (ResourceList) item;

                    boolean first = false;
                    for (Object var : list.getValues()) {
                        if (first) {
                            sb.append(',');
                        }
                        sb.append(var);
                        first = true;
                    }
                    params[i] = sb.toString();
                } else {
                    params[i] = item;
                }
            }
        }
        return params;
    }
}
