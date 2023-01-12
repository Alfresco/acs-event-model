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
package org.alfresco.repo.event.v1.model.extension;

import static org.alfresco.repo.event.util.TestUtil.OBJECT_MAPPER;
import static org.alfresco.repo.event.util.TestUtil.checkExpectedJsonBody;
import static org.alfresco.repo.event.util.TestUtil.getDataSchema;
import static org.alfresco.repo.event.util.TestUtil.getSource;
import static org.alfresco.repo.event.util.TestUtil.getTestNodePrimaryHierarchy;
import static org.alfresco.repo.event.util.TestUtil.getUUID;
import static org.alfresco.repo.event.util.TestUtil.parseTime;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.alfresco.repo.event.extension.ExtensionAttributes;
import org.alfresco.repo.event.extension.ExtensionAttributesImpl;
import org.alfresco.repo.event.util.TestUtil;
import org.alfresco.repo.event.v1.model.ContentInfo;
import org.alfresco.repo.event.v1.model.EventData;
import org.alfresco.repo.event.v1.model.EventType;
import org.alfresco.repo.event.v1.model.NodeResource;
import org.alfresco.repo.event.v1.model.RepoEvent;
import org.alfresco.repo.event.v1.model.UserInfo;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * @author Jamal Kaabi-Mofrad
 */
public class EventExtensionTest
{
    @Test
    public void nodeCreatedEventWithExtensionAttributes_marshalling() throws Exception
    {
        NodeResource resource = NodeResource.builder()
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
                    .setAspectNames(Set.of("cm:titled", "cm:auditable"))
                    .setContent(new ContentInfo("text/plain", 16L, "UTF-8"))
                    .build();

        EventData<NodeResource> eventData = EventData.<NodeResource>builder()
                    .setEventGroupId(getUUID())
                    .setResource(resource)
                    .build();

        // Add extensions
        ExtensionAttributes extAttributes = new ExtensionAttributesImpl();

        ExtensionTestObject extObject = new ExtensionTestObject();
        extObject.setId(getUUID());
        extObject.setIntProp(123);
        extObject.setDoubleProp(123.456789);
        extObject.setBoolProp(true);
        extObject.setListProp(List.of("ListElemTest1", "ListElemTest2", "ListElemTest1"));
        extObject.setSetProp(Set.of("SetElemTest1", "SetElemTest2"));
        extObject.setMapProp(Map.of("key1", "Value1", "key2", "Value2"));

        extAttributes.addExtension("extObject", extObject);
        extAttributes.addExtension("strAttTest", "Extra Attribute");
        extAttributes.addExtension("intAttTest", 1234);
        extAttributes.addExtension("doubleAttTest", 1234.56789);
        extAttributes.addExtension("boolAttTest", false);

        RepoEvent<EventData<NodeResource>> repoEvent = RepoEvent.<EventData<NodeResource>>builder()
                    .setId(getUUID())
                    .setSource(getSource())
                    .setTime(ZonedDateTime.now())
                    .setType(EventType.NODE_CREATED.getType())
                    .setData(eventData)
                    .setDataschema(getDataSchema("nodeCreated"))
                    .setExtensionAttributes(extAttributes)
                    .build();

        String result = OBJECT_MAPPER.writeValueAsString(repoEvent);
        String expectedJson = TestUtil.getResourceFileAsString("NodeCreatedEventWithExtension.json");
        // Compare the Json files
        checkExpectedJsonBody(expectedJson, result);
    }

    @Test
    public void nodeCreatedEventWithExtensionAttributes_unmarshalling() throws Exception
    {
        String nodeCreatedEventWithExtAttJson = TestUtil.getResourceFileAsString("NodeCreatedEventWithExtension.json");
        assertNotNull(nodeCreatedEventWithExtAttJson);
        RepoEvent<EventData<NodeResource>> result = OBJECT_MAPPER.readValue(nodeCreatedEventWithExtAttJson, new TypeReference<>()
        {
        });

        NodeResource resource = NodeResource.builder()
                    .setId("7491120a-e2cb-478f-8599-ebf057cc0c7c")
                    .setName("testFile.txt")
                    .setPrimaryHierarchy(getTestNodePrimaryHierarchy())
                    .setIsFile(true)
                    .setIsFolder(false)
                    .setNodeType("cm:content")
                    .setPrimaryAssocQName("cm:testFile.txt")
                    .setCreatedByUser(new UserInfo("john.doe", "John", "Doe"))
                    .setCreatedAt(parseTime("2020-04-27T12:37:03.555624+01:00"))
                    .setModifiedByUser(new UserInfo("jane.doe", "Jane", "Doe"))
                    .setModifiedAt(parseTime("2020-04-27T12:37:03.557956+01:00"))
                    .setProperties(Map.of("cm:title", "test title", "cm:from", "1969-12-01T11:15:13Z"))
                    .setAspectNames(Set.of("cm:titled", "cm:auditable"))
                    .setContent(new ContentInfo("text/plain", 16L, "UTF-8"))
                    .build();

        EventData<NodeResource> eventData = EventData.<NodeResource>builder()
                    .setEventGroupId("cb645043-e7d2-4e51-b61d-e6d01582cbab")
                    .setResource(resource)
                    .build();

        RepoEvent<EventData<NodeResource>> repoEvent = RepoEvent.<EventData<NodeResource>>builder()
                    .setId("97c1b36c-c569-4c66-8a31-7a8d0b6b804a")
                    .setSource(getSource())
                    .setTime(parseTime("2020-04-27T12:37:03.560134+01:00"))
                    .setType(EventType.NODE_CREATED.getType())
                    .setData(eventData)
                    .setDataschema(getDataSchema("nodeCreated"))
                    .build();

        assertEquals(repoEvent.getId(), result.getId());
        assertEquals(repoEvent.getType(), result.getType());
        assertEquals(repoEvent.getSpecversion(), result.getSpecversion());
        assertEquals(repoEvent.getSource(), result.getSource());
        assertEquals(repoEvent.getTime(), result.getTime());
        assertEquals(repoEvent.getDatacontenttype(), result.getDatacontenttype());
        assertEquals(repoEvent.getDataschema(), result.getDataschema());
        assertEquals(repoEvent.getData(), result.getData());

        // Get the extension attributes map
        ExtensionAttributes resultExtensionAttributes = result.getExtensionAttributes();
        assertNotNull("There should have been an extension.", resultExtensionAttributes);

        // Get the extension with the name 'client'
        Object extObjectValue = resultExtensionAttributes.getExtension("extObject");
        ExtensionTestObject objectOne = OBJECT_MAPPER.readValue(extObjectValue.toString(), ExtensionTestObject.class);
        // Construct the object, so it'd be easier to compare with the result
        ExtensionTestObject extObject = new ExtensionTestObject();
        extObject.setId("4cf25559-4c09-4457-96ea-af6c8a569e76");
        extObject.setIntProp(123);
        extObject.setDoubleProp(123.456789);
        extObject.setBoolProp(true);
        extObject.setListProp(List.of("ListElemTest1", "ListElemTest2", "ListElemTest1"));
        extObject.setSetProp(Set.of("SetElemTest1", "SetElemTest2"));
        extObject.setMapProp(Map.of("key1", "Value1", "key2", "Value2"));

        assertEquals(extObject, objectOne);
        // Now check the rest of added extensions
        assertEquals("Extra Attribute", resultExtensionAttributes.getExtension("strAttTest"));
        assertEquals(1234, resultExtensionAttributes.getExtension("intAttTest"));
        assertEquals(1234.56789, resultExtensionAttributes.getExtension("doubleAttTest"));
        assertEquals(false, resultExtensionAttributes.getExtension("boolAttTest"));
    }

    public static class ExtensionTestObject
    {
        private String       id;
        private int          intProp;
        private double       doubleProp;
        private boolean      boolProp;
        private List<String> listProp;
        private Set<String> setProp;
        private Map<String, String> mapProp;

        public String getId()
        {
            return id;
        }

        public void setId(String id)
        {
            this.id = id;
        }

        public int getIntProp()
        {
            return intProp;
        }

        public void setIntProp(int intProp)
        {
            this.intProp = intProp;
        }

        public double getDoubleProp()
        {
            return doubleProp;
        }

        public void setDoubleProp(double doubleProp)
        {
            this.doubleProp = doubleProp;
        }

        public boolean isBoolProp()
        {
            return boolProp;
        }

        public void setBoolProp(boolean boolProp)
        {
            this.boolProp = boolProp;
        }

        public List<String> getListProp()
        {
            return listProp;
        }

        public void setListProp(List<String> listProp)
        {
            this.listProp = listProp;
        }

        public Set<String> getSetProp()
        {
            return setProp;
        }

        public void setSetProp(Set<String> setProp)
        {
            this.setProp = setProp;
        }

        public Map<String, String> getMapProp()
        {
            return mapProp;
        }

        public void setMapProp(Map<String, String> mapProp)
        {
            this.mapProp = mapProp;
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o)
            {
                return true;
            }
            if (!(o instanceof ExtensionTestObject))
            {
                return false;
            }
            ExtensionTestObject that = (ExtensionTestObject) o;
            return getIntProp() == that.getIntProp()
                        && Double.compare(that.getDoubleProp(), getDoubleProp()) == 0
                        && isBoolProp() == that.isBoolProp()
                        && Objects.equals(getId(), that.getId())
                        && Objects.equals(getListProp(), that.getListProp())
                        && Objects.equals(getSetProp(), that.getSetProp())
                        && Objects.equals(getMapProp(), that.getMapProp());
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(getId(), getIntProp(), getDoubleProp(), isBoolProp(),
                                getListProp(), getSetProp(), getMapProp());
        }
    }
}
