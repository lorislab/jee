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
package com.ajkaandrej.lib.base.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The properties loader.
 * 
 * A better strategy would be to use techniques shown in
 * http://www.javaworld.com/javaworld/javaqa/2003-06/01-qa-0606-load.html
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
public final class PropertiesLoader {

    /** The logger for this class. */
    private static final Logger LOGGER = Logger.getLogger(PropertiesLoader.class.getName());

    /**
     * Suffix for properties files.
     */
    private static final String SUFFIX = ".properties";

    /**
     * Private constructor for properties loader.
     */
    private PropertiesLoader() {
    }

    /**
     * Load the properties file.
     * 
     * @param name the properties file name.
     * @return the loaded proeprties.
     */
    public static Properties loadProperties(final String name) {
        return loadProperties(name, PropertiesLoader.class.getClassLoader());
    }

    /**
     * Looks up a resource named 'name' in the classpath. The resource must map to a file
     * with .properties extention. The name is assumed to be absolute and can use either
     * "/" or "." for package segment separation with an optional leading "/" and optional
     * ".properties" suffix. Thus, the following names refer to the same resource:
     *
     * <pre>
     * some.pkg.Resource
     * some.pkg.Resource.properties
     * some/pkg/Resource
     * some/pkg/Resource.properties
     * /some/pkg/Resource
     * /some/pkg/Resource.properties
     * </pre>
     *
     * @param propertiesName the properties file name.
     * @param propertiesLoader the class loader.
     * @return the loaded properties object.
     */
    public static Properties loadProperties(final String propertiesName, final ClassLoader propertiesLoader) {
        if (propertiesName == null) {
            LOGGER.log(Level.SEVERE, "Parameter name for properties loader is null.");
            return null;
        }

        String name = propertiesName;
        ClassLoader loader = propertiesLoader;

        if (!name.isEmpty() && name.charAt(0) == '/') {
            name = name.substring(1);
        }

        name = name.replace(".", "/");

        if (!name.endsWith(PropertiesLoader.SUFFIX)) {
            name = name.concat(PropertiesLoader.SUFFIX);
        }

        Properties result = null;
        InputStream input = null;

        try {
            if (loader == null) {
                loader = ClassLoader.getSystemClassLoader();
            }

            input = loader.getResourceAsStream(name);
            if (input != null) {
                result = new Properties();
                result.load(input);
            }
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error initializing properties", exception);
            result = null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, "Error by closing the input stream", ex);
                }
            }
        }

        if (result == null) {
            throw new IllegalArgumentException("Could not load properties [" + name + "]");
        }
        return result;
    }
}
