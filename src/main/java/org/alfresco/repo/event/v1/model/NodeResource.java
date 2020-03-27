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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * Represents Alfresco node resource.
 *
 * @author Jamal Kaabi-Mofrad
 */
/* Any attribute that its value is null, will not be serialized.
 * For example, 'affectedPropertiesBefore' and 'affectedPropertiesAfter' are only relevant
 * for Node Updated event, so we don't serialize them when there is Node Created event.
 */
@JsonInclude(Include.NON_NULL)
@JsonDeserialize(builder = NodeResource.Builder.class)
public class NodeResource extends Resource
{
    private final String nodeType;
    private final boolean isFile;
    private final boolean isFolder;
    private final Map<String, Serializable> properties;
    private final Map<String, Serializable> affectedPropertiesBefore;
    private final Map<String, Serializable> affectedPropertiesAfter;
    private final List<String> aspectNames;

    private NodeResource(Builder builder)
    {
        super(builder.id, builder.primaryHierarchy);
        this.nodeType = builder.nodeType;
        this.isFile = builder.isFile;
        this.isFolder = builder.isFolder;
        this.properties = builder.properties;
        this.affectedPropertiesBefore = builder.affectedPropertiesBefore;
        this.affectedPropertiesAfter = builder.affectedPropertiesAfter;
        this.aspectNames = builder.aspectNames;
    }

    public static Builder builder()
    {
        return new Builder();
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

    public Map<String, Serializable> getAffectedPropertiesBefore()
    {
        return affectedPropertiesBefore;
    }

    public Map<String, Serializable> getAffectedPropertiesAfter()
    {
        return affectedPropertiesAfter;
    }

    public List<String> getAspectNames()
    {
        return aspectNames;
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
                    && Objects.equals(properties, that.properties)
                    && Objects.equals(affectedPropertiesBefore, that.affectedPropertiesBefore)
                    && Objects.equals(affectedPropertiesAfter, that.affectedPropertiesAfter)
                    && Objects.equals(aspectNames, that.aspectNames);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), nodeType, isFile, isFolder, properties,
                            affectedPropertiesBefore,
                            affectedPropertiesAfter,
                            aspectNames);
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
          .append(", aspectNames=").append(aspectNames)
          .append(", affectedPropertiesBefore=").append(affectedPropertiesBefore)
          .append(", affectedPropertiesAfter=").append(affectedPropertiesAfter)
          .append(']');
        return sb.toString();
    }

    /**
     * Builder for creating a {@link NodeResource} instance.
     */
    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder
    {
        private String id;
        private String nodeType;
        private boolean isFile;
        private boolean isFolder;
        private List<String> primaryHierarchy;
        private Map<String, Serializable> properties;
        private Map<String, Serializable> affectedPropertiesBefore;
        private Map<String, Serializable> affectedPropertiesAfter;
        private List<String> aspectNames;

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

        public Builder setPrimaryHierarchy(List<String> primaryHierarchy)
        {
            this.primaryHierarchy = primaryHierarchy;
            return this;
        }

        public Builder setProperties(Map<String, Serializable> properties)
        {
            this.properties = properties;
            return this;
        }

        public Builder setAffectedPropertiesBefore(Map<String, Serializable> affectedPropertiesBefore)
        {
            this.affectedPropertiesBefore = affectedPropertiesBefore;
            return this;
        }

        public Builder setAffectedPropertiesAfter(Map<String, Serializable> affectedPropertiesAfter)
        {
            this.affectedPropertiesAfter = affectedPropertiesAfter;
            return this;
        }

        public Builder setAspectNames(List<String> aspectNames)
        {
            this.aspectNames = aspectNames;
            return this;
        }

        public NodeResource build()
        {
            return new NodeResource(this);
        }
    }
}
