package org.alfresco.repo.event.util;

import org.alfresco.repo.event.v1.model.ContentInfo;
import org.alfresco.repo.event.v1.model.NodeResource;
import org.alfresco.repo.event.v1.model.UserInfo;
import org.junit.Test;

import java.lang.reflect.Field;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static org.alfresco.repo.event.util.TestUtil.getTestNodePrimaryHierarchy;
import static org.alfresco.repo.event.util.TestUtil.getUUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

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
                        .build();

                // asserts that all fields have information (are non-null)
                try
                {
                        for (Field field : NodeResource.class.getDeclaredFields())
                        {
                                field.setAccessible(true);
                                assertNotNull(field.get(originalResource));
                        }
                } catch( IllegalAccessException exception ) {
                        fail("all fields should be accessible in this test");
                }

                // WHEN: a new nodeResource is created based on the original nodeResource
                final NodeResource copiedResource = NodeResourceUtils.getFilledBuilder(originalResource).build();

                // THEN: the new nodeResource must have the same information as the original one
                assertEquals(originalResource, copiedResource);
        }
}
