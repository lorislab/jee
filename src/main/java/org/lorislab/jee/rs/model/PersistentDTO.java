package org.lorislab.jee.rs.model;

import java.util.Objects;

/**
 * The abstract entity DTO class.
 * 
 * @author Andrej Petras
 */
public class PersistentDTO extends AbstractPersistentDTO<String> {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = 6232914274045176825L;

    /**
     * The GUID.
     */
    private String guid;

    @Override
    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Override
    public String getGuid() {
        return guid;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PersistentDTO other = (PersistentDTO) obj;        
        if (guid instanceof String) {
            if (guid == null) {
                if (other.guid != null) {
                    return false;
                } else {
                    return super.equals(obj);
                }
            } else if (!guid.equals(other.guid)) {
                return false;
            }
        } else {
            return super.equals(obj);
        }
        
        return true;        
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.guid);
        return hash;
    }

}
