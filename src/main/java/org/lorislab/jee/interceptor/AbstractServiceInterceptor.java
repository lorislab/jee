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
package org.lorislab.jee.interceptor;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.AroundTimeout;
import javax.interceptor.InvocationContext;
import org.lorislab.jee.exception.ServiceException;
import org.lorislab.jee.annotation.LoggerService;
import org.lorislab.jee.Configuration;
import org.lorislab.jee.logger.LoggerFormaterService;
import org.lorislab.java.util.ResourceManager;
import static org.lorislab.jee.interceptor.RequestDataThreadHolder.createAndSet;
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
        return execution(ic, ic.getTarget().getClass());
    }

    public Object execution(final InvocationContext ic, Class clazz) throws Exception {
        Object result = null;
        LoggerService ano = InterceptorUtil.getLoggerServiceAno(clazz, ic.getMethod());
        if (ano.log()) {
            String className = getClassName(clazz);
            String methodName = ic.getMethod().getName();
            String principal = getPrincipal();
            if (principal == null) {
                principal = Configuration.PATTERN_NO_USER;
            }

            boolean cleanRequest = false;
            RequestData data = RequestDataThreadHolder.get();
            if (data == null) {
                data = createAndSet(null, principal, null, null, null);
                cleanRequest = true;
            }

            if (data.isTrace()) {
                LOGGER.trace("{}", Configuration.msgTraceStart(data.peekTrace(), className, methodName));
            }
            data.addTrace(className);

            Logger logger = LoggerFactory.getLogger(className);
            String parameters = loggerFormater.getValuesString(ic.getParameters());

            InterceptorContext context = new InterceptorContext(data.getId(), principal, methodName, parameters);
            logger.info("{}", Configuration.msgStart(context.principal, context.method, context.parameters));
            try {
                result = ic.proceed();

                context.time = InterceptorUtil.intervalToString(context.startTime, System.currentTimeMillis());
                if (ic.getMethod().getReturnType() == Void.TYPE) {
                    context.result = Configuration.PATTERN_RESULT_VOID;
                } else {
                    context.result = loggerFormater.getValue(result);
                }
                // log the success message
                logger.info("{}", Configuration.msgSucceed(context.principal, context.method, context.parameters, context.result, context.time));
            } catch (Throwable e) {
                Exception ex;
                
                if (e instanceof Error) {
                    ex = new RuntimeException(e.getMessage(), e);
                } else {
                    ex = (Exception) e;
                }
                
                
                if (e instanceof InvocationTargetException) {
                    InvocationTargetException ite = (InvocationTargetException) e;
                    ex = (Exception) ite.getCause();
                }

                context.time = InterceptorUtil.intervalToString(context.startTime, System.currentTimeMillis());
                context.result = loggerFormater.getValue(ex);
                // log the failed message
                logger.error("{}", Configuration.msgFailed(context.principal, context.method, context.parameters, context.result, context.time));

                boolean stacktrace = ano.stacktrace();
                if (stacktrace) {
                    ServiceException sec = null;
                    if (ex instanceof ServiceException) {
                        sec = (ServiceException) ex;
                        stacktrace = sec.isStackTraceLog();
                        sec.setStackTraceLog(true);
                    }
                    if (stacktrace) {
                        if (sec != null) {
                            String msg = ResourceManager.getMessage(sec.getKey(), sec.getParameters(), null);
                            logger.error("{}", Configuration.msgServiceException(context.id, sec.getClass().getName(), sec.getKey(), sec.getParameters(), sec.getNamedParameters(), msg));
                        }
                        logger.error("{}", Configuration.msgException(context.id, className, methodName), ex);
                    }
                }
                throw ex;
            } finally {
                if (data.isTrace()) {
                    data.popTrace();
                }
                if (data.isTrace()) {
                    LOGGER.trace("{}", Configuration.msgTraceEnd(className, data.peekTrace(), methodName, context.result));
                }
                if (cleanRequest) {
                    RequestDataThreadHolder.clear();
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

}
