/*
 * Copyright (c) 2016, 1000kit.org, and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.lorislab.jee.rs.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.lorislab.jee.jpa.model.AbstractPersistent;
import org.lorislab.jee.rs.model.AbstractPersistentDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * The abstract map-struct entity mapper.
 *
 * @param <ENTITY> the entity model type.
 * @param <DTO> the DTO model type.
 * @param <K> the key type.
 * 
 * @author Andrej Petras
 */
public interface AbstractEntityMapper<ENTITY extends AbstractPersistent<K>, DTO extends AbstractPersistentDTO<K>, K> {

    /**
     * Creates the entity from the DTO.
     *
     * @param dto the DTO object.
     * @return the corresponding entity object.
     */
    @Mapping(target = "guid", ignore = true)
    @Mapping(target = "version", ignore = true, defaultValue = "0")
    @Mapping(target = "persisted", ignore = true)
    public ENTITY create(DTO dto);

    /**
     * The mapping method for the updating the entity from DTO object.
     *
     * @param model the entity object.
     * @param dto the DTO object.
     */
    @Mapping(target = "guid", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "persisted", ignore = true)
    public void update(@MappingTarget ENTITY model, DTO dto);

    /**
     * The mapping method for find of entity.
     *
     * @param data the entity.
     * @return the corresponding DTO object.
     */
    public DTO find(ENTITY data);

    /**
     * The mapping method for find of list entities.
     *
     * @param data the list of entities.
     * @return the corresponding list of DTO objects.
     */
    public List<DTO> finds(List<ENTITY> data);

    /**
     * The mapping method for load entity.
     *
     * @param data the entity.
     * @return the corresponding DTO object.
     */
    @FullMapping
    public DTO load(ENTITY data);

    /**
     * The mapping method for the load list of entities.
     *
     * @param data the list of entities.
     * @return the corresponding list of DTO object.
     */
    @FullMapping
    @IterableMapping(qualifiedBy = {FullMapping.class})
    public List<DTO> loads(List<ENTITY> data);

    /**
     * The mapping method for the load list of entities.
     *
     * @param data the list of entities.
     * @return the corresponding list of DTO object.
     */
    @FullMapping
    @IterableMapping(qualifiedBy = {FullMapping.class})
    public Set<DTO> loads(Set<ENTITY> data);

    /**
     * The mapping method for find by GUID and return map.
     *
     * @param data the map of entities.
     * @return the corresponding map.
     */
    public Map<K, DTO> findsMap(Map<K, ENTITY> data);

    /**
     * The mapping method for load by GUID and return map.
     *
     * @param data the map of entities.
     * @return the corresponding map.
     */
    @FullMapping
    @MapMapping(valueQualifiedBy = {FullMapping.class})
    public Map<K, DTO> loadsMap(Map<K, ENTITY> data);

}
