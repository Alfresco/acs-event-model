/*
 * #%L
 * Alfresco Repository
 * %%
 * Copyright (C) 2005 - 2020 Alfresco Software Limited
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

package org.alfresco.repo.event.v1.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents Alfresco node resource.
 *
 * @author Jamal Kaabi-Mofrad
 */
public class NodeResource extends Resource
{
    private final String nodeType;
    private final boolean isFile;
    private final boolean isFolder;
    private final Map<String, Serializable> properties;

    @JsonCreator
    protected NodeResource(@JsonProperty("id") String id,
                         @JsonProperty("nodeType") String nodeType,
                         @JsonProperty("isFile") boolean isFile,
                         @JsonProperty("isFolder") boolean isFolder,
                         @JsonProperty("properties") Map<String, Serializable> properties,
                         @JsonProperty("primaryHierarchy") List<String> primaryHierarchy)
    {
        super(id, primaryHierarchy);
        this.nodeType = nodeType;
        this.isFile = isFile;
        this.isFolder = isFolder;
        this.properties = properties;
    }

    public String getNodeType()
    {
        return nodeType;
    }

    @JsonProperty("isFile")
    public boolean isFile()
    {
        return isFile;
    }

    @JsonProperty("isFolder")
    public boolean isFolder()
    {
        return isFolder;
    }

    public Map<String, Serializable> getProperties()
    {
        return properties;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof NodeResource))
        {
            return false;
        }
        if (!super.equals(o))
        {
            return false;
        }
        NodeResource that = (NodeResource) o;
        return isFile == that.isFile
                    && isFolder == that.isFolder
                    && Objects.equals(nodeType, that.nodeType)
                    && Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(nodeType, isFile, isFolder, properties);
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder(250);
        sb.append("NodeResource [nodeType=").append(nodeType)
          .append(", isFile=").append(isFile)
          .append(", isFolder=").append(isFolder)
          .append(", properties=").append(properties)
          .append(", id=").append(id)
          .append(", type=").append(getClass().getName())
          .append(", primaryHierarchy=").append(primaryHierarchy)
          .append(']');
        return sb.toString();
    }

    public static Builder builder()
    {
        return new Builder();
    }

    /**
     * Builder for creating a {@link NodeResource} instance.
     */
    public static class Builder
    {
        private String id;
        private String nodeType;
        private boolean isFile;
        private boolean isFolder;
        private Map<String, Serializable> properties;
        private List<String> primaryHierarchy;

        public Builder setId(String id)
        {
            this.id = id;
            return this;
        }

        public Builder setNodeType(String nodeType)
        {
            this.nodeType = nodeType;
            return this;
        }

        public Builder setIsFile(boolean isFile)
        {
            this.isFile = isFile;
            return this;
        }

        public Builder setIsFolder(boolean isFolder)
        {
            this.isFolder = isFolder;
            return this;
        }

        public Builder setProperties(Map<String, Serializable> properties)
        {
            this.properties = properties;
            return this;
        }

        public Builder setPrimaryHierarchy(List<String> primaryHierarchy)
        {
            this.primaryHierarchy = primaryHierarchy;
            return this;
        }

        public NodeResource build()
        {
            return new NodeResource(id, nodeType, isFile, isFolder, properties, primaryHierarchy);
        }
    }
}
