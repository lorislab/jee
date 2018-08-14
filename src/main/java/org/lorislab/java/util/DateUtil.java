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
package org.lorislab.java.util;

import java.util.Calendar;
import java.util.Date;

/**
 * The date utility class.
 * 
 * @author Andrej Petras
 */
public final class DateUtil {
    
    /**
     * Removes the value from attribute for current date.
     * @param attribute the date attribute.
     * @param value the value.
     * @return the corresponding date.
     */
    public static Date remove(int attribute, int value) {
        return operation(attribute, value, -1);
    }
    
    /**
     * Adds the value from attribute for current date.
     * @param attribute the date attribute.
     * @param value the value.
     * @return the corresponding date.
     */
    public static Date add(int attribute, int value) {
        return operation(attribute, value, +1);
    }    
    
    /**
     * Date operation for current date.
     * @param attribute the date attribute.
     * @param value the date value.
     * @param operator the operation.
     * @return the corresponding date.
     */
    private static Date operation(int attribute, int value, int operator) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(attribute, operator * value);        
        return calendar.getTime();
    }
}
