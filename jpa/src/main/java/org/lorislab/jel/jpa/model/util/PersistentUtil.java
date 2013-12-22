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
package org.lorislab.jel.jpa.model.util;

import org.lorislab.jel.jpa.model.Persistent;

/**
 * The persistent utility.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
public final class PersistentUtil {

    /**
     * The default constructor.
     */
    private PersistentUtil() {
        // empty constructor
    }

    /**
     * Gets the object GUID.
     *
     * @param object the object.
     * @return the object GUID.
     */
    public static String getGuid(Persistent object) {
        String result = null;
        if (object != null) {
            result = object.getGuid();
        }
        return result;
    }

}
