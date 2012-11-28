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
package com.ajkaandrej.lib.base.resources.util;

/**
 * The utility class for the resource.
 * 
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
public final class ResourceUtil {

    /**
     * The default constructor.
     */
    private ResourceUtil() {
    }

    /**
     * Transforms an (enumeration) constant name into a Java property-like String with camel case
     * notation.. Concretely, it transforms the provided name name into lowercase and
     * characters following a single underscore (<code>_</code>) into uppercase again.
     * <p>
     * Example: A constant name of <code>COMPLAINT_NR</code> will be transformed to a
     * property String of <code>complaintNr</code>.
     * </p>
     *
     * @param constantName The constant name to be transformed.
     * @return The resulting String in camel case notation.
     */
    public static String convertConstantToCamelCase(final String constantName) {
        String property = constantName.replace("__", ".").toLowerCase();
        int index = property.indexOf('_');
        while (index != -1 && index < property.length() - 1) {
            String prefix = property.substring(0, index);
            String character = property.substring(index + 1, index + 2).toUpperCase();
            String postfix = property.substring(index + 2);
            property = prefix + character + postfix;
            index = property.indexOf('_');
        }
        return property;
    }

    /**
     * Returns a String that may be used as resource bundle key. It will be a
     * concatenation of the provided <code>prefix</code>, a dot (<code>.</code>)
     * and the provided <code>constantName</code> converted to camel case notation by
     * {@link #convertConstantToCamelCase(String)}.
     * <p>
     * Example: With a <code>prefix</code> of <code>error.service</code> and a
     * <code>constantName</code> of <code>NO_CONNECTION</code>, the resulting String
     * will be <code>error.service.noConnection</code>.
     * </p>
     *
     * @param prefix The prefix of the bundle key.
     * @param constantName The constant name to be converted to camel case notation.
     * Please see {@link #convertConstantToCamelCase(String)} for more information on the
     * transformation rules.
     * @return The concatenated bundle key.
     */
    public static String getBundleKey(final String prefix, final String constantName) {
        if (prefix != null && !prefix.isEmpty()) {
            return prefix.trim() + '.' + convertConstantToCamelCase(constantName);
        } else {
            return convertConstantToCamelCase(constantName);
        }
    }
}
