/*
 * Copyright 2012 Andrej_Petras.
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
package org.lorislab.jel.log.context.impl;

import org.lorislab.jel.log.context.ContextLogger;
import java.util.List;

/**
 * The default implementation of the context logger.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
public class DefaultContextLogger implements ContextLogger {

    /**
     * {@inheritDoc} Output: <user>:<method>(<parameters>).
     */
    @Override
    public String getLogStart(String uuid, String user, String clazz, String method, List<String> parameters) {
        StringBuilder sb = new StringBuilder();
        sb.append(user);
        sb.append(':');
        sb.append(method);
        sb.append('(');
        sb.append(getParameters(parameters));
        sb.append(')');
        return sb.toString();
    }

    /**
     * {@inheritDoc} Output: <user>:<method>(<parameters>) <result> [<time>s]
     * succeed.
     */
    @Override
    public String getLogSucceed(String uuid, String user, String clazz, String method, List<String> parameters, double time, String result) {
        StringBuilder sb = new StringBuilder();
        sb.append(user);
        sb.append(':');
        sb.append(method);
        sb.append('(');
        sb.append(getParameters(parameters));
        sb.append(") ");
        if (result != null) {
            sb.append(result);
            sb.append(" ");
        }
        sb.append('[');
        sb.append(time);
        sb.append("s] succeed.");
        return sb.toString();
    }

    /**
     * {@inheritDoc} Output: <user>:<method>(<parameters>) [<time>s] failed.
     */
    @Override
    public String getLogFailed(String uuid, String user, String clazz, String method, List<String> parameters, double time) {
        StringBuilder sb = new StringBuilder();
        sb.append(user);
        sb.append(':');
        sb.append(method);
        sb.append('(');
        sb.append(getParameters(parameters));
        sb.append(") [");
        sb.append(time);
        sb.append("s] failed.");
        return sb.toString();
    }

    /**
     * Load the list of parameters into the string builder. The items are
     * separated by
     * <code>,</code>.
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
}
