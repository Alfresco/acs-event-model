/*
 * #%L
 * Alfresco Repository
 * %%
 * Copyright (C) 2005 - 2024 Alfresco Software Limited
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
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Objects;

public class AuditEntryResource extends AbstractAuditResource
{
    private ZonedDateTime createdAt;
    private UserInfo createdByUser;
    private String auditApplicationId;
    private final Map<String, Serializable> items;

    public AuditEntryResource(ZonedDateTime createdAt, UserInfo createdByUser, String auditApplicationId, Map<String, Serializable> items)
    {
        super(auditApplicationId);
        this.createdAt = createdAt;
        this.createdByUser = createdByUser;
        this.items = items;
    }

    public ZonedDateTime getCreatedAt()
    {
        return createdAt;
    }

    public UserInfo getCreatedByUser()
    {
        return createdByUser;
    }

    public String getAuditApplicationId()
    {
        return auditApplicationId;
    }

    public Map<String, Serializable> getItems()
    {
        return items;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof AuditEntryResource))
        {
            return false;
        }
        if (!super.equals(o))
        {
            return false;
        }
        AuditEntryResource that = (AuditEntryResource) o;
        return Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(createdByUser, that.createdByUser) &&
                Objects.equals(auditApplicationId, that.auditApplicationId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), createdAt, createdByUser, auditApplicationId);
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder(100);
        sb.append("AuditEntryResource [auditApplicationId=").append(auditApplicationId)
          .append(", createdAt=").append(createdAt)
          .append(", createdByUser=").append(createdByUser)
          .append(']');
        return sb.toString();
    }
}

