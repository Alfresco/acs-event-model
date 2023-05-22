/*
 * #%L
 * Alfresco Repository
 * %%
 * Copyright (C) 2005 - 2022 Alfresco Software Limited
 * %%
 * This file is part of the Alfresco software.
 * If the software was purchased under a paid Alfresco license, the terms of
 * the paid license agreement will prevail.  Otherwise, the software is
 * provided under the following open source license terms:
 *
 * Alfresco is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Alfresco is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

package org.alfresco.repo.event.util;

import org.alfresco.repo.event.v1.model.NodeResource;

import java.util.Objects;

public final class NodeResourceUtils
{
        private NodeResourceUtils() {}

        /**
         * From a <code>NodeResource</code>, creates a <code>NodeResource.Builder</code> filled with all <code>NodeResource</code> information
         *
         * @param nodeResource to be used to copy information from
         * @return a <code>NodeResource.Builder</code> filled with nodeResource information
         */
        public static NodeResource.Builder getFilledBuilder( NodeResource nodeResource ) {
                Objects.requireNonNull(nodeResource);

                return new NodeResource.Builder()
                        .setAspectNames(nodeResource.getAspectNames())
                        .setContent(nodeResource.getContent())
                        .setCreatedAt(nodeResource.getCreatedAt())
                        .setId(nodeResource.getId())
                        .setIsFile(nodeResource.isFile())
                        .setCreatedByUser(nodeResource.getCreatedByUser())
                        .setIsFolder(nodeResource.isFolder())
                        .setLocalizedProperties(nodeResource.getLocalizedProperties())
                        .setModifiedAt(nodeResource.getModifiedAt())
                        .setModifiedByUser(nodeResource.getModifiedByUser())
                        .setName(nodeResource.getName())
                        .setNodeType(nodeResource.getNodeType())
                        .setPrimaryAssocQName(nodeResource.getPrimaryAssocQName())
                        .setPrimaryHierarchy(nodeResource.getPrimaryHierarchy())
                        .setProperties(nodeResource.getProperties());
        }
}