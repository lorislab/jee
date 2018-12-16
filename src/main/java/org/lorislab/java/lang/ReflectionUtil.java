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
package org.lorislab.java.lang;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The simple reflection utility.
 *
 * @author Andrej Petras
 */
public final class ReflectionUtil {

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(ReflectionUtil.class.getName());

    /**
     * The default constructor.
     */
    private ReflectionUtil() {
        // empty constructor
    }

    /**
     * Finds all fields of the class.
     *
     * @param objectClazz the object class.
     * @return the list of fields.
     */
    private static List<Field> findAllFields(Class<?> objectClazz) {
        List<Field> result = new ArrayList<>();
        Class<?> clazz = objectClazz;
        do {
            Field[] f = clazz.getDeclaredFields();
            for (Field field : f) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    result.add(field);
                }
            }
            clazz = clazz.getSuperclass();
        } while (clazz != null && !clazz.equals(Object.class));
        return result;
    }

    /**
     * Writes the object field values to the string.
     *
     * @param object the object.
     * @return object field values as a string.
     */
    public static String toString(Object object) {
        return toString(object, -1);
    }

    /**
     * Writes the object field values to the string.
     *
     * @param object the object.
     * @param collectionSize special log for collection wit more items that
     * {@code collectionSize}
     * @return object field values as a string.
     */
    public static String toString(Object object, int collectionSize) {
        StringBuilder sb = new StringBuilder();

        if (object != null) {
            //sb.append(object.getClass().getName()).append("@").append(Integer.toHexString(object.hashCode()));
            sb.append(object.getClass().getSimpleName());
            List<Field> fields = findAllFields(object.getClass());
            if (fields != null && !fields.isEmpty()) {
                sb.append('[');
                boolean first = false;
                for (Field field : fields) {
                    Object value;
                    boolean accessible = field.canAccess(object);
                    try {
                        try {
                            field.setAccessible(true);
                            value = field.get(object);
                        } finally {
                            field.setAccessible(accessible);
                        }

                        if (first) {
                            sb.append(',');
                        }
                        if (value != null && collectionSize != -1 && Collection.class.isAssignableFrom(field.getType())) {
                            Collection c = (Collection) value;
                            if (c.size() > collectionSize) {
                                value =  field.getType().getSimpleName() + "(" + c.size() + ")";
                            }
                        }
                        sb.append(field.getName()).append('=').append(value);
                        first = true;
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        LOGGER.log(Level.SEVERE, "Error get the field {0} value", field);
                    }
                }
                sb.append(']');
            }
        }

        return sb.toString();
    }
}
