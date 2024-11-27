/*
 * #%L
 * Alfresco Repository
 * %%
 * Copyright (C) 2005 - 2022 Alfresco Software Limited
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
package org.alfresco.repo.event.extension;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.alfresco.repo.event.databind.ExtensionDeserializer;
import org.alfresco.repo.event.databind.ExtensionSerializer;

/**
 * @author Jamal Kaabi-Mofrad
 */
@JsonSerialize(using = ExtensionSerializer.class)
@JsonDeserialize(using = ExtensionDeserializer.class)
public class ExtensionAttributesImpl implements ExtensionAttributes
{
    private final Map<String, Object> map;

    public ExtensionAttributesImpl()
    {
        this.map = new HashMap<>();
    }

    @Override
    public Object getExtension(String extensionName)
    {
        return map.get(extensionName);
    }

    @Override
    public void addExtension(String extensionName, Object extension)
    {
        map.put(extensionName, extension);
    }

    @Override
    public Set<String> getExtensionNames()
    {
        return map.keySet();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof ExtensionAttributesImpl))
        {
            return false;
        }
        ExtensionAttributesImpl that = (ExtensionAttributesImpl) o;
        return Objects.equals(map, that.map);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(map);
    }

    @Override
    public String toString()
    {
        return map.toString();
    }
}
