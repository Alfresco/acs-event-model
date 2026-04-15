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

import java.time.ZonedDateTime;
import java.util.Date;

import tools.jackson.core.Version;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleAbstractTypeResolver;
import tools.jackson.databind.module.SimpleModule;

import org.alfresco.repo.event.extension.ExtensionAttributes;
import org.alfresco.repo.event.extension.ExtensionAttributesImpl;
import org.alfresco.repo.event.v1.model.DataAttributes;
import org.alfresco.repo.event.v1.model.EventData;

/**
 * Repo Event json factory.
 */
public class JsonMapperFactory
{

    public static JsonMapper createInstance()
    {
        return new JsonMapperFactory().createObjectMapper();
    }

    public JsonMapper createObjectMapper()
    {
        final SimpleModule module = getSimpleModule();
        final SimpleAbstractTypeResolver resolver = getSimpleAbstractTypeResolver();
        if (resolver != null)
        {
            // add the extension when creating the Mapper.
            resolver.addMapping(ExtensionAttributes.class, ExtensionAttributesImpl.class);
            module.setAbstractTypes(resolver);
        }

        return JsonMapper.builder()
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .addModule(module)
                .build();
    }

    protected SimpleModule getSimpleModule()
    {
        final SimpleModule module = new SimpleModule("Resource Serializer-Deserializer", new Version(0, 1, 0, "", "", ""));
        module.addSerializer(ZonedDateTime.class, new DateTimeSerializerJackson3());
        module.addDeserializer(ZonedDateTime.class, new DateTimeDeserializerJackson3());
        module.addSerializer(Date.class, new DateSerializerJackson3());

        return module;
    }

    protected SimpleAbstractTypeResolver getSimpleAbstractTypeResolver()
    {
        SimpleAbstractTypeResolver resolver = new SimpleAbstractTypeResolver();
        resolver.addMapping(DataAttributes.class, EventData.class);
        return resolver;
    }
}
