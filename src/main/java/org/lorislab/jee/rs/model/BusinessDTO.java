package org.lorislab.jee.rs.model;

import java.util.Objects;

/**
 * The abstract entity DTO class.
 * 
 * @author Andrej Petras
 */
public class BusinessDTO extends AbstractPersistentDTO<Long> {

    /**
     * The UID for this class.
     */
    private static final long serialVersionUID = -2353694526525149212L;
    
    /**
     * The GUID.
     */
    private Long guid;

    @Override
    public void setGuid(Long guid) {
        this.guid = guid;
    }

    @Override
    public Long getGuid() {
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
        BusinessDTO other = (BusinessDTO) obj;        
        if (guid instanceof Long) {
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
