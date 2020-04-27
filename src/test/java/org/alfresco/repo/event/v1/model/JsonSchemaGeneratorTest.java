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

import java.io.File;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;

/**
 * @author Jamal Kaabi-Mofrad
 */
public class JsonSchemaGeneratorTest
{

    @Test
    public void generateSchema() throws Exception
    {
        File outputDir = new File("target/schema/");
        outputDir.mkdirs();

        ObjectMapper mapper = new ObjectMapper();
        SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
        TypeReference<RepoEvent<NodeResource>> typeReference = new TypeReference<>()
        {
        };

        JavaType type = mapper.getTypeFactory().constructType(typeReference);
        mapper.acceptJsonFormatVisitor(type, visitor);
        JsonSchema schema = visitor.finalSchema();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputDir, "RepoEvent.json"), schema);
    }
}
