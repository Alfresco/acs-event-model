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
package org.alfresco.repo.event.databind;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

import org.alfresco.repo.event.extension.ExtensionAttributes;

/**
 * Custom Jackson 3 serializer for the {@link ExtensionAttributes} type.
 */
public class ExtensionSerializerJackson3 extends StdSerializer<ExtensionAttributes>
{
    protected ExtensionSerializerJackson3()
    {
        super(ExtensionAttributes.class);
    }

    @Override
    public void serialize(ExtensionAttributes eventExtension, JsonGenerator jsonGenerator,
            SerializationContext serializationContext)
    {
        jsonGenerator.writeStartObject();
        for (String ext : eventExtension.getExtensionNames())
        {
            Object extension = eventExtension.getExtension(ext);
            jsonGenerator.writeName(ext);
            jsonGenerator.writePOJO(extension);
        }
        jsonGenerator.writeEndObject();
    }
}
