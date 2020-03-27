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

import static org.alfresco.repo.event.util.TestUtil.UUID_JSON_COMPARATOR;
import static org.alfresco.repo.event.util.TestUtil.getSource;
import static org.alfresco.repo.event.util.TestUtil.getTestNodePrimaryHierarchy;
import static org.alfresco.repo.event.util.TestUtil.getUUID;
import static org.alfresco.repo.event.util.TestUtil.parseTime;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import org.alfresco.repo.event.databind.ObjectMapperFactory;
import org.alfresco.repo.event.util.TestUtil;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Jamal Kaabi-Mofrad
 */
public class EventTest
{
    private static final ObjectMapper OBJECT_MAPPER = ObjectMapperFactory.createInstance();

    @Test
    public void nodeCreatedEvent_marshalling() throws Exception
    {
        NodeResource resource = NodeResource.builder()
                    .setId(getUUID())
                    .setPrimaryHierarchy(getTestNodePrimaryHierarchy())
                    .setIsFile(true)
                    .setIsFolder(false)
                    .setNodeType("cm:content")
                    .setProperties(Map.of("cm:name", "test.txt"))
                    .build();

        EventData<NodeResource> eventData = EventData.<NodeResource>builder().setPrincipal("john.doe")
                    .setEventGroupId(getUUID())
                    .setResource(resource)
                    .build();

        RepoEvent<NodeResource> repoEvent = RepoEvent.<NodeResource>builder().setId(getUUID())
                    .setSource(getSource())
                    .setTime(ZonedDateTime.now())
                    .setType("org.alfresco.event.node.Created")
                    .setData(eventData)
                    .build();

        String result = OBJECT_MAPPER.writeValueAsString(repoEvent);
        String expectedJson = TestUtil.getResourceFileAsString("NodeCreatedEvent.json");
        // Compare the Json files
        checkExpectedJsonBody(expectedJson, result);
    }

    @Test
    public void nodeCreatedEvent_unmarshalling() throws Exception
    {
        String nodeCreatedEventJson = TestUtil.getResourceFileAsString("NodeCreatedEvent.json");
        assertNotNull(nodeCreatedEventJson);
        RepoEvent<NodeResource> result = OBJECT_MAPPER.readValue(nodeCreatedEventJson, new TypeReference<>()
        {
        });

        NodeResource resource = NodeResource.builder()
                    .setId("4989fb57-68f5-42a7-82ed-27fcda8d3e16")
                    .setPrimaryHierarchy(getTestNodePrimaryHierarchy())
                    .setIsFile(true)
                    .setIsFolder(false)
                    .setNodeType("cm:content")
                    .setProperties(Map.of("cm:name", "test.txt"))
                    .build();

        EventData<NodeResource> eventData = EventData.<NodeResource>builder().setEventGroupId(
                    "db5375a8-a4f2-4fda-bef7-f1caea11bc64").setPrincipal("john.doe").setResource(resource).build();

        RepoEvent<NodeResource> repoEvent = RepoEvent.<NodeResource>builder().setId(
                    "56ef7418-c615-4572-8f83-27e76e622333")
                    .setSource(getSource())
                    .setTime(parseTime("2020-03-19T09:20:42.200386Z"))
                    .setType("org.alfresco.event.node.Created")
                    .setData(eventData)
                    .build();

        assertEquals(repoEvent, result);
    }

    @Test
    public void nodeCreatedEvent_withSubject_marshalling() throws Exception
    {
        NodeResource resource = NodeResource.builder()
                    .setId(getUUID())
                    .setPrimaryHierarchy(getTestNodePrimaryHierarchy())
                    .setIsFile(false)
                    .setIsFolder(true)
                    .setNodeType("cm:folder")
                    .setProperties(Map.of("cm:name", "testFolder", "cm:description", "test folder description."))
                    .build();

        EventData<NodeResource> eventData = EventData.<NodeResource>builder().setPrincipal("jane.doe")
                    .setEventGroupId(getUUID())
                    .setResource(resource)
                    .build();

        RepoEvent<NodeResource> repoEvent = RepoEvent.<NodeResource>builder().setId(getUUID())
                    .setSource(getSource())
                    .setTime(ZonedDateTime.now())
                    .setType("org.alfresco.event.node.Created")
                    .setSubject("testFolder")
                    .setData(eventData)
                    .build();

        String result = OBJECT_MAPPER.writeValueAsString(repoEvent);
        String expectedJson = TestUtil.getResourceFileAsString("NodeCreatedEvent-withSubject.json");
        // Compare the Json files
        checkExpectedJsonBody(expectedJson, result);
    }

    @Test
    public void nodeCreatedEvent_withSubject_unmarshalling() throws Exception
    {
        String nodeCreatedEventJson = TestUtil.getResourceFileAsString("NodeCreatedEvent-withSubject.json");
        assertNotNull(nodeCreatedEventJson);
        RepoEvent<NodeResource> result = OBJECT_MAPPER.readValue(nodeCreatedEventJson, new TypeReference<>()
        {
        });

        NodeResource expectedResource = NodeResource.builder()
                    .setId("5b3f42b3-82ff-41a1-b176-88bc1e385d52")
                    .setPrimaryHierarchy(getTestNodePrimaryHierarchy())
                    .setIsFile(false)
                    .setIsFolder(true)
                    .setNodeType("cm:folder")
                    .setProperties(Map.of("cm:name", "testFolder", "cm:description", "test folder description."))
                    .build();

        EventData<NodeResource> expectedEventData = EventData.<NodeResource>builder().setEventGroupId(
                    "fd291980-96b0-4433-9f9a-06a5df1810d3").setPrincipal("jane.doe").setResource(expectedResource).build();

        RepoEvent<NodeResource> expectedRepoEvent = RepoEvent.<NodeResource>builder().setId(
                    "c99a997e-f62e-48f8-82a4-30a19edac1eb")
                    .setSource(getSource())
                    .setTime(parseTime("2020-03-19T09:50:12.201546Z"))
                    .setType("org.alfresco.event.node.Created")
                    .setSubject("testFolder")
                    .setData(expectedEventData)
                    .build();

        assertEquals(expectedRepoEvent, result);
    }

    @Test
    public void nodeUpdatedEvent_marshalling() throws Exception
    {
        NodeResource resource = NodeResource.builder()
                    .setId(getUUID())
                    .setPrimaryHierarchy(getTestNodePrimaryHierarchy())
                    .setIsFile(false)
                    .setIsFolder(true)
                    .setNodeType("cm:folder")
                    .setAspectNames(List.of("cm:titled", "cm:auditable"))
                    .setAffectedPropertiesBefore(
                                Map.of("cm:name", "testFolder", "cm:description", "test folder description."))
                    .setAffectedPropertiesAfter(
                                Map.of("cm:name", "testFolder", "cm:description", "updated test folder description.",
                                       "cm:title", "test title added"))
                    .build();

        EventData<NodeResource> eventData = EventData.<NodeResource>builder().setPrincipal("john.doe")
                    .setEventGroupId(getUUID())
                    .setResource(resource)
                    .build();

        RepoEvent<NodeResource> repoEvent = RepoEvent.<NodeResource>builder().setId(getUUID())
                    .setSource(getSource())
                    .setTime(ZonedDateTime.now())
                    .setType("org.alfresco.event.node.Updated")
                    .setSubject("testFolder")
                    .setData(eventData)
                    .build();

        String result = OBJECT_MAPPER.writeValueAsString(repoEvent);
        String expectedJson = TestUtil.getResourceFileAsString("NodeUpdatedEvent.json");
        // Compare the Json files
        checkExpectedJsonBody(expectedJson, result);
    }

    @Test
    public void nodeUpdatedEvent_unmarshalling() throws Exception
    {
        String nodeUpdatedEventJson = TestUtil.getResourceFileAsString("NodeUpdatedEvent.json");
        assertNotNull(nodeUpdatedEventJson);
        RepoEvent<NodeResource> result = OBJECT_MAPPER.readValue(nodeUpdatedEventJson, new TypeReference<>()
        {
        });

        NodeResource expectedResource = NodeResource.builder()
                    .setId("c73a5a61-59e5-420a-965e-81c6312d8a83")
                    .setPrimaryHierarchy(getTestNodePrimaryHierarchy())
                    .setIsFile(false)
                    .setIsFolder(true)
                    .setNodeType("cm:folder")
                    .setAspectNames(List.of("cm:titled", "cm:auditable"))
                    .setAffectedPropertiesBefore(
                                Map.of("cm:name", "testFolder", "cm:description", "test folder description."))
                    .setAffectedPropertiesAfter(
                                Map.of("cm:name", "testFolder", "cm:description", "updated test folder description.",
                                       "cm:title", "test title added"))
                    .build();

        EventData<NodeResource> expectedEventData = EventData.<NodeResource>builder().setPrincipal("john.doe")
                    .setEventGroupId("81fe60c1-6855-4d71-8450-130901d54f81")
                    .setResource(expectedResource)
                    .build();

        RepoEvent<NodeResource> expectedRepoEvent = RepoEvent.<NodeResource>builder().setId(
                    "8128d7e5-9537-4d18-84cd-b3d532c3c7c4")
                    .setSource(getSource())
                    .setTime(parseTime("2020-03-26T14:35:39.532821Z"))
                    .setType("org.alfresco.event.node.Updated")
                    .setSubject("testFolder")
                    .setData(expectedEventData)
                    .build();

        assertEquals(expectedRepoEvent, result);
    }

    private void checkExpectedJsonBody(String expectedJsonBody, String actualJsonBody) throws Exception
    {
        JSONAssert.assertEquals(expectedJsonBody, actualJsonBody, UUID_JSON_COMPARATOR);
    }
}