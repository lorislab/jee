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
package org.lorislab.jel.logger.context;

import java.text.MessageFormat;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author Andrej Petras
 */
@ApplicationScoped
public class DefaultContextLoggerService implements ContextService {

    /**
     * The start message pattern.
     */
    private static final String START_MESSAGE_PATTERN = "[{0}] {1,choice,0#|1#[remote] }{2}:{3} started.";
    /**
     * The start message pattern system property.
     */
    private static final String START_MESSAGE_PROPERTY = "org.lorislab.jel.logger.context.start";
    /**
     * The succeed message pattern.
     */
    private static final String SUCCEED_MESSAGE_PATTERN = "[{0}] {1,choice,0#|1#[remote] }{2}:{3}:{4} [{5,number,#.###}s] succeed.";
    /**
     * The succeed message pattern system property.
     */
    private static final String SUCCEED_MESSAGE_PROPERTY = "org.lorislab.jel.logger.context.succeed";
    /**
     * The failed message pattern.
     */
    private static final String FAILED_MESSAGE_PATTERN = "[{0}] {1,choice,0#|1#[remote] }{2}:{3}:{4} [{5,number,#.###}s] failed.";
    /**
     * The failed message pattern system property.
     */
    private static final String FAILED_MESSAGE_PROPERTY = "org.lorislab.jel.logger.context.failed";

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLogStart(Context context) {
        String pattern = System.getProperty(START_MESSAGE_PROPERTY, START_MESSAGE_PATTERN);
        int remote = 0;
        if (context.isRemote()) {
            remote = 1;
        }
        String method = getMethodString(context.getMethod(), context.getParameters());
        return MessageFormat.format(pattern, context.getId(), remote, context.getPrincipal(), method);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLogSucceed(Context context) {
        String pattern = System.getProperty(SUCCEED_MESSAGE_PROPERTY, SUCCEED_MESSAGE_PATTERN);
        int remote = 0;
        if (context.isRemote()) {
            remote = 1;
        }
        String method = getMethodString(context.getMethod(), context.getParameters());
        return MessageFormat.format(pattern, context.getId(), remote, context.getPrincipal(), method, context.getResult(), context.getTime());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLogFailed(Context context) {
        String pattern = System.getProperty(FAILED_MESSAGE_PROPERTY, FAILED_MESSAGE_PATTERN);
        int remote = 0;
        if (context.isRemote()) {
            remote = 1;
        }
        String method = getMethodString(context.getMethod(), context.getParameters());
        return MessageFormat.format(pattern, context.getId(), remote, context.getPrincipal(), method, context.getResult(), context.getTime());
    }

    /**
     * Load the list of parameters into the string builder. The items are
     * separated by <code>,</code>.
     *
     * @param parameters the parameters
     * @return the parameters
     */
    private StringBuilder getParameters(List<String> parameters) {
        StringBuilder sb = new StringBuilder();
        if (parameters != null) {
            boolean first = false;
            for (String item : parameters) {
                if (first) {
                    sb.append(',');
                }
                sb.append(item);
                first = true;
            }
        }
        return sb;
    }

    /**
     * Constructs the main part of the log message, with verboseness matching
     * supplied {@link TraceLevel}.
     *
     * @param method method to describe
     * @param parameters list of parameters in their textual representation
     * @param methodLevel level of information detail
     * @return Textual representation of this method relevant for logging
     */
    private String getMethodString(String method, List<String> parameters) {
        StringBuilder sb = new StringBuilder();
        sb.append(method);
        sb.append('(').append(getParameters(parameters)).append(')');
        return sb.toString();
    }

}
