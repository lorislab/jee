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

/**
 *
 * @author Andrej Petras
 */
public final class LoggerRestConfiguration {

    private static final String DEFAULT_EXCEPTION_MESSAGE = "{}@{} {} {} threw exception [{}].";
    /**
     * The start message pattern system property.
     */
    private static final String PROPERTY_EXCEPTION_MESSAGE = "org.lorislab.jel.logger.rs.exception";

    public static final String PATTERN_EXCEPTION_MESSAGE = System.getProperty(PROPERTY_EXCEPTION_MESSAGE, DEFAULT_EXCEPTION_MESSAGE);

    private static final String DEFAULT_START = "{}@{} {} {} started.";
    /**
     * The start message pattern system property.
     */
    private static final String PROPERTY_START = "org.lorislab.jel.logger.rs.start";

    public static final String PATTERN_START = System.getProperty(PROPERTY_START, DEFAULT_START);

    private static final String DEFAULT_SUCCEED = "{}@{} {} {} [{}s] finished with [{}].";
    /**
     * The start message pattern system property.
     */
    private static final String PROPERTY_SUCCEED = "org.lorislab.jel.logger.rs.succeed";

    public static final String PATTERN_SUCCEED = System.getProperty(PROPERTY_SUCCEED, DEFAULT_SUCCEED);

    private static final String DEFAULT_CLIENT_START_PATTERN = "[outgoing] {} {} {} started.";
    private static final String PROPERTY_CLIENT_START_PATTERN = "org.lorislab.jel.logger.rs.client.start";
    public static final String PATTERN_CLIENT_START = System.getProperty(PROPERTY_CLIENT_START_PATTERN, DEFAULT_CLIENT_START_PATTERN);

    private static final String DEFAULT_CLIENT_SUCCEED_PATTERN = "[incomming] {} {} {} finished in [{}s] with [{}].";
    private static final String PROPERTY_CLIENT_SUCCEED_PATTERN = "org.lorislab.jel.logger.rs.client.start";
    public static final String PATTERN_CLIENT_SUCCEED = System.getProperty(PROPERTY_CLIENT_SUCCEED_PATTERN, DEFAULT_CLIENT_SUCCEED_PATTERN);

    
    private LoggerRestConfiguration() {
    }

}
