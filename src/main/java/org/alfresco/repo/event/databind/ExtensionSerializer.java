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
package org.alfresco.repo.event.databind;

import java.io.IOException;

import org.alfresco.repo.event.extension.ExtensionAttributes;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Custom Jackson serializer for the {@link ExtensionAttributes} type.
 *
 * @author Jamal Kaabi-Mofrad
 */
public class ExtensionSerializer extends StdSerializer<ExtensionAttributes>
{
    protected ExtensionSerializer()
    {
        super(null, false);
    }

    @Override
    public void serialize(ExtensionAttributes eventExtension, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException
    {
        jsonGenerator.writeStartObject();
        for (String ext : eventExtension.getExtensionNames())
        {
            Object extension = eventExtension.getExtension(ext);
            jsonGenerator.writeFieldName(ext);
            jsonGenerator.writeObject(extension);
        }
        jsonGenerator.writeEndObject();
    }
}


