/*
 * #%L
 * Alfresco Repository
 * %%
 * Copyright (C) 2005 - 2024 Alfresco Software Limited
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

import org.junit.jupiter.api.Test;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;


/** Unit tests for {{@code @NodeResource}} . */
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
}
