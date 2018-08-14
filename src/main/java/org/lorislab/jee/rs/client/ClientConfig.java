/*
 * Copyright 2016 Andrej Petras.
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
package org.lorislab.jee.rs.client;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andrej Petras
 */
public class ClientConfig {
    
    private String server;

    private boolean allowAllHttps = true;
    
    private boolean defaultLogInterceptor = true;
    
    private final List<Object> components = new ArrayList<>();
    
    private final List<Class> componentClasses = new ArrayList<>();

    public ClientConfig() {
    }

    public ClientConfig(String server) {
        this.server = server;
    }
    
    public void setServer(String server) {
        this.server = server;
    }
    
    public String getServer() {
        return server;
    }

    public void setAllowAllHttps(boolean allowAllHttps) {
        this.allowAllHttps = allowAllHttps;
    }
    
    
    public boolean isAllowAllHttps() {
        return allowAllHttps;
    }

    public void setDefaultLogInterceptor(boolean defaultLogInterceptor) {
        this.defaultLogInterceptor = defaultLogInterceptor;
    }

    
    public boolean isDefaultLogInterceptor() {
        return defaultLogInterceptor;
    }

    public List<Object> getComponents() {
        return components;
    }
    
    public void addComponent(Object component) {
        components.add(component);
    }

    public List<Class> getComponentClasses() {
        return componentClasses;
    }
    
    public void addComponentClass(Class componentClass) {
        componentClasses.add(componentClass);
    }
}
