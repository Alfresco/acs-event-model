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
package org.alfresco.repo.event.v1.model;

import static org.alfresco.repo.event.util.TestUtil.getDataSchema;
import static org.alfresco.repo.event.util.TestUtil.parseTime;

import java.io.Serializable;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

import org.alfresco.repo.event.extension.ExtensionAttributesImpl;

class DeleteEventUtils
{
    private DeleteEventUtils()
    {}

    static Stream<Arguments> deleteEventProvider()
    {
        return Stream.of(
                Arguments.of("auth/NodeDeletedEvent.json", true, true),
                Arguments.of("noAuth/NodeDeletedEvent.json", false, false),
                Arguments.of("noAuth/NodeDeletedEventNoTrashcanSupport.json", false, null));
    }

    static RepoEvent<EventData<NodeResource>> getNodeDeleteEvent(boolean withAuth, Boolean isPermanentlyDeleted) throws Exception
    {
        var expectedResource = NodeResource.builder()
                .setId("fe554533-0fdd-4db3-9545-330fddcdb34f")
                .setPrimaryHierarchy(List.of("040abb50-88ce-4953-8abb-5088ce195375", "8c79bb21-1858-4f92-b9bb-211858ef922e"))
                .setName("test.docx")
                .setNodeType("cm:content")
                .setCreatedByUser(new UserInfo("admin", "Administrator", null))
                .setCreatedAt(parseTime("2026-08-24T12:20:16.042Z"))
                .setModifiedByUser(new UserInfo("admin", "Administrator", null))
                .setModifiedAt(parseTime("2026-08-24T12:20:16.869Z"))
                .setContent(new ContentInfo("application/vnd.openxmlformats-officedocument.wordprocessingml.document", 13571L, "UTF-8"))
                .setProperties(Map.of("cm:autoVersion", true,
                        "cm:versionType", "MAJOR",
                        "cm:versionLabel", "1.0",
                        "cm:autoVersionOnUpdateProps", false,
                        "cm:lastThumbnailModification", (Serializable) List.of("doclib:1787574017232"),
                        "cm:author", "Joe Black",
                        "cm:initialVersion", true))
                .setAspectNames(Set.of("cm:versionable", "cm:author", "cm:thumbnailModification", "cm:titled", "rn:renditioned", "cm:auditable"))
                .setPrimaryAssocQName("cm:test.docx")
                .setSecondaryParents(List.of())
                .setIsFile(true)
                .setIsFolder(false)
                .setIsPermanentlyDeleted(isPermanentlyDeleted)
                .build();

        var expectedEventDataBuilder = EventData.<NodeResource> builder().setEventGroupId("4c64ab50-93f6-42b7-a4ab-5093f632b7c5")
                .setResource(expectedResource);
        if (withAuth)
        {
            expectedEventDataBuilder
                    .setResourceReaderAuthorities(Set.of("GROUP_EVERYONE"))
                    .setResourceDeniedAuthorities(Set.of());
        }
        var expectedEventData = expectedEventDataBuilder.build();

        var extensionAttributes = new ExtensionAttributesImpl();
        extensionAttributes.addExtension("path", "/Company Home/test.docx");

        var expectedRepoEvent = RepoEvent.<EventData<NodeResource>> builder().setId("2217a7dd-4ac2-4484-abd1-b135ba3ccdb2")
                .setSource(URI.create("/bde0e2e8-6f04-447f-a0e2-e86f04547f23"))
                .setTime(parseTime("2026-08-24T12:21:32.612Z"))
                .setType(EventType.NODE_DELETED.getType())
                .setData(expectedEventData)
                .setDataschema(getDataSchema("nodeDeleted"))
                .setExtensionAttributes(extensionAttributes)
                .build();
        return expectedRepoEvent;
    }
}
