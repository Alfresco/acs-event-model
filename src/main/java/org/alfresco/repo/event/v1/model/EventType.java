/*-
 * #%L
 * Alfresco Elastic Connector :: Live Indexing :: All-in-one bundle
 * %%
 * Copyright (C) 2020 Alfresco Software Limited
 * %%
 * License rights for this program may be obtained from Alfresco Software, Ltd.
 * pursuant to a written agreement and any use of this program without such an
 * agreement is prohibited.
 * #L%
 */
package org.alfresco.repo.event.v1.model;

/**
 * List of supported event types.
 *
 * @author Jamal Kaabi-Mofrad
 */
public enum EventType
{
    NODE_CREATED(EventTypeConst.CREATED, ContextType.NODE), NODE_UPDATED(EventTypeConst.UPDATED, ContextType.NODE), NODE_DELETED(EventTypeConst.DELETED, ContextType.NODE),
    CHILD_ASSOC_CREATED(EventTypeConst.CREATED, ContextType.CHILD_ASSOC), CHILD_ASSOC_DELETED(EventTypeConst.DELETED, ContextType.CHILD_ASSOC),
    PEER_ASSOC_CREATED(EventTypeConst.CREATED, ContextType.PEER_ASSOC), PEER_ASSOC_DELETED(EventTypeConst.DELETED, ContextType.PEER_ASSOC);

    private static final String PREFIX = "org.alfresco.event.";
    private final String type;
    private final ContextType contextType;

    EventType(String type, ContextType contextType)
    {
      this.type   = type;
      this.contextType = contextType;
    }

    /* package*/ String getContext()
    {
      return contextType.getContext();
    }

    @Override
    public String toString()
    {
      return PREFIX + getContext() + type;
    }

    /**
     * Gets the type of an event prefixed with a reverse-DNS name.
     * <p>
     * See <a href="https://github.com/cloudevents/spec/blob/v1.0/spec.md#type">v1.0 spec#type</a>
     */
    public String getType()
    {
        return toString();
    }

    private enum ContextType
    {
        NODE("node."), CHILD_ASSOC("assoc.child."), PEER_ASSOC("assoc.peer.");

        private final String context;
        ContextType(String context)
        {
            this.context = context;
        }

        String getContext()
      {
        return context;
      }
    }

    private static class EventTypeConst
    {
        private static final String CREATED = "Created";
        private static final String UPDATED = "Updated";
        private static final String DELETED = "Deleted";
    }
}

