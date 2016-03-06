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
package org.lorislab.jel.core.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The exception class for service exception with localised message.
 *
 * @author Andrej Petras <andrej@lorislab.org>
 */
public class ServiceException extends Exception implements Serializable {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -4874552216768714025L;
    /**
     * The reference object.
     */
    private final Serializable reference;

    /**
     * The key of resource.
     */
    private final Enum<?> key;

    /**
     * The arguments for the message.
     */
    private final Serializable[] arguments;

    /**
     * The constructor with the resource key.
     *
     * @param key the resource key.
     * @param arguments the resource key arguments.
     */
    protected ServiceException(final Enum<?> key, Serializable... arguments) {
        this(key, null, null, arguments);
    }

    /**
     * The constructor with the resource key and cause.
     *
     * @param key the resource key.
     * @param arguments the resource key arguments.
     * @param cause the throw able cause.
     */
    protected ServiceException(final Enum<?> key, final Throwable cause, Serializable... arguments) {
        this(key, null, cause, arguments);
    }    
    
    /**
     * The constructor with the resource key and cause.
     *
     * @param key the resource key.
     * @param arguments the resource key arguments.
     * @param cause the throw able cause.
     * @param reference the reference.
     */
    protected ServiceException(final Enum<?> key, final Serializable reference, final Throwable cause, Serializable... arguments) {
        super(cause);
        this.key = key;
        this.reference = reference;
        this.arguments = arguments;
    }

    /**
     * Gets the key of resource.
     *
     * @return the key of resource.
     */
    public final Enum<?> getKey() {
        return key;
    }

    /**
     * Gets the arguments of the message.
     *
     * @return the arguments of the message.
     */
    public final Serializable[] getArguments() {
        if (arguments != null) {
            return arguments.clone();
        }
        return null;
    }

    /**
     * Gets the reference.
     *
     * @return the reference.
     */
    public final Serializable getReference() {
        return reference;
    }

    /**
     * Gets the message parameters.
     * [0] - reference
     * [1..n] - arguments;
     * 
     * @return the list of message parameters.
     */
    public final Serializable[] getMessagePatemeters() {
        List<Serializable> tmp = new ArrayList<>();
        tmp.add(reference);
        tmp.addAll(Arrays.asList(arguments));
        return tmp.toArray(new Serializable[tmp.size()]);        
    }
}
