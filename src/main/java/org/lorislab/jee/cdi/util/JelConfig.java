/*
 * Copyright 2018 andrej.
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
package org.lorislab.jee.cdi.util;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import org.lorislab.jee.cdi.CdiInterceptorBindingExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author andrej
 */
public class JelConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(JelConfig.class);
    
    public static final String CONFIG_FILE = "jel.properties";

    public static Properties loadConfig() {
        return loadConfig(JelConfig.class);
    }

    public static Properties loadConfig(Class clazz) {
        Properties result = new Properties(System.getProperties());
        URL url = clazz.getResource(CONFIG_FILE);
        if (url != null) {
            try (InputStream in = url.openStream()) {
                result.load(in);
            } catch (Exception ex) {
                LOGGER.error("Error loading the " + CONFIG_FILE, ex);
            }
        }
        return result;
    }
    
    public static String getProperty(String propertyName, String propertyDefault) {
        return getProperty(propertyName, propertyDefault, JelConfig.class);
    }
    
    public static String getProperty(String propertyName, String propertyDefault, Class clazz) {
        Properties prop = JelConfig.loadConfig(CdiInterceptorBindingExtension.class);
        return prop.getProperty(propertyName, propertyDefault);
    }
    
    public static boolean getBooleanProperty(String propertyName, Boolean propertyDefault) {
        return getBooleanProperty(propertyName, propertyDefault, JelConfig.class);
    }
    
    public static boolean getBooleanProperty(String propertyName, Boolean propertyDefault, Class clazz) {
        Properties prop = JelConfig.loadConfig(CdiInterceptorBindingExtension.class);
        return Boolean.valueOf(prop.getProperty(propertyName, "" + propertyDefault));
    }   
    
    public static boolean getBooleanProperty(String propertyName, Boolean propertyDefault, Properties prop) {
        return Boolean.valueOf(prop.getProperty(propertyName, "" + propertyDefault));
    }     
}
