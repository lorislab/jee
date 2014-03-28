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
package org.lorislab.jel.log.service;

import org.lorislab.jel.log.config.LogServiceConfiguration;
import org.lorislab.jel.log.context.ContextLogger;
import org.lorislab.jel.log.context.impl.DefaultContextLogger;
import org.lorislab.jel.log.parameters.ClassLogParameter;
import org.lorislab.jel.log.parameters.InstanceOfLogParameter;
import org.lorislab.jel.log.parameters.LogParameter;
import org.lorislab.jel.log.parameters.impl.DefaultLogParameter;
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
    private static final Map<Class<?>, ClassLogParameter> TMP_CLASSES = new HashMap<>();
    /**
     * The instance logger parameter.
     */
    private static final List<InstanceOfLogParameter> TMP_INSTANCE_OF = new ArrayList<>();
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
        ServiceLoader<LogServiceConfiguration> list = ServiceLoader.load(LogServiceConfiguration.class);
        if (list != null) {
            Iterator<LogServiceConfiguration> iter = list.iterator();
            while (iter.hasNext()) {
                LogServiceConfiguration config = iter.next();
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
                                TMP_CLASSES.put(clazz, p);
                            }
                        }
                    }
                }

                List<InstanceOfLogParameter> iParams = config.getInstanceOfLogParameters();
                if (iParams != null) {
                    TMP_INSTANCE_OF.addAll(iParams);
                }
            }
        }

        // load the instance of log parameters from services files.
        ServiceLoader<InstanceOfLogParameter> insLogParam = ServiceLoader.load(InstanceOfLogParameter.class);
        if (insLogParam != null) {
            Iterator<InstanceOfLogParameter> iter = insLogParam.iterator();
            while (iter.hasNext()) {
                TMP_INSTANCE_OF.add(iter.next());
            }
        }

        // load the classes of log parameters from services files.
        ServiceLoader<ClassLogParameter> classLogParam = ServiceLoader.load(ClassLogParameter.class);
        if (classLogParam != null) {
            Iterator<ClassLogParameter> iter = classLogParam.iterator();
            while (iter.hasNext()) {
                ClassLogParameter p = iter.next();
                if (p.getClasses() != null) {
                    for (Class<?> clazz : p.getClasses()) {
                        TMP_CLASSES.put(clazz, p);
                    }
                }
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

            LogParameter logParam = TMP_CLASSES.get(clazz);
            if (logParam == null && !TMP_INSTANCE_OF.isEmpty()) {
                Iterator<InstanceOfLogParameter> iter = TMP_INSTANCE_OF.iterator();
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
