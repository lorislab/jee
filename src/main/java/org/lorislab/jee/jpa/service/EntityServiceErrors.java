/*
 * Copyright 2016 Andrej Petras.
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
package org.lorislab.jee.jpa.service;

/**
 *
 * @author Andrej Petras
 */
public enum EntityServiceErrors {
    FAILED_TO_GET_ALL_ENTITIES,
    FAILED_TO_GET_ENTITY_BY_GUIDS,
    FAILED_TO_DELETE_ALL,
    FAILED_TO_DELETE_ENTITY,
    FAILED_TO_DELETE_ALL_QUERY,
    FAILED_TO_DELETE_BY_GUID_QUERY,
    FAILED_TO_DELETE_ALL_BY_GUIDS_QUERY,
    FAILED_TO_LOAD_ALL_ENTITIES,
    FAILED_TO_LOAD_GUIDS_ENTITIES,
    FAILED_TO_LOAD_ENTITY_BY_GUID,
    SAVE_ENTITY_FAILED,
    SAVE_ENTITIES_FAILED,
    PERSIST_ENTITY_FAILED,
    MERGE_ENTITY_FAILED,
    DELETE_ENTITY_FAILED,
    DELETE_ENTITY_BY_GUID_FAILED,
    DELETE_ENTITIES_FAILED,
    FIND_ENTITY_BY_ID_FAILED,
    FIND_ALL_ENTITIES_FAILED,
    FIND_FROM_TO_ENTITIES_FAILED;
}
