/*
 * Copyright 2011 Andrej Petras <andrej@ajka-andrej.com>.
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
package org.lorislab.jel.base.exception;

import java.io.Serializable;

/**
 * The exception for services.
 *
 * @author Andrej Petras
 */
public class SystemException extends AbstractSystemException {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -7791045274025766129L;

    /**
     * The constructor with the resource key.
     *
     * @param key the resource key.
     */
    public SystemException(final Enum<?> key) {
        this(key, null);
    }

    /**
     * The constructor with the resource key.
     *
     * @param key the resource key.
     * @param arguments the arguments.
     * @param reference the reference.
     */
    public SystemException(final Enum<?> key, final Serializable reference, Object... arguments) {
        this(key, reference, null, arguments);
    }

    /**
     * The constructor with the resource key and cause.
     *
     * @param key the resource key.
     * @param cause the throw able cause.
     * @param arguments the arguments.
     * @param reference the reference.
     */
    public SystemException(final Enum<?> key, final Serializable reference, final Throwable cause, Object... arguments) {
        super(key, reference, cause, arguments);
    }

}
