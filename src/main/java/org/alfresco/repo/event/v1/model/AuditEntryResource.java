package org.alfresco.repo.event.v1.model;

import java.io.Serializable;
import java.util.Map;

public class AuditEntryResource implements Resource
{
    private String auditApplicationId;
    private Map<String, Serializable> auditData;

    public AuditEntryResource()
    {}

    public AuditEntryResource(String auditApplicationId, Map<String, Serializable> auditData)
    {
        this.auditApplicationId = auditApplicationId;
        this.auditData = auditData;
    }

    public Map<String, ?> getAuditData()
    {
        return auditData;
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
        return auditApplicationId.equals(that.auditApplicationId) && auditData.equals(that.auditData);
    }

    @Override
    public int hashCode()
    {
        int result = auditApplicationId.hashCode();
        result = 31 * result + auditData.hashCode();
        return result;
    }
}
