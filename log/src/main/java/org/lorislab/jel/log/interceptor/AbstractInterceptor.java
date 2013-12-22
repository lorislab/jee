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
package org.lorislab.jel.log.interceptor;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Logger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import org.lorislab.jel.log.service.LogService;

/**
 * The abstract interceptor.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
public abstract class AbstractInterceptor implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -5329282064003891498L;
    /**
     * The time constant.
     */
    private static final double TIME_CONST = 1000000000d;

    /**
     * The default user.
     */
    protected static final String DEFAULT_USER = "anonymous";
    
    /**
     * Gets the user name.
     *
     * @return the user name.
     */
    protected abstract String getUser();

    /**
     * Gets class name for the class.
     *
     * @param clazz the class name.
     * @return the string corresponding to the class.
     */
    protected abstract String getClassName(Class clazz);

    /**
     * Returns <code>true</code> if the context logger is enabled.
     * @param method the method.
     * @return <code>true</code> if the context logger is enabled.
     */
    protected abstract boolean isContextLogger(Method method);

    /**
     * The main interceptor method.
     *
     * @param ic the invocation context.
     * @return the method invocation result.
     * @throws Exception if the method fails.
     */
    @AroundInvoke
    public Object methodExecution(final InvocationContext ic) throws Exception {
        long startTime = System.nanoTime();
        Logger logger = null;
        String user = null;
        String clazz = null;
        String name = null;
        List<String> logParams = null;
        
        Object result = null;
        Method method = ic.getMethod();
        
        boolean contextLog = isContextLogger(method);
        
        if (contextLog) {
            name = method.getName();
            clazz = getClassName(ic.getTarget().getClass());
            user = getUser();
            if (user == null) {
                user = DEFAULT_USER;
            }
            logParams = LogService.getValues(ic.getParameters());

            logger = Logger.getLogger(clazz);
            String startMsg = LogService.getContextLogger().getLogStart(user, clazz, name, logParams);
            logger.info(startMsg);
        }
        try {
            result = ic.proceed();

            if (contextLog) {
                String resultObject = null;
                if (!method.getReturnType().equals(Void.TYPE)) {
                    resultObject = LogService.getValue(result);
                }
                String succeedMsg = LogService.getContextLogger().getLogSucceed(user, clazz, name, logParams, geTime(startTime), resultObject);
                logger.info(succeedMsg);
            }
        } catch (Throwable ex) {
            if (contextLog) {
                String failedMsg = LogService.getContextLogger().getLogFailed(user, clazz, user, logParams, geTime(startTime));
                logger.info(failedMsg);
            }
            throw ex;
        }
        return result;
    }

    /**
     * Gets the duration from
     * <code>startTime</code> to now.
     *
     * @param startTime start time.
     * @return the duration from <code>startTime</code> to now.
     */
    private static double geTime(long startTime) {
        return (1.0d * (System.nanoTime() - startTime)) / TIME_CONST;
    }
}
