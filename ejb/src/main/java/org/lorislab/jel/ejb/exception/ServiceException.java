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
package org.lorislab.jel.ejb.exception;

import java.io.Serializable;
import org.lorislab.jel.base.exception.AbstractSystemException;
import javax.ejb.ApplicationException;

/**
 * The exception for services.
 *
 * @author andrej
 */
@ApplicationException(rollback = true)
public class ServiceException extends AbstractSystemException {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -221717440785277228L;

    /**
     * The constructor with the resource key.
     *
     * @param key the resource key.
     */
    public ServiceException(final Enum<?> key) {
        this(key, null);
    }

    /**
     * The constructor with the resource key.
     *
     * @param key the resource key.
     * @param arguments the arguments.
     * @param reference the reference.
     */
    public ServiceException(final Enum<?> key, final Serializable reference, Object... arguments) {
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
    public ServiceException(final Enum<?> key, final Serializable reference,final Throwable cause, Object... arguments) {
        super(key, reference, cause, arguments);
    }

    
    /**
     * Indicates whether this Exception, respectively this sub-class(!), is fatal,
     * i.e. a rollback on the database should be done.<br/>
     * When employed in an EJB3 environment, the EJB container is advised to roll back the current
     * container-managed transaction (CMT), if this method resolves to <code>true</code>.
     *
     * @return Returns false if this class is marked with annotation
     * <code>ApplicationException(rollback=false)</code> and true if the annotation is
     * missing or the class is annotated with <code>ApplicationException(rollback=true)</code>
     */
    public final boolean isFatal() {
        ApplicationException annotation = this.getClass().getAnnotation(ApplicationException.class);
        if (annotation != null) {
            return annotation.rollback();
        }
        return false;
    }
}
