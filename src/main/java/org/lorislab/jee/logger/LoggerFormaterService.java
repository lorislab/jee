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
package org.lorislab.jee.logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import org.lorislab.jee.annotation.LoggerService;

/**
 * The logger format service.
 *
 * @author Andrej Petras
 */
@ApplicationScoped
@LoggerService(log = false)
public class LoggerFormaterService {

    private final Map<Class, LoggerParameter> classes = new HashMap<>();

    private final Map<Class, LoggerParameter> assignableFrom = new HashMap<>();

    @Inject
    @Any
    private Instance<LoggerParameter> parameters;

    @PostConstruct
    public void init() {
        initParameters(parameters);
    }

    private void initParameters(Iterable<LoggerParameter> parameters) {
        if (parameters != null) {
            for (LoggerParameter parameter : parameters) {

                Class[] tmp = parameter.getClasses();
                if (tmp != null) {
                    for (Class clazz : tmp) {
                        LoggerParameter p = classes.get(clazz);
                        if (p != null) {
                            if (p.getPriority() < parameter.getPriority()) {
                                classes.put(clazz, parameter);
                            }
                        } else {
                            classes.put(clazz, parameter);
                        }
                    }
                }

                Class clazz = parameter.getAssignableFrom();
                if (clazz != null) {
                    LoggerParameter p = assignableFrom.get(clazz);
                    if (p != null) {
                        if (p.getPriority() < parameter.getPriority()) {
                            assignableFrom.put(clazz, parameter);
                        }
                    } else {
                        assignableFrom.put(clazz, parameter);
                    }
                }
            }
        }
    }

    /**
     * Gets the method parameter value.
     *
     * @param parameter the method parameter.
     * @return the value from the parameter.
     */
    private Object getParameterValue(Object parameter) {
        Object result = parameter;
        if (parameter != null) {
            Class clazz = parameter.getClass();
            LoggerParameter tmp = classes.get(clazz);

            if (tmp == null) {
                Iterator<Class> iter = assignableFrom.keySet().iterator();
                while (iter.hasNext() && tmp == null) {
                    Class<?> key = iter.next();
                    if (key.isAssignableFrom(clazz)) {
                        tmp = assignableFrom.get(key);
                    }
                }
            }

            if (tmp != null) {
                result = tmp.getObject(parameter);
                if (!(result instanceof String)) {
                    result = getParameterValue(result);
                }
            }
        }
        return result;
    }

    /**
     * Gets the list of string corresponding to the list of parameters.
     *
     * @param parameters the list of parameters.
     * @return the list of string corresponding to the list of parameters.
     */
    public List<String> getValues(Object[] parameters) {
        List<String> result = new ArrayList<>();
        if (parameters != null) {
            for (Object parameter : parameters) {
                result.add(getValue(parameter));
            }
        }
        return result;
    }

    /**
     * Gets the list of string corresponding to the list of parameters.
     *
     * @param parameters the list of parameters.
     * @return the list of string corresponding to the list of parameters.
     */
    public String getValuesString(Object[] parameters) {
        if (parameters != null && parameters.length > 0) {
            StringBuilder sb = new StringBuilder();
            int index = 0;
            sb.append(getValue(parameters[index++]));
            for (; index < parameters.length; index++) {
                sb.append(',');
                sb.append(getValue(parameters[index]));
            }
            return sb.toString();
        }
        return "";
    }

    /**
     * Gets the string corresponding to the parameter.
     *
     * @param parameter the method parameter.
     * @return the string corresponding to the parameter.
     */
    public String getValue(Object parameter) {
        Object value = getParameterValue(parameter);
        return "" + value;
    }

}
