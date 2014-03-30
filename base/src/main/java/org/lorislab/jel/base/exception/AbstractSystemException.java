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

import org.lorislab.jel.base.resources.model.ResourceMessage;
import java.io.Serializable;

/**
 * The abstract exception class for service exception with localised message.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
public abstract class AbstractSystemException extends Exception implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -4874552216768714025L;
    /**
     * Resource manager for localised message.
     */
    protected final ResourceMessage message;

    /**
     * The constructor with the resource key.
     *
     * @param key the resource key.
     */
    protected AbstractSystemException(final Enum<?> key) {
        this(key, null);
    }

    /**
     * The constructor with the resource key.
     *
     * @param key the resource key.
     * @param arguments the resource key arguments.
     * @param reference the reference.
     */
    protected AbstractSystemException(final Enum<?> key, final Serializable reference, Serializable... arguments) {
        this(key, reference, null, arguments);
    }

    /**
     * The constructor with the resource key and cause.
     *
     * @param key the resource key.
     * @param arguments the resource key arguments.
     * @param cause the throw able cause.
     * @param reference the reference.
     */
    protected AbstractSystemException(final Enum<?> key, final Serializable reference, final Throwable cause, Serializable... arguments) {
        super(cause);
        message = new ResourceMessage(key, reference, arguments);
    }

    /**
     * Gets the resource message.
     *
     * @return the resource message.
     */
    public final ResourceMessage getResourceMessage() {
        return message;
    }

    /**
     * Gets resource key.
     *
     * @return the resource key.
     */
    public final Enum<?> getMessageKey() {
        if (message == null) {
            return null;
        }
        return message.getResourceKey();
    }
}
