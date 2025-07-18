/*
 * #%L
 * Alfresco Repository
 * %%
 * Copyright (C) 2005 - 2025 Alfresco Software Limited
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

import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import static org.alfresco.repo.event.util.TestUtil.OBJECT_MAPPER;
import static org.alfresco.repo.event.util.TestUtil.checkExpectedJsonBody;
import static org.alfresco.repo.event.util.TestUtil.getDataSchema;
import static org.alfresco.repo.event.util.TestUtil.getSecondaryParents;
import static org.alfresco.repo.event.util.TestUtil.getSource;
import static org.alfresco.repo.event.util.TestUtil.getTestNodePrimaryHierarchy;
import static org.alfresco.repo.event.util.TestUtil.getUUID;
import static org.alfresco.repo.event.util.TestUtil.parseTime;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.alfresco.repo.event.util.RandomModelGenerator;
import org.alfresco.repo.event.util.TestUtil;

/**
 * @author Jamal Kaabi-Mofrad
 */
public class EventTest
{
    @Test
    public void nodeCreatedEvent_marshalling() throws Exception
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
                .setLocalizedProperties(Map.of("cm:description",
                        Map.of(GERMAN.getLanguage(), "ruf mich an", ENGLISH.getLanguage(), "call me")))
                .setAspectNames(Set.of("cm:titled", "cm:auditable"))
                .setContent(new ContentInfo("text/plain", 16L, "UTF-8"))
                .setSecondaryParents(getSecondaryParents())
                .build();

        EventData<NodeResource> eventData = EventData.<NodeResource> builder()
                .setEventGroupId(getUUID())
                .setResource(resource)
                .build();

        RepoEvent<EventData<NodeResource>> repoEvent = RepoEvent.<EventData<NodeResource>> builder().setId(getUUID())
                .setSource(getSource())
                .setTime(ZonedDateTime.now())
                .setType(EventType.NODE_CREATED.getType())
                .setData(eventData)
                .setDataschema(getDataSchema("nodeCreated"))
                .build();

        String result = OBJECT_MAPPER.writeValueAsString(repoEvent);
        String expectedJson = TestUtil.getResourceFileAsString("noAuth/NodeCreatedEvent.json");
        // Compare the Json files
        checkExpectedJsonBody(expectedJson, result);
    }

    @Test
    public void nodeCreatedEvent_unmarshalling() throws Exception
    {
        String nodeCreatedEventJson = TestUtil.getResourceFileAsString("noAuth/NodeCreatedEvent.json");
        assertNotNull(nodeCreatedEventJson);
        RepoEvent<EventData<NodeResource>> result = OBJECT_MAPPER.readValue(nodeCreatedEventJson, new TypeReference<>() {});

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
                .setLocalizedProperties(Map.of("cm:description",
                        Map.of(GERMAN.getLanguage(), "ruf mich an", ENGLISH.getLanguage(), "call me")))
                .setAspectNames(Set.of("cm:titled", "cm:auditable"))
                .setContent(new ContentInfo("text/plain", 16L, "UTF-8"))
                .setSecondaryParents(getSecondaryParents())
                .build();

        EventData<NodeResource> eventData = EventData.<NodeResource> builder().setEventGroupId(
                "cb645043-e7d2-4e51-b61d-e6d01582cbab")
                .setResource(resource).build();

        RepoEvent<EventData<NodeResource>> repoEvent = RepoEvent.<EventData<NodeResource>> builder().setId(
                "97c1b36c-c569-4c66-8a31-7a8d0b6b804a")
                .setSource(getSource())
                .setTime(parseTime("2020-04-27T12:37:03.560134+01:00"))
                .setType(EventType.NODE_CREATED.getType())
                .setData(eventData)
                .setDataschema(getDataSchema("nodeCreated"))
                .build();

        assertEquals(repoEvent, result);
    }

    @Test
    public void nodeUpdatedEvent_marshalling() throws Exception
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
                .setProperties(Map.of("cm:title", "test title2", "cm:description", "test description."))
                .setLocalizedProperties(Map.of("cm:description",
                        Map.of(GERMAN.getLanguage(), "ruf mich an", ENGLISH.getLanguage(), "call me")))
                .setAspectNames(Set.of("cm:titled", "cm:auditable"))
                .setContent(new ContentInfo("text/plain", 16L, "UTF-8"))
                .setSecondaryParents(getSecondaryParents())
                .build();

        NodeResource resourceBefore = NodeResource.builder()
                .setModifiedByUser(new UserInfo("jane.doe", "Jane", "Doe"))
                .setModifiedAt(ZonedDateTime.now())
                .setProperties(Map.of("cm:title", "test title"))
                .setLocalizedProperties(Map.of("cm:description", Map.of(GERMAN.getLanguage(), "ruf mi an")))
                .build();

        EventData<NodeResource> eventData = EventData.<NodeResource> builder()
                .setEventGroupId(getUUID())
                .setResource(resource)
                .setResourceBefore(resourceBefore)
                .build();

        RepoEvent<EventData<NodeResource>> repoEvent = RepoEvent.<EventData<NodeResource>> builder().setId(getUUID())
                .setSource(getSource())
                .setTime(ZonedDateTime.now())
                .setType(EventType.NODE_UPDATED.getType())
                .setData(eventData)
                .setDataschema(getDataSchema("nodeUpdated"))
                .build();

        String result = OBJECT_MAPPER.writeValueAsString(repoEvent);
        String expectedJson = TestUtil.getResourceFileAsString("noAuth/NodeUpdatedEvent.json");
        // Compare the Json files
        checkExpectedJsonBody(expectedJson, result);
    }

    @Test
    public void nodeUpdatedEvent_unmarshalling() throws Exception
    {
        String nodeUpdatedEventJson = TestUtil.getResourceFileAsString("noAuth/NodeUpdatedEvent.json");
        assertNotNull(nodeUpdatedEventJson);
        RepoEvent<EventData<NodeResource>> result = OBJECT_MAPPER.readValue(nodeUpdatedEventJson, new TypeReference<>() {});

        NodeResource expectedResource = NodeResource.builder()
                .setId("d366f805-853f-46ac-a81c-af9c257ee876")
                .setName("testFile.txt")
                .setPrimaryHierarchy(getTestNodePrimaryHierarchy())
                .setIsFile(true)
                .setIsFolder(false)
                .setNodeType("cm:content")
                .setPrimaryAssocQName("cm:testFile.txt")
                .setCreatedByUser(new UserInfo("john.doe", "John", "Doe"))
                .setCreatedAt(parseTime("2020-04-27T14:25:59.852475+01:00"))
                .setModifiedByUser(new UserInfo("jane.doe", "Jane", "Doe"))
                .setModifiedAt(parseTime("2020-04-27T14:25:59.854153+01:00"))
                .setProperties(Map.of("cm:title", "test title2", "cm:description", "test description."))
                .setLocalizedProperties(Map.of("cm:description",
                        Map.of(GERMAN.getLanguage(), "ruf mich an", ENGLISH.getLanguage(), "call me")))
                .setAspectNames(Set.of("cm:titled", "cm:auditable"))
                .setContent(new ContentInfo("text/plain", 16L, "UTF-8"))
                .setSecondaryParents(getSecondaryParents())
                .build();

        NodeResource expectedResourceBefore = NodeResource.builder()
                .setModifiedByUser(new UserInfo("jane.doe", "Jane", "Doe"))
                .setModifiedAt(parseTime("2020-04-27T14:25:59.854558+01:00"))
                .setProperties(Map.of("cm:title", "test title"))
                .setLocalizedProperties(Map.of("cm:description", Map.of(GERMAN.getLanguage(), "ruf mi an")))
                .build();

        EventData<NodeResource> expectedEventData = EventData.<NodeResource> builder()
                .setEventGroupId("ce852a52-609e-4b7d-8438-04e211fd76a0")
                .setResource(expectedResource)
                .setResourceBefore(expectedResourceBefore)
                .build();

        RepoEvent<EventData<NodeResource>> expectedRepoEvent = RepoEvent.<EventData<NodeResource>> builder().setId(
                "df708027-e0a8-4b30-92a5-0d19235a7800")
                .setSource(getSource())
                .setTime(parseTime("2020-04-27T14:25:59.855866+01:00"))
                .setType(EventType.NODE_UPDATED.getType())
                .setData(expectedEventData)
                .setDataschema(getDataSchema("nodeUpdated"))
                .build();

        assertEquals(expectedRepoEvent, result);
    }

    @Test
    public void childAssocCreatedEvent_marshalling() throws Exception
    {
        ChildAssociationResource childAssocResource = new ChildAssociationResource(getUUID(),
                getUUID(),
                "cm:contains",
                "cm:213131-1234-1235-5432-12039400a121");

        EventData<ChildAssociationResource> eventData = EventData.<ChildAssociationResource> builder()
                .setEventGroupId(getUUID())
                .setResource(childAssocResource)
                .build();

        RepoEvent<EventData<ChildAssociationResource>> repoEvent = RepoEvent.<EventData<ChildAssociationResource>> builder()
                .setId(getUUID())
                .setSource(getSource())
                .setTime(ZonedDateTime.now())
                .setType(EventType.CHILD_ASSOC_CREATED.getType())
                .setData(eventData)
                .setDataschema(getDataSchema("childAssocCreated"))
                .build();

        String result = OBJECT_MAPPER.writeValueAsString(repoEvent);
        String expectedJson = TestUtil.getResourceFileAsString("noAuth/ChildAssocCreated.json");
        // Compare the Json files
        checkExpectedJsonBody(expectedJson, result);
    }

    @Test
    public void childAssocCreatedEvent_unmarshalling() throws Exception
    {
        String childAssocCreatedEventJson = TestUtil.getResourceFileAsString("noAuth/ChildAssocCreated.json");
        assertNotNull(childAssocCreatedEventJson);
        RepoEvent<EventData<ChildAssociationResource>> result = OBJECT_MAPPER.readValue(childAssocCreatedEventJson, new TypeReference<>() {});

        ChildAssociationResource expectedResource = new ChildAssociationResource("7624edb6-6f28-4130-a4fb-4a5362807a05",
                "5d9f3d6f-9802-4bb6-8ad3-0f9261d08894",
                "cm:contains",
                "cm:213131-1234-1235-5432-12039400a121");

        EventData<ChildAssociationResource> expectedEventData = EventData.<ChildAssociationResource> builder()
                .setEventGroupId("d933857e-5f4f-406e-aa5f-c747901f5a8f")
                .setResource(expectedResource)
                .build();

        RepoEvent<EventData<ChildAssociationResource>> expectedRepoEvent = RepoEvent.<EventData<ChildAssociationResource>> builder()
                .setId("928c28fc-da23-441d-bb8b-72b068a9ceb5")
                .setSource(getSource())
                .setTime(parseTime("2020-05-28T09:56:52.235411+01:00"))
                .setType(EventType.CHILD_ASSOC_CREATED.getType())
                .setData(expectedEventData)
                .setDataschema(getDataSchema("childAssocCreated"))
                .build();

        assertEquals(expectedRepoEvent, result);
    }

    @Test
    public void peerAssocCreatedEvent_marshalling() throws Exception
    {
        PeerAssociationResource peerAssocResource = new PeerAssociationResource(getUUID(), getUUID(), "cm:references");

        EventData<PeerAssociationResource> eventData = EventData.<PeerAssociationResource> builder()
                .setEventGroupId(getUUID())
                .setResource(peerAssocResource)
                .build();

        RepoEvent<EventData<PeerAssociationResource>> repoEvent = RepoEvent.<EventData<PeerAssociationResource>> builder()
                .setId(getUUID())
                .setSource(getSource())
                .setTime(ZonedDateTime.now())
                .setType(EventType.PEER_ASSOC_CREATED.getType())
                .setData(eventData)
                .setDataschema(getDataSchema("peerAssocCreated"))
                .build();

        String result = OBJECT_MAPPER.writeValueAsString(repoEvent);
        String expectedJson = TestUtil.getResourceFileAsString("noAuth/PeerAssocCreated.json");
        // Compare the Json files
        checkExpectedJsonBody(expectedJson, result);
    }

    @Test
    public void peerAssocCreatedEvent_unmarshalling() throws Exception
    {
        String peerAssocCreatedEventJson = TestUtil.getResourceFileAsString("noAuth/PeerAssocCreated.json");
        assertNotNull(peerAssocCreatedEventJson);
        RepoEvent<EventData<PeerAssociationResource>> result = OBJECT_MAPPER.readValue(peerAssocCreatedEventJson, new TypeReference<>() {});

        PeerAssociationResource expectedResource = new PeerAssociationResource("d323671c-9bd0-483e-8471-bd64cea73c09",
                "595c55b0-64b3-4f1b-95f8-c1cc6b3843a2",
                "cm:references");

        EventData<PeerAssociationResource> expectedEventData = EventData.<PeerAssociationResource> builder()
                .setEventGroupId("b23f75d0-45da-40d1-9063-eb800a4f2444")
                .setResource(expectedResource)
                .build();

        RepoEvent<EventData<PeerAssociationResource>> expectedRepoEvent = RepoEvent.<EventData<PeerAssociationResource>> builder()
                .setId("8677a369-a30d-469e-b313-e5bf1b590c8e")
                .setSource(getSource())
                .setTime(parseTime("2020-05-28T10:36:21.291392+01:00"))
                .setType(EventType.PEER_ASSOC_CREATED.getType())
                .setData(expectedEventData)
                .setDataschema(getDataSchema("peerAssocCreated"))
                .build();

        assertEquals(expectedRepoEvent, result);
    }

    @Test
    public void nodeCreatedWithAuthoritiesEvent_marshalling() throws Exception
    {
        NodeResource resource = NodeResource.builder()
                .setId(getUUID())
                .setName("testFile.txt")
                .setPrimaryHierarchy(getTestNodePrimaryHierarchy())
                .setIsFile(true)
                .setIsFolder(false)
                .setNodeType("cm:content")
                .setPrimaryAssocQName("cm:testFile.txt")
                .setPrimaryAssocQName("cm:testFile.txt")
                .setCreatedByUser(new UserInfo("john.doe", "John", "Doe"))
                .setCreatedAt(ZonedDateTime.now())
                .setModifiedByUser(new UserInfo("jane.doe", "Jane", "Doe"))
                .setModifiedAt(ZonedDateTime.now())
                .setProperties(Map.of("cm:title", "test title"))
                .setAspectNames(Set.of("cm:titled", "cm:auditable"))
                .setContent(new ContentInfo("text/plain", 16L, "UTF-8"))
                .build();

        EventData<NodeResource> eventData = EventData.<NodeResource> builder().setEventGroupId(getUUID())
                .setResource(resource)
                .setResourceReaderAuthorities(Set.of("GROUP_EVERYONE", "GROUP_ONE", "GROUP_TWO"))
                .setResourceDeniedAuthorities(Set.of())
                .build();

        RepoEvent<EventData<NodeResource>> repoEvent = RepoEvent.<EventData<NodeResource>> builder().setId(getUUID())
                .setSource(getSource())
                .setTime(ZonedDateTime.now())
                .setType("org.alfresco.event.node.Created")
                .setData(eventData)
                .setDataschema(getDataSchema("nodeCreated"))
                .build();

        String result = OBJECT_MAPPER.writeValueAsString(repoEvent);
        String expectedJson = TestUtil.getResourceFileAsString("auth/NodeCreatedEvent.json");
        // Compare the Json files
        checkExpectedJsonBody(expectedJson, result);
    }

    @Test
    public void nodeCreatedWithAuthoritiesEvent_unmarshalling() throws Exception
    {
        String nodeCreatedEventJson = TestUtil.getResourceFileAsString("auth/NodeCreatedEvent.json");
        assertNotNull(nodeCreatedEventJson);
        RepoEvent<EventData<NodeResource>> result = OBJECT_MAPPER.readValue(nodeCreatedEventJson, new TypeReference<>() {});

        NodeResource resource = NodeResource.builder()
                .setId("7491120a-e2cb-478f-8599-ebf057cc0c7c")
                .setName("testFile.txt")
                .setPrimaryHierarchy(getTestNodePrimaryHierarchy())
                .setIsFile(true)
                .setIsFolder(false)
                .setNodeType("cm:content")
                .setCreatedByUser(new UserInfo("john.doe", "John", "Doe"))
                .setCreatedAt(parseTime("2020-06-10T12:37:03.555624+01:00"))
                .setModifiedByUser(new UserInfo("jane.doe", "Jane", "Doe"))
                .setModifiedAt(parseTime("2020-06-10T12:37:03.557956+01:00"))
                .setProperties(Map.of("cm:title", "test title"))
                .setAspectNames(Set.of("cm:titled", "cm:auditable"))
                .setContent(new ContentInfo("text/plain", 16L, "UTF-8"))
                .build();

        EventData<NodeResource> eventData = EventData.<NodeResource> builder().setEventGroupId("cb645043-e7d2-4e51-b61d-e6d01582cbab")
                .setResource(resource)
                .setResourceReaderAuthorities(Set.of("GROUP_EVERYONE", "GROUP_ONE", "GROUP_TWO"))
                .setResourceDeniedAuthorities(Set.of())
                .build();

        RepoEvent<EventData<NodeResource>> repoEvent = RepoEvent.<EventData<NodeResource>> builder().setId("97c1b36c-c569-4c66-8a31-7a8d0b6b804a")
                .setSource(getSource())
                .setTime(parseTime("2020-06-10T12:37:03.560134+01:00"))
                .setType("org.alfresco.event.node.Created")
                .setData(eventData)
                .setDataschema(getDataSchema("nodeCreated"))
                .build();

        assertEquals(repoEvent, result);
    }

    @Test
    public void nodeUpdatedWithAuthoritiesEvent_marshalling() throws Exception
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
                .setProperties(Map.of("cm:title", "test title2", "cm:description", "test description."))
                .setAspectNames(Set.of("cm:titled", "cm:auditable"))
                .setContent(new ContentInfo("text/plain", 16L, "UTF-8"))
                .build();

        NodeResource resourceBefore = NodeResource.builder()
                .setModifiedByUser(new UserInfo("jane.doe", "Jane", "Doe"))
                .setModifiedAt(ZonedDateTime.now())
                .setProperties(Map.of("cm:title", "test title"))
                .build();

        EventData<NodeResource> eventData = EventData.<NodeResource> builder()
                .setEventGroupId(getUUID())
                .setResource(resource)
                .setResourceBefore(resourceBefore)
                .setResourceReaderAuthorities(Set.of("GROUP_ONE", "GROUP_THREE"))
                .setResourceDeniedAuthorities(Set.of("GROUP_TWO"))
                .setResourceReaderSecurityControls(Set.of("e428663f-9171-42e2-b233-d8f2cd3f54f7:mF2foUuA"))
                .build();

        RepoEvent<EventData<NodeResource>> repoEvent = RepoEvent.<EventData<NodeResource>> builder().setId(getUUID())
                .setSource(getSource())
                .setTime(ZonedDateTime.now())
                .setType("org.alfresco.event.node.Updated")
                .setData(eventData)
                .setDataschema(getDataSchema("nodeUpdated"))
                .build();

        String result = OBJECT_MAPPER.writeValueAsString(repoEvent);
        String expectedJson = TestUtil.getResourceFileAsString("auth/NodeUpdatedEvent.json");
        // Compare the Json files
        checkExpectedJsonBody(expectedJson, result);
    }

    @Test
    public void nodeUpdatedWithAuthoritiesEvent_unmarshalling() throws Exception
    {
        String nodeUpdatedEventJson = TestUtil.getResourceFileAsString("auth/NodeUpdatedEvent.json");
        assertNotNull(nodeUpdatedEventJson);
        RepoEvent<EventData<NodeResource>> result = OBJECT_MAPPER.readValue(nodeUpdatedEventJson, new TypeReference<>() {});

        NodeResource expectedResource = NodeResource.builder()
                .setId("d366f805-853f-46ac-a81c-af9c257ee876")
                .setName("testFile.txt")
                .setPrimaryHierarchy(getTestNodePrimaryHierarchy())
                .setIsFile(true)
                .setIsFolder(false)
                .setNodeType("cm:content")
                .setCreatedByUser(new UserInfo("john.doe", "John", "Doe"))
                .setCreatedAt(parseTime("2020-06-10T14:25:59.852475+01:00"))
                .setModifiedByUser(new UserInfo("jane.doe", "Jane", "Doe"))
                .setModifiedAt(parseTime("2020-06-10T14:25:59.854153+01:00"))
                .setProperties(Map.of("cm:title", "test title2", "cm:description", "test description."))
                .setAspectNames(Set.of("cm:titled", "cm:auditable"))
                .setContent(new ContentInfo("text/plain", 16L, "UTF-8"))
                .build();

        NodeResource expectedResourceBefore = NodeResource.builder()
                .setModifiedByUser(new UserInfo("jane.doe", "Jane", "Doe"))
                .setModifiedAt(parseTime("2020-06-10T14:25:59.854558+01:00"))
                .setProperties(Map.of("cm:title", "test title"))
                .build();

        EventData<NodeResource> expectedEventData = EventData.<NodeResource> builder()
                .setEventGroupId("ce852a52-609e-4b7d-8438-04e211fd76a0")
                .setResource(expectedResource)
                .setResourceBefore(expectedResourceBefore)
                .setResourceReaderAuthorities(Set.of("GROUP_ONE", "GROUP_THREE"))
                .setResourceDeniedAuthorities(Set.of("GROUP_TWO"))
                .setResourceReaderSecurityControls(Set.of("e428663f-9171-42e2-b233-d8f2cd3f54f7:mF2foUuA"))
                .build();

        RepoEvent<EventData<NodeResource>> expectedRepoEvent = RepoEvent.<EventData<NodeResource>> builder().setId(
                "df708027-e0a8-4b30-92a5-0d19235a7800")
                .setSource(getSource())
                .setTime(parseTime("2020-06-10T14:25:59.855866+01:00"))
                .setType("org.alfresco.event.node.Updated")
                .setData(expectedEventData)
                .setDataschema(getDataSchema("nodeUpdated"))
                .build();

        assertEquals(expectedRepoEvent, result);
    }

    @Test
    public void childAssocCreatedWithAuthoritiesEvent_marshalling() throws Exception
    {
        ChildAssociationResource childAssocResource = new ChildAssociationResource(getUUID(), getUUID(), "cm:contains", "cm:213131-1234-1235-5432-12039400a121");

        EventData<ChildAssociationResource> eventData = EventData.<ChildAssociationResource> builder()
                .setEventGroupId(getUUID())
                .setResource(childAssocResource)
                .setResourceReaderAuthorities(Set.of("GROUP_EVERYONE", "GROUP_ONE"))
                .setResourceDeniedAuthorities(Set.of("GROUP_TWO"))
                .build();

        RepoEvent<EventData<ChildAssociationResource>> repoEvent = RepoEvent.<EventData<ChildAssociationResource>> builder()
                .setId(getUUID())
                .setSource(getSource())
                .setTime(ZonedDateTime.now())
                .setType("org.alfresco.event.assoc.child.Created")
                .setData(eventData)
                .setDataschema(getDataSchema("childAssocCreated"))
                .build();

        String result = OBJECT_MAPPER.writeValueAsString(repoEvent);
        String expectedJson = TestUtil.getResourceFileAsString("auth/ChildAssocCreated.json");
        // Compare the Json files
        checkExpectedJsonBody(expectedJson, result);
    }

    @Test
    public void childAssocCreatedWithAuthoritiesEvent_unmarshalling() throws Exception
    {
        String childAssocCreatedEventJson = TestUtil.getResourceFileAsString("auth/ChildAssocCreated.json");
        assertNotNull(childAssocCreatedEventJson);
        RepoEvent<EventData<ChildAssociationResource>> result = OBJECT_MAPPER.readValue(childAssocCreatedEventJson, new TypeReference<>() {});

        ChildAssociationResource expectedResource = new ChildAssociationResource("7624edb6-6f28-4130-a4fb-4a5362807a05",
                "5d9f3d6f-9802-4bb6-8ad3-0f9261d08894",
                "cm:contains",
                "cm:213131-1234-1235-5432-12039400a121");

        EventData<ChildAssociationResource> expectedEventData = EventData.<ChildAssociationResource> builder()
                .setEventGroupId("d933857e-5f4f-406e-aa5f-c747901f5a8f")
                .setResource(expectedResource)
                .setResourceReaderAuthorities(Set.of("GROUP_EVERYONE", "GROUP_ONE"))
                .setResourceDeniedAuthorities(Set.of("GROUP_TWO"))
                .build();

        RepoEvent<EventData<ChildAssociationResource>> expectedRepoEvent = RepoEvent.<EventData<ChildAssociationResource>> builder()
                .setId("928c28fc-da23-441d-bb8b-72b068a9ceb5")
                .setSource(getSource())
                .setTime(parseTime("2020-06-10T09:56:52.235411+01:00"))
                .setType("org.alfresco.event.assoc.child.Created")
                .setData(expectedEventData)
                .setDataschema(getDataSchema("childAssocCreated"))
                .build();

        assertEquals(expectedRepoEvent, result);
    }

    @Test
    public void peerAssocCreatedWithAuthoritiesEvent_marshalling() throws Exception
    {
        PeerAssociationResource peerAssocResource = new PeerAssociationResource(getUUID(), getUUID(), "cm:references");

        EventData<PeerAssociationResource> eventData = EventData.<PeerAssociationResource> builder()
                .setEventGroupId(getUUID())
                .setResource(peerAssocResource)
                .setResourceReaderAuthorities(Set.of())
                .setResourceDeniedAuthorities(Set.of("GROUP_TWO"))
                .build();

        RepoEvent<EventData<PeerAssociationResource>> repoEvent = RepoEvent.<EventData<PeerAssociationResource>> builder()
                .setId(getUUID())
                .setSource(getSource())
                .setTime(ZonedDateTime.now())
                .setType("org.alfresco.event.assoc.peer.Created")
                .setData(eventData)
                .setDataschema(getDataSchema("peerAssocCreated"))
                .build();

        String result = OBJECT_MAPPER.writeValueAsString(repoEvent);
        String expectedJson = TestUtil.getResourceFileAsString("auth/PeerAssocCreated.json");
        // Compare the Json files
        checkExpectedJsonBody(expectedJson, result);
    }

    @Test
    public void peerAssocCreatedWithAuthoritiesEvent_unmarshalling() throws Exception
    {
        String peerAssocCreatedEventJson = TestUtil.getResourceFileAsString("auth/PeerAssocCreated.json");
        assertNotNull(peerAssocCreatedEventJson);
        RepoEvent<EventData<PeerAssociationResource>> result = OBJECT_MAPPER.readValue(peerAssocCreatedEventJson, new TypeReference<>() {});

        PeerAssociationResource expectedResource = new PeerAssociationResource("d323671c-9bd0-483e-8471-bd64cea73c09",
                "595c55b0-64b3-4f1b-95f8-c1cc6b3843a2",
                "cm:references");

        EventData<PeerAssociationResource> expectedEventData = EventData.<PeerAssociationResource> builder()
                .setEventGroupId("b23f75d0-45da-40d1-9063-eb800a4f2444")
                .setResource(expectedResource)
                .setResourceReaderAuthorities(Set.of())
                .setResourceDeniedAuthorities(Set.of("GROUP_TWO"))
                .build();

        RepoEvent<EventData<PeerAssociationResource>> expectedRepoEvent = RepoEvent.<EventData<PeerAssociationResource>> builder()
                .setId("8677a369-a30d-469e-b313-e5bf1b590c8e")
                .setSource(getSource())
                .setTime(parseTime("2020-06-10T10:36:21.291392+01:00"))
                .setType("org.alfresco.event.assoc.peer.Created")
                .setData(expectedEventData)
                .setDataschema(getDataSchema("peerAssocCreated"))
                .build();

        assertEquals(expectedRepoEvent, result);
    }

    @Test
    public void auditEntryEvent_marshalling() throws Exception
    {
        Map<String, ?> auditData = Map.of(
                "somekey", "somevalue",
                "anotherkey", new ArrayList<>(List.of("firstvalue", "secondvalue")));

        EventData<AuditEntryResource> eventData = EventData.<AuditEntryResource> builder()
                .setEventGroupId(getUUID())
                .setResource(new AuditEntryResource("auditedApp", auditData))
                .build();

        RepoEvent<EventData<AuditEntryResource>> repoEvent = RepoEvent.<EventData<AuditEntryResource>> builder()
                .setId(getUUID())
                .setSource(getSource())
                .setTime(ZonedDateTime.now())
                .setType("org.alfresco.event.audit.Created")
                .setData(eventData)
                .setDataschema(getDataSchema("auditEntryCreated"))
                .build();

        String result = OBJECT_MAPPER.writeValueAsString(repoEvent);
        String expectedJson = TestUtil.getResourceFileAsString("AuditEntryCreated.json");
        // Compare the Json files
        checkExpectedJsonBody(expectedJson, result);
    }

    @Test
    public void auditRecordEvent_marshalling() throws Exception
    {
        Map<String, ?> auditData = Map.of(
                "somekey", "somevalue",
                "anotherkey", new ArrayList<>(List.of("firstvalue", "secondvalue")));

        EventData<AuditRecordResource> eventData = EventData.<AuditRecordResource> builder()
                .setEventGroupId(getUUID())
                .setResource(new AuditRecordResource("auditedApp", auditData))
                .build();

        RepoEvent<EventData<AuditRecordResource>> repoEvent = RepoEvent.<EventData<AuditRecordResource>> builder()
                .setId(getUUID())
                .setSource(getSource())
                .setTime(ZonedDateTime.now())
                .setType("org.alfresco.event.audit.entryCreated")
                .setData(eventData)
                .setDataschema(getDataSchema("auditEntryCreated"))
                .build();

        String result = OBJECT_MAPPER.writeValueAsString(repoEvent);
        String expectedJson = TestUtil.getResourceFileAsString("AuditRecordResource.json");
        // Compare the Json files
        checkExpectedJsonBody(expectedJson, result);
    }

    @Test
    public void auditEntryEvent_unmarshalling() throws Exception
    {
        String auditEntryEventJson = TestUtil.getResourceFileAsString("AuditEntryCreated.json");
        assertNotNull(auditEntryEventJson);
        RepoEvent<EventData<AuditEntryResource>> result = OBJECT_MAPPER.readValue(auditEntryEventJson, new TypeReference<>() {});

        Map<String, Serializable> auditData = Map.of(
                "somekey", "somevalue",
                "anotherkey", new ArrayList<>(List.of("firstvalue", "secondvalue")));

        EventData<AuditEntryResource> expectedEventData = EventData.<AuditEntryResource> builder()
                .setEventGroupId("b23f75d0-45da-40d1-9063-eb800a4f2444")
                .setResource(new AuditEntryResource("auditedApp", auditData))
                .build();

        RepoEvent<EventData<AuditEntryResource>> expectedRepoEvent = RepoEvent.<EventData<AuditEntryResource>> builder()
                .setId("8877b428-da23-441d-bb8b-e5bf1b59abfe")
                .setSource(getSource())
                .setTime(parseTime("2020-06-10T09:56:52.235411+01:00"))
                .setType("org.alfresco.event.audit.Created")
                .setData(expectedEventData)
                .setDataschema(getDataSchema("auditEntryCreated"))
                .build();

        assertEquals(expectedRepoEvent, result);
    }

    @Test
    public void nonExistingResource_unmarshalling() throws Exception
    {
        String peerAssocCreatedEventJson = TestUtil.getResourceFileAsString("NonExistingResource.json");
        assertNotNull(peerAssocCreatedEventJson);
        RepoEvent<EventData<Resource>> result = OBJECT_MAPPER.readValue(peerAssocCreatedEventJson, new TypeReference<>() {});
        assertNull(result.getData().getResource());
    }

    @Test
    @DisplayName("should correctly clone RepoEvent object using toBuilder method")
    void shouldClone()
    {
        // given
        var src = RandomModelGenerator.generateRepoEvent();

        // when
        var dst = src.toBuilder().build();

        // then
        assertEquals(src, dst);
    }
}
