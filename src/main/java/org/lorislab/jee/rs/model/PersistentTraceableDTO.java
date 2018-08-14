package org.lorislab.jee.rs.model;

import java.util.Date;

/**
 *
 * @author Andrej Petras
 */
public class PersistentTraceableDTO extends PersistentDTO {

    private static final long serialVersionUID = 6232914274045176825L;

    /**
     * The creation date.
     */
    private Date creationDate;
    /**
     * The creation user.
     */
    private String creationUser;
    /**
     * The modification date.
     */
    private Date modificationDate;
    /**
     * The modification user.
     */
    private String modificationUser;

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getModificationUser() {
        return modificationUser;
    }

    public void setModificationUser(String modificationUser) {
        this.modificationUser = modificationUser;
    }

}
