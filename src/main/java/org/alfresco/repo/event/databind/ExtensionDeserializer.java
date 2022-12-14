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
import org.alfresco.repo.event.extension.ExtensionAttributesImpl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Custom Jackson deserializer for the {@link ExtensionAttributes} type.
 *
 * @author Jamal Kaabi-Mofrad
 */
public class ExtensionDeserializer extends JsonDeserializer<ExtensionAttributes>
{
    public ExtensionDeserializer()
    {
    }

    @Override
    public ExtensionAttributes deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException
    {
        final ObjectCodec objectCodec = parser.getCodec();
        final JsonNode jsonNode = objectCodec.readTree(parser);

        ExtensionAttributes extension = new ExtensionAttributesImpl();

        jsonNode.fields().forEachRemaining(entry -> {
            String extName = entry.getKey();
            JsonNode extValue = entry.getValue();

            switch (extValue.getNodeType())
            {
                case STRING:
                    extension.addExtension(extName, extValue.textValue());
                    break;
                case NUMBER:
                    extension.addExtension(extName, extValue.numberValue());
                    break;
                case BOOLEAN:
                    extension.addExtension(extName, extValue.booleanValue());
                    break;
                default:
                    extension.addExtension(extName, extValue.toString());
            }
        });
        return extension;
    }
}


