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

import static java.util.Collections.emptyList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.alfresco.repo.event.util.RandomModelGenerator;

/** Unit tests for {@link NodeResource} . */
public class NodeResourceTest
{
    @Test
    public void setSecondaryParents_populatedListShouldBeStored()
    {
        List<String> secondaryParents = List.of("parentA", "parentB");
        NodeResource.Builder nodeResourceBuilder = new NodeResource.Builder();

        nodeResourceBuilder.setSecondaryParents(secondaryParents);

        NodeResource nodeResource = nodeResourceBuilder.build();
        assertEquals(nodeResource.getSecondaryParents(), secondaryParents, "Unexpected secondary parents.");
    }

    @Test
    public void setSecondaryParents_emptyListShouldBePreserved()
    {
        NodeResource.Builder nodeResourceBuilder = new NodeResource.Builder();

        nodeResourceBuilder.setSecondaryParents(emptyList());

        NodeResource nodeResource = nodeResourceBuilder.build();
        assertEquals(nodeResource.getSecondaryParents(), emptyList(), "Expected empty list of secondary parents.");
    }

    @Test
    public void setSecondaryParents_nullListShouldBeStoredAsEmpty()
    {
        NodeResource.Builder nodeResourceBuilder = new NodeResource.Builder();

        nodeResourceBuilder.setSecondaryParents(null);

        NodeResource nodeResource = nodeResourceBuilder.build();
        assertEquals(nodeResource.getSecondaryParents(), emptyList(), "Expected empty list of secondary parents.");
    }

    @Test
    @DisplayName("should correctly clone NodeResource object using toBuilder method")
    void shouldClone()
    {
        // given
        var src = RandomModelGenerator.generateNodeResource();

        // when
        var dst = src.toBuilder().build();

        // then
        assertEquals(src, dst);
    }

    @Test
    void addProperties_nullOnUnsetPropertiesShouldKeepNull()
    {
        NodeResource nodeResource = new NodeResource.Builder()
                .addProperties(null)
                .build();

        assertThat(nodeResource.getProperties()).isNull();
    }

    @Test
    void addProperties_emptyOnUnsetPropertiesShouldKeepNull()
    {
        NodeResource nodeResource = new NodeResource.Builder()
                .addProperties(Map.of())
                .build();

        assertThat(nodeResource.getProperties()).isNull();
    }

    @Test
    void addProperties_nullShouldKeepExistingProperties()
    {
        Map<String, Serializable> existing = Map.of("cm:title", "Title");

        NodeResource nodeResource = new NodeResource.Builder()
                .setProperties(existing)
                .addProperties(null)
                .build();

        assertThat(nodeResource.getProperties()).isEqualTo(existing);
    }

    @Test
    void addProperties_onUnsetPropertiesShouldSetThem()
    {
        NodeResource nodeResource = new NodeResource.Builder()
                .addProperties(Map.of("cm:title", "Title"))
                .build();

        assertThat(nodeResource.getProperties()).isEqualTo(Map.of("cm:title", "Title"));
    }

    @Test
    void addProperties_shouldMergeWithNewValuesWinning()
    {
        NodeResource nodeResource = new NodeResource.Builder()
                .setProperties(Map.of("cm:title", "Old", "cm:description", "Desc"))
                .addProperties(Map.of("cm:title", "New", "cm:author", "Bob"))
                .build();

        assertThat(nodeResource.getProperties()).isEqualTo(
                Map.of("cm:title", "New", "cm:description", "Desc", "cm:author", "Bob"));
    }

    @Test
    void addProperties_shouldAccumulateAcrossCalls()
    {
        NodeResource nodeResource = new NodeResource.Builder()
                .addProperties(Map.of("a", 1))
                .addProperties(Map.of("b", 2))
                .build();

        assertThat(nodeResource.getProperties()).isEqualTo(Map.of("a", 1, "b", 2));
    }

    @Test
    void addProperties_shouldPreserveInsertionOrder()
    {
        Map<String, Serializable> existing = new LinkedHashMap<>();
        existing.put("z", 1);
        existing.put("a", 2);
        Map<String, Serializable> added = new LinkedHashMap<>();
        added.put("m", 3);
        added.put("b", 4);

        NodeResource nodeResource = new NodeResource.Builder()
                .setProperties(existing)
                .addProperties(added)
                .build();

        assertThat(nodeResource.getProperties().keySet()).containsExactly("z", "a", "m", "b");
    }
}
