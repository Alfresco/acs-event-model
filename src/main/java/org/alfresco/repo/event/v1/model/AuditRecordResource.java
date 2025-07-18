package org.alfresco.repo.event.v1.model;

import java.util.Map;

public class AuditRecordResource implements Resource
{
    private String auditApplicationId;
    private Map<String, ?> auditRecordData;

    public AuditRecordResource()
    {}

    public AuditRecordResource(String auditApplicationId, Map<String, ?> auditRecordData)
    {
        this.auditApplicationId = auditApplicationId;
        this.auditRecordData = auditRecordData;
    }

    public Map<String, ?> getAuditRecordData()
    {
        return auditRecordData;
    }

    public String getAuditApplicationId()
    {
        return auditApplicationId;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass())
            return false;

        AuditEntryResource that = (AuditEntryResource) o;
        return auditApplicationId.equals(that.getAuditApplicationId()) && auditRecordData.equals(that.getAuditData());
    }

    @Override
    public int hashCode()
    {
        int result = auditApplicationId.hashCode();
        result = 31 * result + auditRecordData.hashCode();
        return result;
    }
}