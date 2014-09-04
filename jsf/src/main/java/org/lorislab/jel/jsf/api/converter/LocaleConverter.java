/*
 * Copyright 2014 lorislab.org.
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

import java.util.Locale;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * The locale converter.
 * 
 * @author Andrej Petras
 */
@FacesConverter(forClass = Locale.class)
public class LocaleConverter implements Converter {

    /**
     * {@inheritDoc }
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null) {
            return Locale.forLanguageTag(value);
        }
        return null;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
           Locale locale = (Locale) value;
           return locale.toLanguageTag();
        }
        return null;
    }
    
}
