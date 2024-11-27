/*
 * #%L
 * Alfresco Repository
 * %%
 * Copyright (C) 2005 - 2023 Alfresco Software Limited
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

import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import static org.alfresco.repo.event.util.TestUtil.getSecondaryParents;
import static org.alfresco.repo.event.util.TestUtil.getTestNodePrimaryHierarchy;
import static org.alfresco.repo.event.util.TestUtil.getUUID;

import java.lang.reflect.Field;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import org.alfresco.repo.event.v1.model.ContentInfo;
import org.alfresco.repo.event.v1.model.NodeResource;
import org.alfresco.repo.event.v1.model.UserInfo;

public class NodeResourceUtilsTest
{
    @Test
    public void nodeResource_createCopyFromBuilder()
    {
        // GIVEN: a nodeResource with all fields setup
        NodeResource originalResource = NodeResource.builder()
                .setId(getUUID())
                .setName("testFile.txt")
                .setPrimaryHierarchy(getTestNodePrimaryHierarchy())
                .setIsFile(true)
                .setIsFolder(false)
                .setNodeType("cm:content")
                .setPrimaryAssocQName("cm:testFile.txt")
                .setCreatedByUser(new UserInfo("john.doe", "John", "Doe"))
                .setCreatedAt(ZonedDateTime.now())
                .setModifiedByUser(new UserInfo("jane.doe", "Jane", "Doe"))
                .setModifiedAt(ZonedDateTime.now())
                .setProperties(Map.of("cm:title", "test title", "cm:from", new Date(-2637887000L)))
                .setLocalizedProperties(Map.of("cm:description",
                        Map.of(GERMAN.getLanguage(), "ruf mich an", ENGLISH.getLanguage(), "call me")))
                .setAspectNames(Set.of("cm:titled", "cm:auditable"))
                .setContent(new ContentInfo("text/plain", 16L, "UTF-8"))
                .setSecondaryParents(getSecondaryParents())
                .build();

        // asserts that all fields have information (are non-null)
        try
        {
            for (Field field : NodeResource.class.getDeclaredFields())
            {
                field.setAccessible(true);
                assertNotNull(field.get(originalResource));
            }
        }
        catch (IllegalAccessException exception)
        {
            fail("all fields should be accessible in this test");
        }

        // WHEN: a new nodeResource is created based on the original nodeResource
        final NodeResource copiedResource = NodeResourceUtils.getFilledBuilder(originalResource).build();

        // THEN: the new nodeResource must have the same information as the original one
        assertEquals(originalResource, copiedResource);
    }
}
