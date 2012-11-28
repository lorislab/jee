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
package com.ajkaandrej.log.service;

import com.ajkaandrej.log.config.LogServiceConfiguration;
import com.ajkaandrej.log.context.ContextLogger;
import com.ajkaandrej.log.context.impl.DefaultContextLogger;
import com.ajkaandrej.log.parameters.ClassLogParameter;
import com.ajkaandrej.log.parameters.InstanceOfLogParameter;
import com.ajkaandrej.log.parameters.LogParameter;
import com.ajkaandrej.log.parameters.impl.DefaultLogParameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * The logger service.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
public final class LogService {

    /**
     * The map of class and logger parameter.
     */
    private static final Map<Class<?>, ClassLogParameter> tmpClasses = new HashMap<>();
    /**
     * The instance logger parameter.
     */
    private static final List<InstanceOfLogParameter> tmpInstanceOf = new ArrayList<>();
    /**
     * The default logger parameter.
     */
    private static LogParameter defaultParam = new DefaultLogParameter();
    /**
     * The context logger.
     */
    private static ContextLogger contextLogger = new DefaultContextLogger();

    /**
     * Static block of code.
     */
    static {
        LogServiceConfiguration config = null;
        ServiceLoader<LogServiceConfiguration> list = ServiceLoader.load(LogServiceConfiguration.class);
        if (list != null) {
            Iterator<LogServiceConfiguration> iter = list.iterator();
            if (iter.hasNext()) {
                config = iter.next();
            }
        }

        if (config != null) {

            LogParameter dp = config.getDefaultLogParameter();
            if (dp != null) {
                defaultParam = dp;
            }

            ContextLogger cn = config.getContextLogger();
            if (cn != null) {
                contextLogger = cn;
            }

            List<ClassLogParameter> cParams = config.getClassLogParameters();
            if (cParams != null) {
                for (ClassLogParameter p : cParams) {
                    if (p.getClasses() != null) {
                        for (Class<?> clazz : p.getClasses()) {
                            tmpClasses.put(clazz, p);
                        }
                    }
                }
            }

            List<InstanceOfLogParameter> iParams = config.getInstanceOfLogParameters();
            if (iParams != null) {
                tmpInstanceOf.addAll(iParams);
            }
        }
    }

    /**
     * The default constructor.
     */
    private LogService() {
        // empty constructor
    }

    /**
     * Gets the method parameter value.
     *
     * @param parameter the method parameter.
     * @return the value from the parameter.
     */
    private static Object getParameterValue(Object parameter) {
        Object result = null;
        if (parameter != null) {
            Class clazz = parameter.getClass();

            LogParameter logParam = tmpClasses.get(clazz);
            if (logParam == null && !tmpInstanceOf.isEmpty()) {
                Iterator<InstanceOfLogParameter> iter = tmpInstanceOf.iterator();
                while (iter.hasNext() && logParam == null) {
                    InstanceOfLogParameter item = iter.next();
                    if (item.instanceOfClasses(parameter)) {
                        logParam = item;
                    }
                }
            }

            if (logParam != null) {
                result = logParam.getObject(parameter);
                if (!logParam.isResult()) {
                    result = getParameterValue(result);
                }
            } else {
                result = defaultParam.getObject(parameter);
            }
        }
        return result;
    }

    /**
     * Gets the context logger.
     *
     * @return the context logger.
     */
    public static ContextLogger getContextLogger() {
        return contextLogger;
    }

    /**
     * Gets the list of string corresponding to the list of parameters.
     *
     * @param parameters the list of parameters.
     * @return the list of string corresponding to the list of parameters.
     */
    public static List<String> getValues(Object[] parameters) {
        List<String> result = new ArrayList<>();
        if (parameters != null) {
            for (Object parameter : parameters) {
                result.add(getValue(parameter));
            }
        }
        return result;
    }

    /**
     * Gets the string corresponding to the parameter.
     *
     * @param parameter the method parameter.
     * @return the string corresponding to the parameter.
     */
    public static String getValue(Object parameter) {
        Object value = getParameterValue(parameter);
        return "" + value;
    }
}
