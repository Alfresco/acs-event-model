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

import java.util.Objects;

/**
 * Base audit resource.
 */
public abstract class AbstractAuditResource implements Resource
{
    protected String auditApplicationId;

    public AbstractAuditResource(String auditApplicationId)
    {
        this.auditApplicationId = auditApplicationId;
    }

    public String getAuditApplicationId()
    {
        return auditApplicationId;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof AbstractAuditResource))
        {
            return false;
        }
        AbstractAuditResource that = (AbstractAuditResource) o;
        return Objects.equals(auditApplicationId, that.auditApplicationId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(auditApplicationId);
    }
}
