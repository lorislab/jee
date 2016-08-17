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

/**
 *
 * @author Andrej Petras
 */
public final class LoggerConfiguration {

    /**
     * The start message pattern.
     */
    private static final String START_MESSAGE_PATTERN = "{}:{}({}) started.";
    /**
     * The start message pattern system property.
     */
    private static final String START_MESSAGE_PROPERTY = "org.lorislab.jel.logger.start";

    /**
     * The start message pattern.
     */
    private static final String TRACE_START_MESSAGE_PATTERN = "{}->{}:{}()";
    /**
     * The start message pattern system property.
     */
    private static final String TRACE_START_MESSAGE_PROPERTY = "org.lorislab.jel.logger.trace.start";
    /**
     * The start message pattern.
     */
    private static final String TRACE_END_MESSAGE_PATTERN = "{}-->{}:{}() {}";
    /**
     * The start message pattern system property.
     */
    private static final String TRACE_END_MESSAGE_PROPERTY = "org.lorislab.jel.logger.trace.end";
    /**
     * The start message pattern.
     */
    private static final String STACKETRACE_EXCEPTION_MESSAGE_PATTERN = "Exception in [{}] {}:{} error ";
    /**
     * The start message pattern system property.
     */
    private static final String STACKETRACE_EXCEPTION_MESSAGE_PROPERTY = "org.lorislab.jel.logger.stacketrace.exception";
    /**
     * The succeed message pattern.
     */
    private static final String SUCCEED_MESSAGE_PATTERN = "{}:{}({}):{} [{}s] succeed.";
    /**
     * The succeed message pattern system property.
     */
    private static final String SUCCEED_MESSAGE_PROPERTY = "org.lorislab.jel.logger.succeed";
    /**
     * The failed message pattern.
     */
    private static final String FAILED_MESSAGE_PATTERN = "{}:{}({}):{} [{}s] failed.";
    /**
     * The failed message pattern system property.
     */
    private static final String FAILED_MESSAGE_PROPERTY = "org.lorislab.jel.logger.failed";

/**
     * The start message pattern.
     */
    private static final String SERVICE_EXCEPTION_MESSAGE_PATTERN = "Service exception:\nrequestId:{}\nclass:{}\nkey:{}\nparams:{}\nnparams:{}\nmsg:{}";
    /**
     * The start message pattern system property.
     */
    private static final String SERVICE_EXCEPTION_MESSAGE_PROPERTY = "org.lorislab.jel.logger.service.exception";
    
    private static final String RESULT_VOID = "void";
    private static final String RESULT_VOID_PROPERTY = "org.lorislab.jel.logger.result.void";
    private static final String NO_USER = "anonymous";
    private static final String NO_USER_PROPERTY = "org.lorislab.jel.logger.nouser";

    public static final String PATTERN_NO_USER;
    public static final String PATTERN_RESULT_VOID;
    public static final String PATTERN_START;
    public static final String PATTERN_TRACE_START;
    public static final String PATTERN_TRACE_END;
    public static final String PATTERN_STACKETRACE_EXCEPTION;
    public static final String PATTERN_SERVICE_EXCEPTION;
    public static final String PATTERN_SUCCEED;

    public static final String PATTERN_FAILED;

    static {
        PATTERN_NO_USER = System.getProperty(NO_USER_PROPERTY, NO_USER);
        PATTERN_RESULT_VOID = System.getProperty(RESULT_VOID_PROPERTY, RESULT_VOID);
        PATTERN_TRACE_START = System.getProperty(TRACE_START_MESSAGE_PROPERTY, TRACE_START_MESSAGE_PATTERN);
        PATTERN_TRACE_END = System.getProperty(TRACE_END_MESSAGE_PROPERTY, TRACE_END_MESSAGE_PATTERN);
        PATTERN_STACKETRACE_EXCEPTION = System.getProperty(STACKETRACE_EXCEPTION_MESSAGE_PROPERTY, STACKETRACE_EXCEPTION_MESSAGE_PATTERN);
        PATTERN_SERVICE_EXCEPTION = System.getProperty(SERVICE_EXCEPTION_MESSAGE_PROPERTY, SERVICE_EXCEPTION_MESSAGE_PATTERN);
        PATTERN_START = System.getProperty(START_MESSAGE_PROPERTY, START_MESSAGE_PATTERN);
        PATTERN_SUCCEED = System.getProperty(SUCCEED_MESSAGE_PROPERTY, SUCCEED_MESSAGE_PATTERN);
        PATTERN_FAILED = System.getProperty(FAILED_MESSAGE_PROPERTY, FAILED_MESSAGE_PATTERN);
    }

    private LoggerConfiguration() {
    }

}
