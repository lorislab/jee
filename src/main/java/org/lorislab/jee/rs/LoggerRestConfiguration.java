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
package org.lorislab.jee.rs;

import java.text.MessageFormat;
import java.util.Properties;
import org.lorislab.jee.Configuration;
import org.lorislab.jee.cdi.util.JelConfig;

/**
 *
 * @author Andrej Petras
 */
public final class LoggerRestConfiguration {
    
    private static final MessageFormat MESSAGE_START;
    private static final MessageFormat MESSAGE_SUCCEED;
    private static final MessageFormat MESSAGE_CLIENT_START;
    private static final MessageFormat MESSAGE_CLIENT_SUCCEED;
    private static final MessageFormat MESSAGE_EXCEPTION;

    public static final boolean CLIENT_HEADER_PRINCIPAL;
    public static final boolean CLIENT_HEADER_HOST;
    
    static {
        Properties prop = JelConfig.loadConfig();
        MESSAGE_START = new MessageFormat( prop.getProperty("org.lorislab.jel.logger.rs.start", "{0}@{1} {2} {3} started."));
        MESSAGE_SUCCEED = new MessageFormat(prop.getProperty("org.lorislab.jel.logger.rs.succeed", "{0}@{1} {2} {3} [{4}s] finished with [{5}]."));
        MESSAGE_CLIENT_START = new MessageFormat(prop.getProperty("org.lorislab.jel.logger.rs.client.start", "[outgoing] {0} {1} {2} started."));
        MESSAGE_CLIENT_SUCCEED = new MessageFormat(prop.getProperty("org.lorislab.jel.logger.rs.client.start", "[incomming] {0} {1} {2} finished in [{3}s] with [{4}]."));
        MESSAGE_EXCEPTION = new MessageFormat(prop.getProperty("org.lorislab.jel.logger.rs.exception", "{0}@{1} {2} {3} threw exception [{4}]."));
        CLIENT_HEADER_PRINCIPAL = JelConfig.getBooleanProperty("org.lorislab.jel.logger.rs.client.header.principal", true, prop);
        CLIENT_HEADER_HOST = JelConfig.getBooleanProperty("org.lorislab.jel.logger.rs.client.header.host", true, prop);        
    }
    
    private LoggerRestConfiguration() {
    }

    public static Object msgException(Object... parameters) {
        return Configuration.msg(MESSAGE_EXCEPTION, parameters);
    }

    public static Object msgStart(Object... parameters) {
        return Configuration.msg(MESSAGE_START, parameters);
    }

    public static Object msgSucceed(Object... parameters) {
        return Configuration.msg(MESSAGE_SUCCEED, parameters);
    }

    public static Object msgClientSucceed(Object... parameters) {
        return Configuration.msg(MESSAGE_CLIENT_SUCCEED, parameters);
    }

    public static Object msgClientStart(Object... parameters) {
        return Configuration.msg(MESSAGE_CLIENT_START, parameters);
    }
}
