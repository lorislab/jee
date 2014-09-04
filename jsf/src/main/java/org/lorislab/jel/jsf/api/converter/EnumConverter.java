/*
 * Copyright 2014 Andrej Petras.
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
package org.lorislab.jel.jsf.api.converter;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * The resource key converter.
 *
 * @author Andrej Petras
 */
@FacesConverter(value = "org.lorislab.jel.jsf.converter.EnumConverter")
public class EnumConverter implements Converter {

    /**
     * The logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(EnumConverter.class.getName());

    /**
     * The separator.
     */
    private static final String SEPARATOR = "#";

    /**
     * {@inheritDoc }
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Object result = null;
        if (value != null) {
            String[] items = value.split(SEPARATOR);
            try {
                Class clazz = Class.forName(items[0]);
                result = Enum.valueOf(clazz, items[1]);
            } catch (ClassNotFoundException ex) {
                LOGGER.log(Level.WARNING, "Error reading the resource key from value [{0}]", value);
                LOGGER.log(Level.FINEST, "Error converting the reosource key", ex);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String result = null;
        if (value != null) {
            if (value instanceof Enum) {
                Enum e = (Enum) value;
                result = e.getClass().getName() + "#" + e.name();
            } else {
                result = value.toString();
            }            
        }
        return result;
    }

}
