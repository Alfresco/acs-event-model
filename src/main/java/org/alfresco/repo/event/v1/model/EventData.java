/*
 * #%L
 * Alfresco Repository
 * %%
 * Copyright (C) 2005 - 2026 Alfresco Software Limited
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

import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import org.alfresco.repo.event.util.EventDataUtils;

/**
 * Represents Alfresco event's data.
 *
 * @author Jamal Kaabi-Mofrad
 */
@JsonDeserialize(builder = EventData.Builder.class)
@tools.jackson.databind.annotation.JsonDeserialize(builder = EventData.Builder.class)
public class EventData<R extends Resource> implements DataAttributes<R>
{
    private final String eventGroupId;

    @JsonTypeInfo(use = Id.NAME)
    private final R resource;

    @JsonInclude(Include.NON_NULL)
    @JsonTypeInfo(use = Id.NAME)
    private final R resourceBefore;

    // Ignored if authorities is disabled
    @JsonInclude(Include.NON_NULL)
    private final Set<String> resourceReaderAuthorities;

    // Ignored if authorities is disabled
    @JsonInclude(Include.NON_NULL)
    private final Set<String> resourceDeniedAuthorities;

    // Only relevant for AGS module
    @JsonInclude(Include.NON_NULL)
    private final Set<String> resourceReaderSecurityControls;

    // Ignored if authorities is disabled
    @JsonInclude(Include.NON_NULL)
    private final Set<String> resourceAppliedReaderAuthorities;

    // Ignored if authorities is disabled
    @JsonInclude(Include.NON_NULL)
    private final Set<String> resourceAppliedDeniedAuthorities;

    @JsonInclude(Include.NON_NULL)
    private final Boolean isInheritParentPermissions;

    private EventData(Builder<R> builder)
    {
        this.eventGroupId = builder.eventGroupId;
        this.resource = builder.resource;
        this.resourceBefore = builder.resourceBefore;
        this.resourceReaderAuthorities = builder.resourceReaderAuthorities;
        this.resourceDeniedAuthorities = builder.resourceDeniedAuthorities;
        this.resourceReaderSecurityControls = builder.resourceReaderSecurityControls;
        this.resourceAppliedReaderAuthorities = builder.resourceAppliedReaderAuthorities;
        this.resourceAppliedDeniedAuthorities = builder.resourceAppliedDeniedAuthorities;
        this.isInheritParentPermissions = builder.isInheritParentPermissions;
    }

    public static <R extends Resource> Builder<R> builder()
    {
        return new Builder<>();
    }

    @Override
    public String getEventGroupId()
    {
        return eventGroupId;
    }

    @Override
    public R getResource()
    {
        return resource;
    }

    @Override
    public R getResourceBefore()
    {
        return resourceBefore;
    }

    public Set<String> getResourceReaderAuthorities()
    {
        return resourceReaderAuthorities;
    }

    public Set<String> getResourceDeniedAuthorities()
    {
        return resourceDeniedAuthorities;
    }

    public Set<String> getResourceReaderSecurityControls()
    {
        return resourceReaderSecurityControls;
    }

    public Set<String> getResourceAppliedReaderAuthorities()
    {
        return resourceAppliedReaderAuthorities;
    }

    public Set<String> getResourceAppliedDeniedAuthorities()
    {
        return resourceAppliedDeniedAuthorities;
    }

    public Boolean getIsInheritParentPermissions()
    {
        return isInheritParentPermissions;
    }

    public EventData.Builder<R> toBuilder()
    {
        return EventDataUtils.getFilledBuilder(this);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof EventData<?> that))
        {
            return false;
        }
        return Objects.equals(eventGroupId, that.eventGroupId)
                && Objects.equals(resource, that.resource)
                && Objects.equals(resourceBefore, that.resourceBefore)
                && Objects.equals(resourceReaderAuthorities, that.resourceReaderAuthorities)
                && Objects.equals(resourceDeniedAuthorities, that.resourceDeniedAuthorities)
                && Objects.equals(resourceReaderSecurityControls, that.resourceReaderSecurityControls)
                && Objects.equals(resourceAppliedReaderAuthorities, that.resourceAppliedReaderAuthorities)
                && Objects.equals(resourceAppliedDeniedAuthorities, that.resourceAppliedDeniedAuthorities)
                && Objects.equals(isInheritParentPermissions, that.isInheritParentPermissions);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(eventGroupId, resource, resourceBefore, resourceReaderAuthorities,
                resourceDeniedAuthorities, resourceReaderSecurityControls, resourceAppliedReaderAuthorities,
                resourceAppliedDeniedAuthorities, isInheritParentPermissions);
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder(500);
        sb.append("EventData [eventGroupId=").append(eventGroupId)
                .append(", resource=").append(resource)
                .append(", resourceBefore=").append(resourceBefore)
                .append(", resourceReaderAuthorities=").append(resourceReaderAuthorities)
                .append(", resourceDeniedAuthorities=").append(resourceDeniedAuthorities)
                .append(", resourceReaderSecurityControls=").append(resourceReaderSecurityControls)
                .append(", resourceAppliedReaderAuthorities=").append(resourceAppliedReaderAuthorities)
                .append(", resourceAppliedDeniedAuthorities=").append(resourceAppliedDeniedAuthorities)
                .append(", isInheritParentPermissions=").append(isInheritParentPermissions)
                .append(']');
        return sb.toString();
    }

    /**
     * Builder for creating a {@link EventData} instance.
     */
    @JsonPOJOBuilder(withPrefix = "set")
    @tools.jackson.databind.annotation.JsonPOJOBuilder(withPrefix = "set")
    public static class Builder<R extends Resource>
    {

        private String eventGroupId;
        private R resource;
        private R resourceBefore;
        private Set<String> resourceReaderAuthorities;
        private Set<String> resourceDeniedAuthorities;
        private Set<String> resourceReaderSecurityControls;
        private Set<String> resourceAppliedReaderAuthorities;
        private Set<String> resourceAppliedDeniedAuthorities;
        private Boolean isInheritParentPermissions;

        public Builder<R> setEventGroupId(String eventGroupId)
        {
            this.eventGroupId = eventGroupId;
            return this;
        }

        public Builder<R> setResource(R resource)
        {
            this.resource = resource;
            return this;
        }

        public Builder<R> setResourceBefore(R resourceBefore)
        {
            this.resourceBefore = resourceBefore;
            return this;
        }

        public Builder<R> setResourceReaderAuthorities(Set<String> resourceReaderAuthorities)
        {
            this.resourceReaderAuthorities = resourceReaderAuthorities;
            return this;
        }

        public Builder<R> setResourceDeniedAuthorities(Set<String> resourceDeniedAuthorities)
        {
            this.resourceDeniedAuthorities = resourceDeniedAuthorities;
            return this;
        }

        public Builder<R> setResourceReaderSecurityControls(Set<String> resourceReaderSecurityControls)
        {
            this.resourceReaderSecurityControls = resourceReaderSecurityControls;
            return this;
        }

        public Builder<R> setResourceAppliedReaderAuthorities(Set<String> resourceAppliedReaderAuthorities)
        {
            this.resourceAppliedReaderAuthorities = resourceAppliedReaderAuthorities;
            return this;
        }

        public Builder<R> setResourceAppliedDeniedAuthorities(Set<String> resourceAppliedDeniedAuthorities)
        {
            this.resourceAppliedDeniedAuthorities = resourceAppliedDeniedAuthorities;
            return this;
        }

        public Builder<R> setIsInheritParentPermissions(Boolean isInheritParentPermissions)
        {
            this.isInheritParentPermissions = isInheritParentPermissions;
            return this;
        }

        public EventData<R> build()
        {
            return new EventData<>(this);
        }
    }
}
