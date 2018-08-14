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
package org.lorislab.jee;

import java.text.MessageFormat;
import java.util.Properties;
import org.lorislab.jee.cdi.util.JelConfig;

/**
 *
 * @author Andrej Petras
 */
public final class Configuration {

    public static final String PATTERN_NO_USER;
    public static final String PATTERN_RESULT_VOID;

    private static final MessageFormat MESSAGE_TRACE_START;
    private static final MessageFormat MESSAGE_TRACE_END;
    private static final MessageFormat MESSAGE_SERVICE_EXCEPTION;

    private static final MessageFormat MESSAGE_START;
    private static final MessageFormat MESSAGE_SUCCEED;
    private static final MessageFormat MESSAGE_FAILED;
    private static final MessageFormat MESSAGE_EXCEPTION;
    
    static {
        Properties prop = JelConfig.loadConfig();
        PATTERN_NO_USER = prop.getProperty("org.lorislab.jel.logger.nouser", "anonymous");
        PATTERN_RESULT_VOID = prop.getProperty("org.lorislab.jel.logger.result.void", "void");

        MESSAGE_TRACE_START = new MessageFormat(prop.getProperty("org.lorislab.jel.logger.trace.start", "{0}->{1}:{2}()"));
        MESSAGE_TRACE_END = new MessageFormat(prop.getProperty("org.lorislab.jel.logger.trace.end", "{0}-->{1}:{2}() {3}"));
        MESSAGE_SERVICE_EXCEPTION = new MessageFormat(prop.getProperty("org.lorislab.jel.logger.service.exception", "Service exception:\nrequestId:{0}\nclass:{1}\nkey:{2}\nparams:{3}\nnparams:{4}\nmsg:{5}"));

        MESSAGE_START = new MessageFormat(prop.getProperty("org.lorislab.jel.logger.start", "{0}:{1}({2}) started."));
        MESSAGE_SUCCEED = new MessageFormat(prop.getProperty("org.lorislab.jel.logger.succeed", "{0}:{1}({2}):{3} [{4}s] succeed."));
        MESSAGE_FAILED = new MessageFormat(prop.getProperty("org.lorislab.jel.logger.failed", "{0}:{1}({2}):{3} [{4}s] failed."));
        MESSAGE_EXCEPTION = new MessageFormat(prop.getProperty("org.lorislab.jel.logger.exception", "Exception in [{0}] {1}:{2} error"));        
    }
    
    private Configuration() {
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
