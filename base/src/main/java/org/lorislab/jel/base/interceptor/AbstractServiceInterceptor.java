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
package org.lorislab.jel.base.interceptor;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.security.Principal;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.AroundTimeout;
import javax.interceptor.InvocationContext;
import org.lorislab.jel.base.interceptor.annotation.LoggerService;
import org.lorislab.jel.base.interceptor.model.RequestData;
import org.lorislab.jel.base.logger.LoggerConfiguration;
import org.lorislab.jel.base.logger.LoggerContext;
import org.lorislab.jel.base.logger.LoggerFormaterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Andrej Petras
 */
@LoggerService
public abstract class AbstractServiceInterceptor implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractServiceInterceptor.class);

    private static final long serialVersionUID = 8386921967393114384L;

    @Inject
    private LoggerFormaterService loggerFormater;

    protected abstract String getPrincipal();

    @AroundInvoke
    @AroundTimeout
    public Object methodExecution(final InvocationContext ic) throws Exception {
        Object result = null;
        LoggerService ano = getLoggerServiceAno(ic.getTarget().getClass(), ic.getMethod());
        if (ano.log()) {
            String className = getClassName(ic.getTarget().getClass());
            String methodName = ic.getMethod().getName();
            String principal = getPrincipal();
            if (principal == null) {
                principal = LoggerConfiguration.PATTERN_NO_USER;
            }

            RequestData data = RequestDataThreadHolder.getOrCreate(principal);

            if (data.isTrace()) {
                LOGGER.trace(LoggerConfiguration.PATTERN_TRACE_START, data.peekTrace(), className, methodName);
            }
            data.addTrace(className);

            String parameters = loggerFormater.getValuesString(ic.getParameters());
            LoggerContext context = new LoggerContext(data.getId(), principal, methodName, parameters);

            Logger logger = LoggerFactory.getLogger(getClassName(ic.getTarget().getClass()));
            logger.info(LoggerConfiguration.PATTERN_START, context.getStartParams());

            context.setStartTime(System.currentTimeMillis());
            try {
                result = ic.proceed();

                context.setEndTime(System.currentTimeMillis());
                if (ic.getMethod().getReturnType() == Void.TYPE) {
                    context.setResult(LoggerConfiguration.PATTERN_RESULT_VOID);
                } else {
                    context.setResult(loggerFormater.getValue(result));
                }
                // log the success message
                logger.info(LoggerConfiguration.PATTERN_SUCCEED, context.getSuccessParams());
            } catch (Exception e) {
                Exception ex = transformException(e);
                context.setEndTime(System.currentTimeMillis());
                context.setResult(loggerFormater.getValue(ex));
                // log the failed message
                logger.error(LoggerConfiguration.PATTERN_FAILED, context.getFailedParams());
                if (ano.stacktrace()) {
                    logger.error(LoggerConfiguration.PATTERN_TRACE_EXCEPTION, context.getId(), className, methodName, ex);
                }
                throw ex;
            } finally {
                if (data.isTrace()) {
                    data.popTrace();
                }
                if (data.isTrace()) {
                    LOGGER.trace(LoggerConfiguration.PATTERN_TRACE_END, className, data.peekTrace(), methodName, context.getResult());
                }
            }
        } else {
            result = ic.proceed();
        }
        return result;
    }

    protected Exception transformException(Exception ex) {
        return (Exception) ex;
    }

    /**
     * Gets the service class name.
     *
     * @param clazz the target class.
     * @return the corresponding class name.
     */
    protected String getClassName(Class<?> clazz) {
        String result = null;
        if (clazz != null) {
            result = clazz.getName();
            int index = result.indexOf('$');
            if (index != -1) {
                result = result.substring(0, index);
            }
        }
        return result;
    }

    public static String getPrincipalName(Principal principal) {
        if (principal != null) {
            return principal.getName();
        }
        return LoggerConfiguration.PATTERN_NO_USER;
    }

    public static LoggerService getLoggerServiceAno(Class<?> clazz, Method method) {
        LoggerService result = AbstractServiceInterceptor.class.getAnnotation(LoggerService.class);
        if (method != null && method.isAnnotationPresent(LoggerService.class)) {
            result = method.getAnnotation(LoggerService.class);
        } else if (clazz != null && clazz.isAnnotationPresent(LoggerService.class)) {
            result = clazz.getAnnotation(LoggerService.class);
        }
        return result;
    }

}
