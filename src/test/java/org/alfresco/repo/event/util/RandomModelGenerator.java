/*
 * #%L
 * Alfresco Repository
 * %%
 * Copyright (C) 2025 Alfresco Software Limited
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;

import org.apache.commons.lang3.reflect.TypeUtils;
import uk.co.jemos.podam.api.AbstractRandomDataProviderStrategy;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import org.alfresco.repo.event.extension.ExtensionAttributes;
import org.alfresco.repo.event.extension.ExtensionAttributesImpl;
import org.alfresco.repo.event.v1.model.DataAttributes;
import org.alfresco.repo.event.v1.model.EventData;
import org.alfresco.repo.event.v1.model.NodeResource;
import org.alfresco.repo.event.v1.model.RepoEvent;
import org.alfresco.repo.event.v1.model.Resource;

@SuppressWarnings("unchecked")
public class RandomModelGenerator
{
    private static final PodamFactory POJO_FACTORY = new PodamFactoryImpl(new EventModelGenStrategy());

    private RandomModelGenerator()
    {}

    public static NodeResource generateNodeResource()
    {
        var pojo = POJO_FACTORY.manufacturePojoWithFullData(NodeResource.class);
        assertAllFieldsAreSet(pojo);
        return pojo;
    }

    public static <R extends Resource> EventData<R> generateEventData()
    {
        var pojo = POJO_FACTORY.manufacturePojoWithFullData(EventData.class, NodeResource.class);
        assertAllFieldsAreSet(pojo);
        return pojo;
    }

    public static <D extends DataAttributes<? extends Resource>> RepoEvent<D> generateRepoEvent()
    {
        var pojo = POJO_FACTORY.manufacturePojoWithFullData(RepoEvent.class, TypeUtils.parameterize(EventData.class, NodeResource.class));
        assertAllFieldsAreSet(pojo);
        return pojo;
    }

    static class EventModelGenStrategy extends AbstractRandomDataProviderStrategy
    {
        @Override
        public <T> Class<? extends T> getSpecificClass(Class<T> nonInstantiatableClass)
        {
            if (nonInstantiatableClass.equals(ExtensionAttributes.class))
            {
                return (Class<? extends T>) ExtensionAttributesImpl.class;
            }
            return super.getSpecificClass(nonInstantiatableClass);
        }
    }

    static void assertAllFieldsAreSet(Object obj)
    {
        try
        {
            for (Field field : obj.getClass().getDeclaredFields())
            {
                field.setAccessible(true);
                assertNotNull(field.get(obj), field.getName());
                if (!field.getType().isPrimitive())
                {
                    assertAllFieldsAreSet(field);
                }
            }
        }
        catch (IllegalAccessException e)
        {
            fail("all fields should be accessible in this test");
        }
    }
}
