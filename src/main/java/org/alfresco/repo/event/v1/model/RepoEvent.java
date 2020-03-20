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

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.Objects;

import org.alfresco.repo.event.EventAttributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents Alfresco event.
 *
 * @author Jamal Kaabi-Mofrad
 */
public class RepoEvent<R extends Resource> implements EventAttributes
{
    private static final String SPEC_VERSION = "1.0";
    private static final String CONTENT_TYPE = "application/json";

    private final String specversion;
    private final String type;
    private final String id;
    private final URI source;
    // Optional
    @JsonInclude(Include.NON_NULL)
    private final String subject;
    private final ZonedDateTime time;
    private final String datacontenttype;
    private final EventData<R> data;

    @JsonCreator
    private RepoEvent(@JsonProperty("specversion") String specversion,
                      @JsonProperty("type") String type,
                      @JsonProperty("id") String id,
                      @JsonProperty("source") URI source,
                      @JsonProperty("subject") String subject,
                      @JsonProperty("time") ZonedDateTime time,
                      @JsonProperty("datacontenttype") String datacontenttype,
                      @JsonProperty("data") EventData<R> data)
    {
        this.specversion = specversion;
        this.type = type;
        this.id = id;
        this.source = source;
        this.subject = subject;
        this.time = time;
        this.datacontenttype = datacontenttype;
        this.data = data;
    }

    public static <R extends Resource> Builder<R> builder()
    {
        return new Builder<>();
    }

    @Override
    public String getSpecversion()
    {
        return specversion;
    }

    @Override
    public String getType()
    {
        return type;
    }

    @Override
    public String getId()
    {
        return id;
    }

    @Override
    public URI getSource()
    {
        return source;
    }

    @Override
    public String getSubject()
    {
        return subject;
    }

    @Override
    public ZonedDateTime getTime()
    {
        return time;
    }

    @Override
    public String getDatacontenttype()
    {
        return datacontenttype;
    }

    public EventData<R> getData()
    {
        return data;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof RepoEvent))
        {
            return false;
        }
        RepoEvent<?> repoEvent = (RepoEvent<?>) o;
        return Objects.equals(specversion, repoEvent.specversion)
                    && Objects.equals(type, repoEvent.type)
                    && Objects.equals(id, repoEvent.id)
                    && Objects.equals(source, repoEvent.source)
                    && Objects.equals(subject, repoEvent.subject)
                    && Objects.equals(time, repoEvent.time)
                    && Objects.equals(datacontenttype, repoEvent.datacontenttype)
                    && Objects.equals(data, repoEvent.data);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(specversion, type, id, source, subject, time, datacontenttype, data);
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder(250);
        sb.append("RepoEvent [specversion=").append(specversion)
          .append(", type=").append(type)
          .append(", id=").append(id)
          .append(", source=").append(source)
          .append(", subject=").append(subject)
          .append(", time=").append(time)
          .append(", datacontenttype=").append(datacontenttype)
          .append(", data=").append(data)
          .append(']');
        return sb.toString();
    }

    /**
     * Builder for creating a {@link RepoEvent} instance.
     */
    public static class Builder<R extends Resource>
    {
        private String specversion = SPEC_VERSION;
        private String type;
        private String id;
        private URI source;
        private String subject;
        private ZonedDateTime time;
        private String datacontenttype = CONTENT_TYPE;
        private EventData<R> data;

        public Builder<R> setSpecversion(String specversion)
        {
            this.specversion = specversion;
            return this;
        }

        public Builder<R> setType(String type)
        {
            this.type = type;
            return this;
        }

        public Builder<R> setId(String id)
        {
            this.id = id;
            return this;
        }

        public Builder<R> setSource(URI source)
        {
            this.source = source;
            return this;
        }

        public Builder<R> setSubject(String subject)
        {
            this.subject = (subject != null && subject.isEmpty()) ? null : subject;
            return this;
        }

        public Builder<R> setTime(ZonedDateTime time)
        {
            this.time = time;
            return this;
        }

        public Builder<R> setDatacontenttype(String datacontenttype)
        {
            this.datacontenttype = datacontenttype;
            return this;
        }

        public Builder<R> setData(EventData<R> data)
        {
            this.data = data;
            return this;
        }

        public RepoEvent<R> build()
        {
            return new RepoEvent<>(specversion, type, id, source, subject, time, datacontenttype, data);
        }
    }
}