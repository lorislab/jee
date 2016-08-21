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
package org.lorislab.jel.base.logger;

import java.text.MessageFormat;

/**
 *
 * @author Andrej Petras
 */
public final class LoggerConfiguration {

    /**
     * The start message pattern system property.
     */
    private static final String START_MESSAGE_PROPERTY = "org.lorislab.jel.logger.start";

    /**
     * The succeed message pattern system property.
     */
    private static final String SUCCEED_MESSAGE_PROPERTY = "org.lorislab.jel.logger.succeed";

    /**
     * The failed message pattern system property.
     */
    private static final String FAILED_MESSAGE_PROPERTY = "org.lorislab.jel.logger.failed";

    /**
     * The start message pattern system property.
     */
    private static final String TRACE_START_MESSAGE_PROPERTY = "org.lorislab.jel.logger.trace.start";

    /**
     * The start message pattern system property.
     */
    private static final String TRACE_END_MESSAGE_PROPERTY = "org.lorislab.jel.logger.trace.end";

    /**
     * The start message pattern system property.
     */
    private static final String SERVICE_EXCEPTION_MESSAGE_PROPERTY = "org.lorislab.jel.logger.service.exception";
    private static final String EXCEPTION_MESSAGE_PROPERTY = "org.lorislab.jel.logger.exception";
    private static final String RESULT_VOID_PROPERTY = "org.lorislab.jel.logger.result.void";

    private static final String NO_USER_PROPERTY = "org.lorislab.jel.logger.nouser";

    public static final String PATTERN_NO_USER = System.getProperty(NO_USER_PROPERTY, "anonymous");
    public static final String PATTERN_RESULT_VOID = System.getProperty(RESULT_VOID_PROPERTY, "void");

    private static final MessageFormat MESSAGE_TRACE_START = new MessageFormat(System.getProperty(TRACE_START_MESSAGE_PROPERTY, "{0}->{1}:{2}()"));
    private static final MessageFormat MESSAGE_TRACE_END = new MessageFormat(System.getProperty(TRACE_END_MESSAGE_PROPERTY, "{0}-->{1}:{2}() {3}"));
    private static final MessageFormat MESSAGE_SERVICE_EXCEPTION = new MessageFormat(System.getProperty(SERVICE_EXCEPTION_MESSAGE_PROPERTY, "Service exception:\nrequestId:{0}\nclass:{1}\nkey:{2}\nparams:{3}\nnparams:{4}\nmsg:{5}"));

    private static final MessageFormat MESSAGE_START = new MessageFormat(System.getProperty(START_MESSAGE_PROPERTY, "{0}:{1}({2}) started."));
    private static final MessageFormat MESSAGE_SUCCEED = new MessageFormat(System.getProperty(SUCCEED_MESSAGE_PROPERTY, "{0}:{1}({2}):{3} [{4}s] succeed."));
    private static final MessageFormat MESSAGE_FAILED = new MessageFormat(System.getProperty(FAILED_MESSAGE_PROPERTY, "{0}:{1}({2}):{3} [{4}s] failed."));
    private static final MessageFormat MESSAGE_EXCEPTION = new MessageFormat(System.getProperty(EXCEPTION_MESSAGE_PROPERTY, "Exception in [{0}] {1}:{2} error"));
    
    private LoggerConfiguration() {
    }

    public static Object msgTraceStart(Object... parameters) {
        return msg(MESSAGE_TRACE_START, parameters);
    }

    public static Object msgTraceEnd(Object... parameters) {
        return msg(MESSAGE_TRACE_END, parameters);
    }

    public static Object msgException(Object... parameters) {
        return msg(MESSAGE_EXCEPTION, parameters);
    }
    
    public static Object msgServiceException(Object... parameters) {
        return msg(MESSAGE_SERVICE_EXCEPTION, parameters);
    }

    public static Object msgFailed(Object... parameters) {
        return msg(MESSAGE_FAILED, parameters);
    }

    public static Object msgSucceed(Object... parameters) {
        return msg(MESSAGE_SUCCEED, parameters);
    }

    public static Object msgStart(Object... parameters) {
        return msg(MESSAGE_START, parameters);
    }

    public static Object msg(MessageFormat mf, Object[] parameters) {
        return new Object() {
            @Override
            public String toString() {
                return mf.format(parameters, new StringBuffer(), null).toString();
            }
        };
    }
}
