package org.alfresco.repo.event.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateSerializer extends StdSerializer<Date>
{

    private static final long serialVersionUID = 8901511880933150511L;
    private final DateTimeSerializer dateTimeSerializer;

    public DateSerializer()
    {
        super(null, false);
        this.dateTimeSerializer = new DateTimeSerializer();
    }

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException
    {
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(date.toInstant(), ZoneOffset.UTC);
        //delegating the formatting to the DateTimeSerializer in order to apply the same strategy
        dateTimeSerializer.serialize(zonedDateTime, jsonGenerator, serializerProvider);
    }
}
