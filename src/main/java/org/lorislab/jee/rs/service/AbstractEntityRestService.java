package org.lorislab.jee.rs.service;

import java.util.List;
import java.util.Map;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.lorislab.jee.exception.ServiceException;
import org.lorislab.jee.jpa.model.AbstractPersistent;
import org.lorislab.jee.jpa.service.AbstractEntityService;
import org.lorislab.jee.rs.mapper.AbstractEntityMapper;
import org.lorislab.jee.rs.model.AbstractPersistentDTO;


/**
 * The abstract rest entity service.
 *
 * @param <ENTITY> the entity runtime type.
 * @param <DTO> the DTO runtime type.
 * @param <K> t he key type.
 * 
 * @author Andrej Petras
 */
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public abstract class AbstractEntityRestService<ENTITY extends AbstractPersistent<K>, DTO extends AbstractPersistentDTO<K>, K> implements EntityRestService<DTO, K> {

    /**
     * Gets the entity mapper.
     *
     * @return the entity mapper.
     */
    protected abstract AbstractEntityMapper<ENTITY, DTO, K> getMapper();

    /**
     * Gets the entity service.
     *
     * @return the entity service.
     */
    protected abstract AbstractEntityService<ENTITY, K> getService();

    /**
     * {@inheritDoc }
     */
    @Override
    public List<DTO> find(Integer from, Integer count) throws ServiceException {
        List<ENTITY> tmp = getService().find(from, count);
        return getMapper().finds(tmp);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public DTO findByGuid(K guid) throws ServiceException {
        ENTITY tmp = getService().findByGuid(guid);
        return getMapper().find(tmp);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<DTO> findByGuids(List<K> guids) throws ServiceException {
        List<ENTITY> tmp = getService().findByGuids(guids);
        return getMapper().finds(tmp);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @SuppressWarnings("unchecked")
    public Map<K, DTO> findByGuidsMap(List<K> guids) throws ServiceException {
        Map<K, ENTITY> tmp = getService().findByGuidsMap(guids);
        return getMapper().findsMap(tmp);
    }
            
    /**
     * {@inheritDoc }
     */
    @Override
    public DTO update(K guid, DTO object) throws ServiceException {
        ENTITY conf = getService().loadByGuid(guid);
        getMapper().update(conf, object);
        conf = getService().update(conf);
        return getMapper().find(conf);
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public DTO create(DTO object) throws ServiceException {
        ENTITY tmp = getMapper().create(object);
        ENTITY conf = getService().create(tmp);
        return getMapper().find(conf);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int deleteAll() throws ServiceException {
        return getService().deleteAll();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean deleteByGuid(K guid) throws ServiceException {
        return getService().deleteByGuid(guid);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @SuppressWarnings("unchecked")
    public int deleteByGuids(List<K> guids) throws ServiceException {
        return getService().deleteByGuids(guids);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<DTO> loadAll() throws ServiceException {
        List<ENTITY> tmp = getService().loadAll();
        return getMapper().loads(tmp);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public DTO loadByGuid(K guid) throws ServiceException {
        ENTITY tmp = getService().loadByGuid(guid);
        return getMapper().load(tmp);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<DTO> loadByGuids(List<K> guids) throws ServiceException {
        List<ENTITY> tmp = getService().loadByGuids(guids);
        return getMapper().loads(tmp);
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    @SuppressWarnings("unchecked")
    public Map<K, DTO> loadByGuidsMap(List<K> guids) throws ServiceException {
        Map<K, ENTITY> tmp = getService().loadByGuidsMap(guids);
        return getMapper().findsMap(tmp);
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public int deleteQueryAll() throws ServiceException {
        return getService().deleteQueryAll();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean deleteQueryByGuid(K guid) throws ServiceException {
        return getService().deleteQueryByGuid(guid);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    @SuppressWarnings("unchecked")
    public int deleteQueryByGuids(List<K> guids) throws ServiceException {
        return getService().deleteQueryByGuids((List)guids);
    }
   
}
