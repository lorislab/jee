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
package org.lorislab.jel.base.resources.model;

import java.io.Serializable;

/**
 * The message of resource.
 *
 * @author Andrej Petras <andrej@ajka-andrej.com>
 */
public class ResourceMessage implements Serializable {

    /**
     * The UID of this class.
     */
    private static final long serialVersionUID = -4266822500859564754L;

    /**
     * The reference object.
     */
    private final Serializable reference;

    /**
     * The key of resource.
     */
    private final Enum<?> resourceKey;

    /**
     * The arguments for the message.
     */
    private final Serializable[] arguments;
    
    /**
     * Creates the message of resource from the resource key and the arguments.
     *
     * @param resourceKey the key of resource.
     * @param arguments the arguments of message.
     * @param reference the reference key.
     */
    public ResourceMessage(final Enum<?> resourceKey, final Serializable reference, final Serializable[] arguments) {
        this.resourceKey = resourceKey;
        this.reference = reference;
        this.arguments = arguments;
    }

    /**
     * Gets the key of resource.
     *
     * @return the key of resource.
     */
    public final Enum<?> getResourceKey() {
        return resourceKey;
    }

    /**
     * Gets the arguments of the message.
     *
     * @return the arguments of the message.
     */
    public final Serializable[] getArguments() {
        return arguments.clone();
    }

    /**
     * Gets the reference.
     *
     * @return the reference.
     */
    public final Serializable getReference() {
        return reference;
    }
}
