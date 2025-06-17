package org.alfresco.repo.event.v1.model;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EventTypeTest
{
    @Test
    void shouldReturnOptionalWithEventTypeFromTypeStringForAllEventTypes()
    {
        for (EventType expected : EventType.values())
        {
            String type = expected.getType();

            Optional<EventType> actual = EventType.findByType(type);

            assertTrue(actual.isPresent());
            assertEquals(expected, actual.get());
        }
    }

    @Test
    void shouldReturnEmptyOptionalWhenTypeStringIsNotMatchingAnyEventType()
    {
        String type = "wrong.type";

        Optional<EventType> actual = EventType.findByType(type);

        assertFalse(actual.isPresent());
    }
}
