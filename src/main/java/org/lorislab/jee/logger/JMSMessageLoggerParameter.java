/*
 * Copyright 201 Andrej Petras.
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
package org.lorislab.jee.logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.Message;

/**
 * The JMS message logger parameter.
 */
@Named
@ApplicationScoped
public class JMSMessageLoggerParameter extends LoggerParameter {

    /**
     * {@inheritDoc}
     */
    @Override
    public Class getAssignableFrom() {
        return Message.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getObject(Object parameter) {
        Message tmp = (Message) parameter;
        StringBuilder sb = new StringBuilder();
        sb.append(tmp.getClass().getName());
        sb.append("[id:");
        try {
            sb.append(tmp.getJMSMessageID()).append(",c:").append(tmp.getJMSCorrelationID());
        } catch (JMSException ex) {
            sb.append("?");
        }
        sb.append("],c:");
        try {
            sb.append(tmp.getJMSCorrelationID());
        } catch (JMSException ex) {
            sb.append("?");
        }
        sb.append(']');
        return sb.toString();
    }
}
