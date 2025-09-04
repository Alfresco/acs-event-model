package org.alfresco.repo.event.util;

import static java.util.Optional.ofNullable;

import org.alfresco.repo.event.v1.model.ContentInfo;
import org.alfresco.repo.event.v1.model.DataAttributes;
import org.alfresco.repo.event.v1.model.NodeResource;
import org.alfresco.repo.event.v1.model.Resource;

public class DataAttributesUtils
{
    private DataAttributesUtils()
    {}

    /**
     * Checks within {@link DataAttributes} if it contains not blank content. Content is considered to be present if the resource is a {@link NodeResource} and its {@link ContentInfo#getSizeInBytes()} is greater than 0.
     */
    public static boolean containsNotBlankContent(DataAttributes<? extends Resource> dataAttributes)
    {
        return ofNullable(dataAttributes)
                .map(DataAttributes::getResource)
                .filter(NodeResource.class::isInstance)
                .map(NodeResource.class::cast)
                .map(NodeResource::getContent)
                .map(ContentInfo::getSizeInBytes)
                .filter(size -> size > 0)
                .isPresent();
    }
}
