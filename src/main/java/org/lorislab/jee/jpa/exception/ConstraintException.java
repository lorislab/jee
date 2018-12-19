/*
 * Copyright 2018 Andrej Petras <andrej@ajka-andrej.com>.
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
package org.lorislab.jee.jpa.exception;

import org.lorislab.jee.exception.ServiceException;

/**
 * The constraint exception.
 */
public class ConstraintException extends ServiceException {

    /**
     * The constraints parameter key.
     */
    private static final String PARAMETER = "constraint";

    /**
     * The default constructor.
     * @param constraints the constraints message.
     * @param messageKey the message key.
     * @param cause the cause exception.
     */
    public ConstraintException(String constraints, Enum<?> messageKey, Throwable cause) {
        super(messageKey, cause);
        getNamedParameters().put(PARAMETER, constraints);
    }
    /**
     * The default constructor.
     * @param constraints the constraints message.
     * @param messageKey the message key.
     * @param cause the cause exception.
     * @param params the exception parameters.
     */
    public ConstraintException(String constraints, Enum<?> messageKey, Throwable cause, Object... params) {
        super(messageKey, cause, params);
        getNamedParameters().put(PARAMETER, constraints);
    }

    /**
     * Gets the constraints message.
     * @return the constraints message.
     */
    public String getConstraints() {
        return (String) getNamedParameters().get(PARAMETER);
    }

}