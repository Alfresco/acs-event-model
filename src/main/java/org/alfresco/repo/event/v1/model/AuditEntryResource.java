package org.alfresco.repo.event.v1.model;

import java.util.Map;
import java.util.Objects;

public class AuditEntryResource implements Resource
{
    private Map<String, ?> auditData;

    public AuditEntryResource()
    {}

    public AuditEntryResource(Map<String, ?> auditData)
    {
        this.auditData = auditData;
    }

    public Map<String, ?> getAuditData()
    {
        return auditData;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass())
            return false;
        AuditEntryResource that = (AuditEntryResource) o;
        return Objects.equals(auditData, that.auditData);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(auditData);
    }
}
