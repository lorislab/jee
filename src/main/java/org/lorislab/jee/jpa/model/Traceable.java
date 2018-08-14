/*
 * Copyright 2012 Andrej Petras <andrej@ajka-andrej.com>.
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
package org.lorislab.jee.jpa.model;

import java.util.Date;

/**
 * Traceable persistent entity implementation.
 *
 * The implementation class for Persistent interface.
 *
 * @author Andrej Petras
 */
public interface Traceable {


    /**
     * Gets the creation date.
     *
     * @return the creation date.
     */
    public Date getCreationDate();

    /**
     * Sets the creation date.
     *
     * @param creationDate the creation date.
     */
    public void setCreationDate(final Date creationDate);

    /**
     * Gets the creation user.
     *
     * @return the creation user.
     */
    public String getCreationUser();

    /**
     * Sets the creation user.
     *
     * @param creationUser the creation user.
     */
    public void setCreationUser(final String creationUser);

    /**
     * Gets the modification date.
     *
     * @return the modification date.
     */
    public Date getModificationDate();

    /**
     * Sets the modification date.
     *
     * @param modificationDate the modification date.
     */
    public void setModificationDate(final Date modificationDate);

    /**
     * Gets the modification user.
     *
     * @return the modification user.
     */
    public String getModificationUser();

    /**
     * Sets the modification user.
     *
     * @param modificationUser the modification user.
     */
    public void setModificationUser(final String modificationUser);
}
