
package org.lorislab.jee.rs.service;

import java.util.List;
import java.util.Map;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import org.lorislab.jee.exception.ServiceException;

/**
 * The abstract entity REST service.
 *
 * @author Andrej Petras
 *
 * @param <DTO> the DTO type.
 * @param <K> the key value.
 */
public interface EntityRestService<DTO, K> {

    /**
     * Finds all entities in the corresponding interval.
     *
     * @param from the from index.
     * @param count the count.
     * @return the corresponding list of the entities.
     * @throws ServiceException if the method fails.
     */
    @GET
    @Path("/")
    public List<DTO> find(@QueryParam("from") Integer from, @QueryParam("count") Integer count) throws ServiceException;

    /**
     * Finds the object by GUID.
     *
     * @param guid the GUID.
     * @return the corresponding entity.
     * @throws ServiceException if the method fails.
     */
    @GET
    @Path("/{guid}")
    public DTO findByGuid(@PathParam("guid") K guid) throws ServiceException;

    /**
     * Finds the object by set of GUIDs.
     *
     * @param guids the set of GUIDs.
     * @return the corresponding entity.
     * @throws ServiceException if the method fails.
     */
    @POST
    @Path("/find")
    public List<DTO> findByGuids(List<K> guids) throws ServiceException;

    /**
     * Finds the object by set of GUIDs.
     *
     * @param guids the set of GUIDs.
     * @return the corresponding entity.
     * @throws ServiceException if the method fails.
     */
    @POST
    @Path("/find/map")
    public Map<K, DTO> findByGuidsMap(List<K> guids) throws ServiceException;
    
    /**
     * Updates the entity.
     *
     * @param guid the object GUID.
     * @param object the entity.
     * @return the corresponding updated entity.
     * @throws ServiceException if the method fails.
     */
    @PUT
    @Path("/{guid}")
    public DTO update(@PathParam("guid") K guid, DTO object) throws ServiceException;

    /**
     * Creates the object.
     *
     * @param object the object.
     * @return the created entity.
     * @throws ServiceException if the method fails.
     */
    @POST
    @Path("/")
    public DTO create(DTO object) throws ServiceException;

    /**
     * Deletes all object.
     *
     * @return the number of removed objects.
     * @throws ServiceException if the method fails.
     */
    @DELETE
    @Path("/all")
    public int deleteAll() throws ServiceException;

    /**
     * Removes an object by GUID. Check on existence is made.
     *
     * @param guid the GUID of the entity
     * @return {@code true} if removed.
     * @throws ServiceException if the method fails.
     */
    @DELETE
    @Path("/{guid}")
    public boolean deleteByGuid(@PathParam("guid") K guid) throws ServiceException;

    /**
     * Deletes all object by set of GUIDs.
     *
     * @param guids the set of GUIDs.
     * @return the number of removed removed objects.
     * @throws ServiceException if the method fails.
     */
    @DELETE
    @Path("/")
    public int deleteByGuids(List<K> guids) throws ServiceException;

    /**
     * Loads all object.
     *
     * @return the list of DTO's
     * @throws ServiceException if the method fails.
     */
    @GET
    @Path("/load")
    public List<DTO> loadAll() throws ServiceException;

    /**
     * Loads the object by set of GUIDs.
     *
     * @param guids the object GUID.
     * @return the corresponding object.
     * @throws ServiceException if the method fails.
     */
    @POST
    @Path("/load")
    public List<DTO> loadByGuids(List<K> guids) throws ServiceException;

    /**
     * Loads the object by set of GUIDs.
     *
     * @param guids the object GUID.
     * @return the corresponding object.
     * @throws ServiceException if the method fails.
     */
    @POST
    @Path("/load/map")
    public Map<K, DTO> loadByGuidsMap(List<K> guids) throws ServiceException;
    
    /**
     * Loads the object by GUID.
     *
     * @param guid the object GUID.
     * @return the corresponding object.
     * @throws ServiceException if the method fails.
     */
    @GET
    @Path("/load/{guid}")
    public DTO loadByGuid(@PathParam("guid") K guid) throws ServiceException;

    /**
     * Deletes all object with query.
     *
     * @return the list of DTO's
     * @throws ServiceException if the method fails.
     */
    @DELETE
    @Path("/query/all")
    public int deleteQueryAll() throws ServiceException;

    /**
     * Deletes object by GUID with query.
     *
     * @param guid the object GUID.
     * @return {@code true} if all object removed.
     * @throws ServiceException if the method fails.
     */
    @DELETE
    @Path("/query/{guid}")
    public boolean deleteQueryByGuid(@PathParam("guid") K guid) throws ServiceException;

    /**
     * Deletes object by set of GUIDs with query.
     *
     * @param guids the set of GUIDs.
     * @return the number of deletes object.
     * @throws ServiceException if the method fails.
     */
    @DELETE
    @Path("/query")
    public int deleteQueryByGuids(List<K> guids) throws ServiceException;

}
