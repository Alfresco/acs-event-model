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

package org.alfresco.repo.event.databind;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

/**
 * Jackson serializer for the values of type {@link ZonedDateTime}.
 *
 * @author Jamal Kaabi-Mofrad
 */
public class DateTimeSerializerJackson3 extends StdSerializer<ZonedDateTime>
{
    public DateTimeSerializerJackson3()
    {
        super(ZonedDateTime.class);
    }

    @Override
    public void serialize(ZonedDateTime zonedDateTime, JsonGenerator jsonGenerator,
            SerializationContext serializationContext)
    {
        jsonGenerator.writeString(zonedDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }
}
