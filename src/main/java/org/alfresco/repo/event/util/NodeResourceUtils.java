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
