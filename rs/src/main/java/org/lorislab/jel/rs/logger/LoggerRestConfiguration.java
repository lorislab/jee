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
package org.lorislab.jel.rs.logger;

import java.text.MessageFormat;
import org.lorislab.jel.base.logger.LoggerConfiguration;

/**
 *
 * @author Andrej Petras
 */
public final class LoggerRestConfiguration {

    /**
     * The start message pattern system property.
     */
    private static final String PROPERTY_EXCEPTION_MESSAGE = "org.lorislab.jel.logger.rs.exception";

    /**
     * The start message pattern system property.
     */
    private static final String PROPERTY_START = "org.lorislab.jel.logger.rs.start";

    /**
     * The start message pattern system property.
     */
    private static final String PROPERTY_SUCCEED = "org.lorislab.jel.logger.rs.succeed";

    private static final String PROPERTY_CLIENT_START_PATTERN = "org.lorislab.jel.logger.rs.client.start";

    private static final String PROPERTY_CLIENT_SUCCEED_PATTERN = "org.lorislab.jel.logger.rs.client.start";

    private static final MessageFormat MESSAGE_START = new MessageFormat(System.getProperty(PROPERTY_START, "{0}@{1} {2} {3} started."));
    private static final MessageFormat MESSAGE_SUCCEED = new MessageFormat(System.getProperty(PROPERTY_SUCCEED, "{0}@{1} {2} {3} [{4}s] finished with [{5}]."));
    private static final MessageFormat MESSAGE_CLIENT_START = new MessageFormat(System.getProperty(PROPERTY_CLIENT_START_PATTERN, "[outgoing] {0} {1} {2} started."));
    private static final MessageFormat MESSAGE_CLIENT_SUCCEED = new MessageFormat(System.getProperty(PROPERTY_CLIENT_SUCCEED_PATTERN, "[incomming] {0} {1} {2} finished in [{3}s] with [{4}]."));

    private static final MessageFormat MESSAGE_EXCEPTION = new MessageFormat(System.getProperty(PROPERTY_EXCEPTION_MESSAGE, "{0}@{1} {2} {3} threw exception [{4}]."));

    private LoggerRestConfiguration() {
    }

    public static Object msgException(Object... parameters) {
        return LoggerConfiguration.msg(MESSAGE_EXCEPTION, parameters);
    }

    public static Object msgStart(Object... parameters) {
        return LoggerConfiguration.msg(MESSAGE_START, parameters);
    }

    public static Object msgSucceed(Object... parameters) {
        return LoggerConfiguration.msg(MESSAGE_SUCCEED, parameters);
    }

    public static Object msgClientSucceed(Object... parameters) {
        return LoggerConfiguration.msg(MESSAGE_CLIENT_SUCCEED, parameters);
    }

    public static Object msgClientStart(Object... parameters) {
        return LoggerConfiguration.msg(MESSAGE_CLIENT_START, parameters);
    }
}
