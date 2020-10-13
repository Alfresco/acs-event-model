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

public interface EventType
{
  String NODE_CREATED = "org.alfresco.event.node.Created";
  String NODE_UPDATED = "org.alfresco.event.node.Updated";
  String NODE_DELETED = "org.alfresco.event.node.Deleted";
  String CHILD_ASSOC_CREATED = "org.alfresco.event.assoc.child.Created";
  String CHILD_ASSOC_UPDATED = "org.alfresco.event.assoc.child.Updated";
  String CHILD_ASSOC_DELETED = "org.alfresco.event.assoc.child.Deleted";
  String PEER_ASSOC_CREATED = "org.alfresco.event.assoc.peer.Created";
  String PEER_ASSOC_UPDATED = "org.alfresco.event.assoc.peer.Updated";
  String PEER_ASSOC_DELETED = "org.alfresco.event.assoc.peer.Deleted";
}
