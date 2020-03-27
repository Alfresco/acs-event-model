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

import java.util.Objects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * Represents Alfresco event's data.
 *
 * @author Jamal Kaabi-Mofrad
 */
@JsonDeserialize(builder = EventData.Builder.class)
public class EventData<R extends Resource>
{
    private final String principal;
    private final String eventGroupId;
    private final R resource;

    private EventData(Builder<R> builder)
    {
        this.principal = builder.principal;
        this.eventGroupId = builder.eventGroupId;
        this.resource = builder.resource;
    }

    public static <R extends Resource> Builder<R> builder()
    {
        return new Builder<>();
    }

    public String getPrincipal()
    {
        return principal;
    }

    public String getEventGroupId()
    {
        return eventGroupId;
    }

    public R getResource()
    {
        return resource;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof EventData))
        {
            return false;
        }
        EventData<?> eventData = (EventData<?>) o;
        return Objects.equals(principal, eventData.principal)
                    && Objects.equals(eventGroupId, eventData.eventGroupId)
                    && Objects.equals(resource, eventData.resource);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(principal, eventGroupId, resource);
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder(250);
        sb.append("EventData [principal=").append(principal)
          .append(", eventGroupId=").append(eventGroupId)
          .append(", resource=").append(resource)
          .append(']');

        return sb.toString();
    }

    /**
     * Builder for creating a {@link EventData} instance.
     */
    @JsonPOJOBuilder(withPrefix = "set")
    public static class Builder<R extends Resource>
    {
        private String principal;
        private String eventGroupId;
        private R resource;

        public Builder<R> setPrincipal(String principal)
        {
            this.principal = principal;
            return this;
        }

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

        public EventData<R> build()
        {
            return new EventData<>(this);
        }
    }
}
