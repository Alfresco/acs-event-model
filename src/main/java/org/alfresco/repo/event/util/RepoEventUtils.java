/*
 * #%L
 * Alfresco Repository
 * %%
 * Copyright (C) 2025 Alfresco Software Limited
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

import static java.util.Objects.requireNonNull;

import org.alfresco.repo.event.v1.model.DataAttributes;
import org.alfresco.repo.event.v1.model.RepoEvent;
import org.alfresco.repo.event.v1.model.Resource;

public class RepoEventUtils
{
    private RepoEventUtils()
    {}

    public static <D extends DataAttributes<? extends Resource>> RepoEvent.Builder<D> getFilledBuilder(RepoEvent<D> repoEvent)
    {
        requireNonNull(repoEvent);

        return new RepoEvent.Builder<D>()
                .setSpecversion(repoEvent.getSpecversion())
                .setType(repoEvent.getType())
                .setId(repoEvent.getId())
                .setSource(repoEvent.getSource())
                .setTime(repoEvent.getTime())
                .setDataschema(repoEvent.getDataschema())
                .setDatacontenttype(repoEvent.getDatacontenttype())
                .setData(repoEvent.getData())
                .setExtensionAttributes(repoEvent.getExtensionAttributes());
    }
}
